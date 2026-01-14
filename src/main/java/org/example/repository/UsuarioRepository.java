package org.example.repository;

import org.example.config.DataBase;
import org.example.model.InformacionGeneral;
import org.example.model.Usuario;
import org.example.model.administrarCliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para manejar operaciones de base de datos de usuarios.
 * Incluye CRUD completo y consultas especializadas.
 */
public class UsuarioRepository {

    /**
     * Constructor vacío para crear un repositorio de usuarios sin inicializar datos.
     */
    public UsuarioRepository() {
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     * @param usuario el usuario a registrar
     * @throws SQLException si hay error en la base de datos
     */
    public void registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, apellidoPaterno, apellidoMaterno, telefono, correo, contraseña, imagenPerfil, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellidoPaterno());
            stmt.setString(3, usuario.getApellidoMaterno());
            stmt.setString(4, usuario.getTelefono());
            stmt.setString(5, usuario.getCorreo());
            stmt.setString(6, usuario.getPassword());
            stmt.setString(7, usuario.getImagenPerfil());
            stmt.setInt(8, usuario.getRol());
            stmt.executeUpdate();
        }
    }

    /**
     * Verifica si un correo ya existe en la base de datos.
     * @param correo el correo a verificar
     * @return true si existe, false si no
     * @throws SQLException si hay error en la base de datos
     */
    public boolean correoExiste(String correo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario WHERE correo = ?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    /**
     * Obtiene un usuario por su correo electrónico.
     * @param correo el correo del usuario
     * @return Optional con el usuario o vacío si no existe
     * @throws SQLException si hay error en la base de datos
     */
    public Optional<Usuario> obtenerPorCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapearUsuario(rs));
            }
        }
        return Optional.empty();
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id el ID del usuario
     * @return Optional con el usuario o vacío si no existe
     * @throws SQLException si hay error en la base de datos
     */
    public Optional<Usuario> obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapearUsuario(rs));
            }
        }
        return Optional.empty();
    }

    /**
     * Actualiza los datos del perfil de un usuario.
     * @param usuario el usuario con datos actualizados
     * @throws SQLException si hay error en la base de datos
     */
    public void actualizarPerfil(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nombre=?, apellidoPaterno=?, apellidoMaterno=?, imagenPerfil=?, telefono=?, correo=?, contraseña=?, rol=? WHERE idUsuario=?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellidoPaterno());
            stmt.setString(3, usuario.getApellidoMaterno());
            stmt.setString(4, usuario.getImagenPerfil());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getCorreo());
            stmt.setString(7, usuario.getPassword());
            stmt.setInt(8, usuario.getRol());
            stmt.setInt(9, usuario.getIdUsuario());

            int filas = stmt.executeUpdate();
            if (filas == 0) {
                throw new IllegalArgumentException("No se encontró el usuario con ID " + usuario.getIdUsuario());
            }
        }
    }

    /**
     * Actualiza la contraseña de un usuario.
     * @param correo el correo del usuario
     * @param nuevaPasswordEncriptada la nueva contraseña encriptada
     * @throws SQLException si hay error en la base de datos
     */
    public void actualizarPassword(String correo, String nuevaPasswordEncriptada) throws SQLException {
        String sql = "UPDATE usuario SET contraseña = ? WHERE correo = ?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevaPasswordEncriptada);
            stmt.setString(2, correo);
            stmt.executeUpdate();
        }
    }

    /**
     * Obtiene información general de usuarios para el dashboard.
     * @return lista con información consolidada de usuarios
     * @throws SQLException si hay error en la base de datos
     */
    public List<InformacionGeneral> obtenerInformacionGeneral() throws SQLException {
        List<InformacionGeneral> informacionGeneralList = new ArrayList<>();
        String sql = "SELECT " +
                "u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correo, u.telefono, " +
                "GROUP_CONCAT(DISTINCT sa.direccionTerreno SEPARATOR '\\n') AS direcciones, " +
                "SUM(sa.superficieTotal) AS superficieTotal, " +
                "GROUP_CONCAT(DISTINCT cc.nombreCultivo SEPARATOR ', ') AS cultivos " +
                "FROM usuario u " +
                "INNER JOIN solicitudasesoria sa ON u.idUsuario = sa.idAgricultor " +
                "INNER JOIN cultivoporsolicitud cps ON sa.idSolicitud = cps.idSolicitud " +
                "INNER JOIN catalogocultivo cc ON cps.idCultivo = cc.idCultivo " +
                "WHERE sa.idEstado = 2 " +
                "GROUP BY u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correo, u.telefono";

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                informacionGeneralList.add(mapearInformacionGeneral(rs));
            }
        }
        return informacionGeneralList;
    }

    /**
     * Mapea un ResultSet a un objeto Usuario.
     * @param rs el ResultSet de la consulta
     * @return el usuario mapeado
     * @throws SQLException si hay error al leer los datos
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("idUsuario"));
        u.setNombre(rs.getString("nombre"));
        u.setApellidoPaterno(rs.getString("apellidoPaterno"));
        u.setApellidoMaterno(rs.getString("apellidoMaterno"));
        u.setTelefono(rs.getString("telefono"));
        u.setCorreo(rs.getString("correo"));
        u.setPassword(rs.getString("contraseña"));
        u.setImagenPerfil(rs.getString("imagenPerfil"));
        u.setRol(rs.getInt("rol"));
        return u;
    }

    /**
     * Mapea un ResultSet a un objeto InformacionGeneral.
     * @param rs el ResultSet de la consulta
     * @return la información general mapeada
     * @throws SQLException si hay error al leer los datos
     */
    private InformacionGeneral mapearInformacionGeneral(ResultSet rs) throws SQLException {
        InformacionGeneral info = new InformacionGeneral();
        info.setIdUsuario(rs.getInt("idUsuario"));
        info.setNombre(rs.getString("nombre"));
        info.setApellidoPaterno(rs.getString("apellidoPaterno"));
        info.setApellidoMaterno(rs.getString("apellidoMaterno"));
        info.setCorreo(rs.getString("correo"));
        info.setTelefono(rs.getString("telefono"));
        info.setDirecciones(rs.getString("direcciones"));
        info.setSuperficieTotal(rs.getFloat("superficieTotal"));
        info.setCultivos(rs.getString("cultivos"));
        return info;
    }
    /**
     * Obtiene todos los clientes (usuarios con rol 2).
     * @return lista de todos los clientes
     * @throws SQLException si hay error en la base de datos
     */
    public List<administrarCliente> verTodosClientes() throws SQLException {
        List<administrarCliente> clientes = new ArrayList<>();
        String sql = "SELECT idUsuario, imagenPerfil, nombre, apellidoPaterno, apellidoMaterno, telefono, correo FROM usuario WHERE rol = 2";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientes.add(mapearClientePublico(rs));
            }
        }
        System.out.println(clientes);
        System.out.println(sql);
        return clientes;
    }
    /**
     * Mapea un ResultSet a un objeto administrarCliente.
     * @param rs el ResultSet de la consulta
     * @return el cliente mapeado
     * @throws SQLException si hay error al leer los datos
     */
    private administrarCliente mapearClientePublico(ResultSet rs) throws SQLException {
        administrarCliente u = new administrarCliente();
        u.setIdUsuario(rs.getInt("idUsuario"));
        u.setNombre(rs.getString("nombre"));
        u.setApellidoPaterno(rs.getString("apellidoPaterno"));
        u.setApellidoMaterno(rs.getString("apellidoMaterno"));
        u.setTelefono(rs.getString("telefono"));
        u.setCorreo(rs.getString("correo"));
        u.setImagenPerfil(rs.getString("imagenPerfil"));
        return u;
    }
    /**
     * Elimina un cliente por su ID.
     * @param id el ID del cliente a eliminar
     * @return true si se eliminó, false si no existía
     * @throws SQLException si hay error en la base de datos
     */
    public boolean eliminarClientePorId(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE rol = 2 and idUsuario = ?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            return filas > 0;
        }
    }
    /**
     * Actualiza los datos de un cliente.
     * @param usuario el cliente con datos actualizados
     * @throws SQLException si hay error en la base de datos
     */
    public void editarClientes(administrarCliente usuario) throws SQLException {
        String sql = "UPDATE usuario SET nombre=?, apellidoPaterno=?, apellidoMaterno=?, imagenPerfil=?, telefono=?, correo=? WHERE idUsuario=?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellidoPaterno());
            stmt.setString(3, usuario.getApellidoMaterno());
            stmt.setString(4, usuario.getImagenPerfil());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getCorreo());
            stmt.setInt(7, usuario.getIdUsuario());

            int filas = stmt.executeUpdate();
            if (filas == 0) {
                throw new IllegalArgumentException("No se encontró el usuario con ID " + usuario.getIdUsuario());
            }
        }
    }
}