package org.example.repository;

import org.example.config.DataBase;
import org.example.model.SolicitudTaller;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SolicitudTallerRepository {

    public SolicitudTallerRepository() {}

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
            stmt.setString(8, solicitud.getEstadoPagoImagen()); // ← cambiado de Blob a String
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
    public List<SolicitudTaller> obtenerSolicitudesPorUsuario(int userId) {
        List<SolicitudTaller> lista = new ArrayList<>();
        // Subconsulta para obtener el nombre del agrónomo (usuario rol 1)
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

    private SolicitudTaller mapear(ResultSet rs) throws SQLException {
        SolicitudTaller s = new SolicitudTaller();
        s.setIdSolicitudTaller(rs.getInt("idSolicitudTaller"));
        s.setIdAgricultor(rs.getInt("idAgricultor"));
        s.setIdTaller(rs.getInt("idTaller"));

        // Mapear nombre del agricultor si viene en la consulta
        try {
            s.setNombreAgricultor(rs.getString("nombreCompleto"));
        } catch (SQLException e) { /* ignorar si no está */ }

        // Mapear nombre del agrónomo si viene en la consulta
        try {
            String agro = rs.getString("nombreAgronomo");
            s.setNombreAgronomo(agro);
            s.setImpartio(agro); // Campo auxiliar para compatibilidad
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

    public void eliminar(int id) {
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM solicitudtaller WHERE idSolicitudTaller = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
}