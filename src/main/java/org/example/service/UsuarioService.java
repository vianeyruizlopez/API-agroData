package org.example.service;

import org.example.model.InformacionGeneral;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void registrar(Usuario usuario) throws SQLException {
        if (usuario.getRol() != 2) {
            throw new IllegalArgumentException("Solo se permite registro de agricultores (rol 2)");
        }
        if (repository.correoExiste(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        String hashed = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
        usuario.setPassword(hashed);
        repository.registrarUsuario(usuario);
    }

    public Optional<Usuario> obtenerPorCorreo(String correo) throws SQLException {
        return repository.obtenerPorCorreo(correo);
    }

    public Optional<Usuario> obtenerPorId(int id) throws SQLException {
        return repository.obtenerPorId(id);
    }

    public void editarPerfil(Usuario usuario) throws SQLException {
        repository.actualizarPerfil(usuario);
    }

    public void recuperarPassword(String correo, String nuevaPassword) throws SQLException {
        if (!repository.correoExiste(correo)) {
            throw new IllegalArgumentException("Correo no registrado");
        }
        String hashed = BCrypt.hashpw(nuevaPassword, BCrypt.gensalt());
        repository.actualizarPassword(correo, hashed);
    }

    public List<InformacionGeneral> obtenerInformacionGeneral() throws SQLException {
        return repository.obtenerInformacionGeneral();
    }


    public List<Usuario> obtenerTodosLosClientes() {
        return repository.obtenerClientes();
    }

    public void eliminarCliente(int id) throws SQLException {
        repository.eliminarUsuario(id);
    }
    public void actualizarCliente(Usuario usuario) throws SQLException {
        repository.actualizarPerfil(usuario);
    }
}
