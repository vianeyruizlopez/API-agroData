package org.example.service;

import org.example.model.InformacionGeneral;
import org.example.model.Usuario;
import org.example.model.administrarCliente;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los servicios para manejar usuarios.
 * Incluye operaciones de registro, login, perfil y administración.
 */
public interface IUsuarioService {
    /**
     * Registra un nuevo usuario en el sistema.
     * @param usuario el usuario a registrar
     * @throws SQLException si hay error en la base de datos
     */
    void registrar(Usuario usuario) throws SQLException;
    /**
     * Busca un usuario por su correo electrónico.
     * @param correo el correo del usuario
     * @return el usuario encontrado o vacío si no existe
     * @throws SQLException si hay error en la base de datos
     */
    Optional<Usuario> obtenerPorCorreo(String correo) throws SQLException;
    /**
     * Busca un usuario por su ID.
     * @param id el ID del usuario
     * @return el usuario encontrado o vacío si no existe
     * @throws SQLException si hay error en la base de datos
     */
    Optional<Usuario> obtenerPorId(int id) throws SQLException;
    /**
     * Actualiza los datos del perfil de un usuario.
     * @param usuario el usuario con los datos actualizados
     * @throws SQLException si hay error en la base de datos
     */
    void editarPerfil(Usuario usuario) throws SQLException;
    /**
     * Cambia la contraseña de un usuario.
     * @param correo el correo del usuario
     * @param nuevaPassword la nueva contraseña
     * @throws SQLException si hay error en la base de datos
     */
    void recuperarPassword(String correo, String nuevaPassword) throws SQLException;
    /**
     * Obtiene información general del sistema para el dashboard.
     * @return lista con estadísticas generales
     * @throws SQLException si hay error en la base de datos
     */
    List<InformacionGeneral> obtenerInformacionGeneral() throws SQLException;
    /**
     * Obtiene la lista de todos los clientes registrados.
     * @return lista de todos los clientes
     * @throws SQLException si hay error en la base de datos
     */
    List<administrarCliente> verTodosClientes() throws SQLException;
    /**
     * Elimina un cliente del sistema.
     * @param id el ID del cliente a eliminar
     * @throws SQLException si hay error en la base de datos
     */
    void eliminarClientes(int id) throws SQLException;
    /**
     * Actualiza los datos de un cliente.
     * @param id el ID del cliente
     * @param usuario los nuevos datos del cliente
     * @throws SQLException si hay error en la base de datos
     */
    void editarClientes(int id, administrarCliente usuario) throws SQLException;
}
