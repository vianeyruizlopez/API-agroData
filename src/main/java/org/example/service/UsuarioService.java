package org.example.service;

import org.example.model.InformacionGeneral;
import org.example.model.Usuario;
import org.example.model.administrarCliente;
import org.example.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que implementa la lógica de negocio para usuarios.
 * Maneja registro, autenticación y administración de usuarios.
 */
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository repository;

    /**
     * Constructor que recibe el repositorio de usuarios.
     * @param repository el repositorio para acceder a los datos
     */
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    /**
     * Registra un nuevo usuario validando que sea agricultor y que el correo no exista.
     * Encripta la contraseña antes de guardarla.
     * @param usuario el usuario a registrar
     * @throws SQLException si hay error en la base de datos
     */
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

    /**
     * Busca un usuario por su correo electrónico.
     * @param correo el correo del usuario
     * @return el usuario encontrado o vacío si no existe
     * @throws SQLException si hay error en la base de datos
     */
    @Override
    public Optional<Usuario> obtenerPorCorreo(String correo) throws SQLException {
        return repository.obtenerPorCorreo(correo);
    }

    /**
     * Busca un usuario por su ID.
     * @param id el ID del usuario
     * @return el usuario encontrado o vacío si no existe
     * @throws SQLException si hay error en la base de datos
     */
    @Override
    public Optional<Usuario> obtenerPorId(int id) throws SQLException {
        return repository.obtenerPorId(id);
    }

    /**
     * Actualiza el perfil de un usuario.
     * @param usuario el usuario con los datos actualizados
     * @throws SQLException si hay error en la base de datos
     */
    @Override
    public void editarPerfil(Usuario usuario) throws SQLException {
        repository.actualizarPerfil(usuario);
    }

    /**
     * Cambia la contraseña de un usuario verificando que el correo exista.
     * Encripta la nueva contraseña antes de guardarla.
     * @param correo el correo del usuario
     * @param nuevaPassword la nueva contraseña
     * @throws SQLException si hay error en la base de datos
     */
    @Override
    public void recuperarPassword(String correo, String nuevaPassword) throws SQLException {
        if (!repository.correoExiste(correo)) {
            throw new IllegalArgumentException("Correo no registrado");
        }
        String hashed = BCrypt.hashpw(nuevaPassword, BCrypt.gensalt());
        repository.actualizarPassword(correo, hashed);
    }

    /**
     * Obtiene información general del sistema para mostrar en el dashboard.
     * @return lista con estadísticas del sistema
     * @throws SQLException si hay error en la base de datos
     */
    @Override
    public List<InformacionGeneral> obtenerInformacionGeneral() throws SQLException {
        return repository.obtenerInformacionGeneral();
    }

    /**
     * Obtiene todos los clientes registrados en el sistema.
     * @return lista de todos los clientes
     * @throws SQLException si hay error en la base de datos
     */
    @Override
    public List<administrarCliente> verTodosClientes() throws SQLException {
        return repository.verTodosClientes();
    }

    /**
     * Elimina un cliente del sistema verificando que exista.
     * @param id el ID del cliente a eliminar
     * @throws SQLException si hay error en la base de datos
     */
    @Override
    public void eliminarClientes(int id) throws SQLException {
        boolean eliminado = repository.eliminarClientePorId(id);
        if (!eliminado) {
            throw new IllegalArgumentException("No existe un cliente con ID " + id);
        }
    }

    /**
     * Actualiza los datos de un cliente existente.
     * @param id el ID del cliente
     * @param usuario los nuevos datos del cliente
     * @throws SQLException si hay error en la base de datos
     */
    @Override
    public void editarClientes(int id, administrarCliente usuario) throws SQLException {
        usuario.setIdUsuario(id);
        repository.editarClientes(usuario);
    }
}
