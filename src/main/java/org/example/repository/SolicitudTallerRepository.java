package org.example.repository;

import org.example.config.DataBase;
import org.example.model.SolicitudTaller;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositorio para gestionar las solicitudes de talleres agrícolas.
 * Maneja las operaciones CRUD relacionadas con solicitudes de participación en talleres.
 */
public class SolicitudTallerRepository {

    /**
     * Constructor por defecto del repositorio de solicitudes de taller.
     */
    public SolicitudTallerRepository() {}

    /**
     * Obtiene una solicitud de taller por su ID.
     * @param id ID de la solicitud
     * @return Solicitud de taller encontrada o null si no existe
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public SolicitudTaller obtenerPorId(int id) {
        String sql = "SELECT * FROM solicitudtaller WHERE idSolicitudTaller = ?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene todas las solicitudes de taller activas.
     * @return Lista de solicitudes de taller con información del agricultor y taller
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public List<SolicitudTaller> obtenerTodas() {
        List<SolicitudTaller> lista = new ArrayList<>();
        String sql = """
            SELECT 
                s.*,
                CONCAT(u.nombre, ' ', u.apellidoPaterno, ' ', u.apellidoMaterno) AS nombreCompleto,
                ct.nombreTaller
            FROM solicitudtaller s
            JOIN catalogoestado e ON s.idEstado = e.idEstado
            JOIN usuario u ON s.idAgricultor = u.idUsuario
            JOIN catalogotaller ct ON s.idTaller  = ct.idTaller
            WHERE e.nombreEstado != 5 
        """;
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Agrega una nueva solicitud de taller a la base de datos.
     * @param solicitud Solicitud de taller a insertar
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public void agregar(SolicitudTaller solicitud) {
        String sql = """
            INSERT INTO solicitudtaller (
                idAgricultor,
                idTaller,
                fechaSolicitud,
                fechaAplicarTaller,
                direccion,
                comentario,
                idEstado,
                estadoPagoImagen,
                fechaFin
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, solicitud.getIdAgricultor());
            stmt.setInt(2, solicitud.getIdTaller());

            LocalDateTime fechaSolicitud = solicitud.getFechaSolicitud() != null ? solicitud.getFechaSolicitud() : LocalDateTime.now();
            stmt.setObject(3, fechaSolicitud);

            stmt.setObject(4, solicitud.getFechaAplicarTaller());
            stmt.setString(5, solicitud.getDireccion());
            stmt.setString(6, solicitud.getComentario());
            stmt.setInt(7, solicitud.getIdEstado());
            stmt.setString(8, solicitud.getEstadoPagoImagen());
            stmt.setObject(9, solicitud.getFechaFin());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    solicitud.setIdSolicitudTaller(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Obtiene las solicitudes de taller de un usuario específico.
     * @param userId ID del usuario agricultor
     * @return Lista de solicitudes del usuario
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public List<SolicitudTaller> obtenerSolicitudesPorUsuario(int userId) {
        List<SolicitudTaller> lista = new ArrayList<>();

        String sql = """
            SELECT s.*, 
                   (SELECT CONCAT(nombre, ' ', apellidoPaterno) FROM usuario WHERE rol = 1 LIMIT 1) as nombreAgronomo
            FROM solicitudtaller s
            WHERE s.idAgricultor = ?
        """;

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Mapea un ResultSet a un objeto SolicitudTaller.
     * @param rs ResultSet con los datos de la solicitud
     * @return Objeto SolicitudTaller mapeado
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private SolicitudTaller mapear(ResultSet rs) throws SQLException {
        SolicitudTaller s = new SolicitudTaller();
        s.setIdSolicitudTaller(rs.getInt("idSolicitudTaller"));
        s.setIdAgricultor(rs.getInt("idAgricultor"));
        s.setIdTaller(rs.getInt("idTaller"));


        try {
            s.setNombreAgricultor(rs.getString("nombreCompleto"));
        } catch (SQLException e) { /* ignorar si no está */ }


        try {
            String agro = rs.getString("nombreAgronomo");
            s.setNombreAgronomo(agro);
            s.setImpartio(agro);
        } catch (SQLException e) { /* ignorar */ }

        try {
            s.setNombreTaller(rs.getString("nombreTaller"));
        } catch (SQLException e) { /* ignorar si no está */ }

        Timestamp tsSolicitud = rs.getTimestamp("fechaSolicitud");
        if (tsSolicitud != null) s.setFechaSolicitud(tsSolicitud.toLocalDateTime());
        Date dAplicar = rs.getDate("fechaAplicarTaller");
        if (dAplicar != null) s.setFechaAplicarTaller(dAplicar.toLocalDate());
        s.setDireccion(rs.getString("direccion"));
        s.setComentario(rs.getString("comentario"));
        s.setIdEstado(rs.getInt("idEstado"));
        s.setEstadoPagoImagen(rs.getString("estadoPagoImagen"));
        Date dFin = rs.getDate("fechaFin");
        if (dFin != null) s.setFechaFin(dFin.toLocalDate());

        return s;
    }

    /**
     * Elimina una solicitud de taller de la base de datos.
     * @param id ID de la solicitud a eliminar
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public void eliminar(int id) {
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM solicitudtaller WHERE idSolicitudTaller = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la imagen de comprobante de pago y el estado de la solicitud.
     * @param id ID de la solicitud
     * @param imagen Imagen en base64 del comprobante
     * @param nuevoEstado Nuevo estado de la solicitud
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public void actualizarImagenPago(int id, String imagen, int nuevoEstado) {
        String sql = "UPDATE solicitudtaller SET estadoPagoImagen = ?, idEstado = ? WHERE idSolicitudTaller = ?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, imagen);
            stmt.setInt(2, nuevoEstado);
            stmt.setInt(3, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el estado de una solicitud de taller.
     * @param id ID de la solicitud
     * @param nuevoEstado Nuevo estado de la solicitud
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public void actualizarEstado(int id, int nuevoEstado) {
        String sql = "UPDATE solicitudtaller SET idEstado = ? WHERE idSolicitudTaller = ?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nuevoEstado);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene las solicitudes de taller filtradas por estado.
     * @param idEstado ID del estado a filtrar
     * @return Lista de solicitudes con el estado especificado
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public List<SolicitudTaller> obtenerTalleresPorStatus(int idEstado) {
        List<SolicitudTaller> lista = new ArrayList<>();
        String sql = """
            SELECT s.*, CONCAT(u.nombre, ' ', u.apellidoPaterno, ' ', u.apellidoMaterno) AS nombreCompleto
            FROM solicitudtaller s
            JOIN usuario u ON s.idAgricultor = u.idUsuario
            WHERE s.idEstado = ?""";

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    /**
     * Obtiene estadísticas de talleres en un rango de fechas.
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de mapas con estadísticas por taller
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public List<Map<String, Object>> obtenerEstadisticas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Map<String, Object>> listaEstadisticas = new ArrayList<>();

        String sql = """
            SELECT 
                ct.nombreTaller, 
                COUNT(st.idSolicitudTaller) as total,
                SUM(CASE WHEN st.idEstado = 5 THEN 1 ELSE 0 END) as completados
            FROM solicitudtaller st
            JOIN catalogotaller ct ON st.idTaller = ct.idTaller
            WHERE st.fechaSolicitud BETWEEN ? AND ?
            GROUP BY ct.nombreTaller
            ORDER BY total DESC
        """;

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (fechaInicio != null) {
                stmt.setTimestamp(1, Timestamp.valueOf(fechaInicio));
            } else {
                stmt.setNull(1, Types.TIMESTAMP);
            }

            if (fechaFin != null) {
                stmt.setTimestamp(2, Timestamp.valueOf(fechaFin));
            } else {
                stmt.setNull(2, Types.TIMESTAMP);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("nombreTaller", rs.getString("nombreTaller"));
                fila.put("total", rs.getInt("total"));
                fila.put("completados", rs.getInt("completados"));
                listaEstadisticas.add(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEstadisticas;
    }
}