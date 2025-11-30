package org.example.service;

import org.example.model.InformacionGeneral;
import org.example.model.Usuario;
import org.example.model.administrarCliente;
import org.example.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
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

    @Override
    public Optional<Usuario> obtenerPorCorreo(String correo) throws SQLException {
        return repository.obtenerPorCorreo(correo);
    }

    @Override
    public Optional<Usuario> obtenerPorId(int id) throws SQLException {
        return repository.obtenerPorId(id);
    }

    @Override
    public void editarPerfil(Usuario usuario) throws SQLException {
        repository.actualizarPerfil(usuario);
    }

    @Override
    public void recuperarPassword(String correo, String nuevaPassword) throws SQLException {
        if (!repository.correoExiste(correo)) {
            throw new IllegalArgumentException("Correo no registrado");
        }
        String hashed = BCrypt.hashpw(nuevaPassword, BCrypt.gensalt());
        repository.actualizarPassword(correo, hashed);
    }

    @Override
    public List<InformacionGeneral> obtenerInformacionGeneral() throws SQLException {
        return repository.obtenerInformacionGeneral();
    }

    @Override
    public List<administrarCliente> verTodosClientes() throws SQLException {
        return repository.verTodosClientes();
    }

    @Override
    public void eliminarClientes(int id) throws SQLException {
        boolean eliminado = repository.eliminarClientePorId(id);
        if (!eliminado) {
            throw new IllegalArgumentException("No existe un cliente con ID " + id);
        }
    }

    @Override
    public void editarClientes(int id, administrarCliente usuario) throws SQLException {
        usuario.setIdUsuario(id);
        repository.editarClientes(usuario);
    }
}
