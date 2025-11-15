package org.example.repository;

import org.example.config.DataBase;
import org.example.model.Notificacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificacionRepository {

    public NotificacionRepository() {
    }

    public List<Notificacion> obtenerNotificacionesAsesorias(String filtroNotificacion) {
        List<Notificacion> notificacionList = new ArrayList<>();

        // Reemplazar "idUsuario" por el nombre correcto de columna en esta tabla
        String filtroCorregido = filtroNotificacion.replace("idUsuario", "solicitudasesoria.idAgricultor");

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT idSolicitud, nombreEstado FROM solicitudasesoria " +
                             "INNER JOIN catalogoestado ON solicitudasesoria.idEstado = catalogoestado.idEstado " +
                             filtroCorregido)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notificacionList.add(mapearAsesoria(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notificacionList;
    }

    public List<Notificacion> obtenerNotificacionesTalleres(String filtroNotificacion) {
        List<Notificacion> notificacionList = new ArrayList<>();

        // Reemplazar "idUsuario" por el nombre correcto de columna en esta tabla
        String filtroCorregido = filtroNotificacion.replace("idUsuario", "solicitudtaller.idAgricultor");

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT idSolicitudTaller, nombreEstado FROM solicitudtaller " +
                             "INNER JOIN catalogoestado ON solicitudtaller.idEstado = catalogoestado.idEstado " +
                             filtroCorregido)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notificacionList.add(mapearTaller(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notificacionList;
    }

    public List<Notificacion> obtenerNotificacionesTareas(String filtroNotificacion) {
        List<Notificacion> notificacionList = new ArrayList<>();

        // Reemplazar "idUsuario" por el nombre correcto de columna en esta tabla
        String filtroCorregido = filtroNotificacion.replace("idUsuario", "sa.idAgricultor");

        String baseQuery = "SELECT t.idTarea, ce.nombreEstado " +
                "FROM tarea t " +
                "INNER JOIN catalogoestado ce ON t.idEstado = ce.idEstado " +
                "INNER JOIN plandecultivo pc ON t.idPlan = pc.idPlan " +
                "INNER JOIN solicitudasesoria sa ON pc.idSolicitud = sa.idSolicitud ";

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(baseQuery + filtroCorregido)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notificacionList.add(mapearTarea(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notificacionList;
    }

    private Notificacion mapearAsesoria(ResultSet rs) throws SQLException {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(rs.getInt("idSolicitud"));
        notificacion.setNombreEstado(rs.getString("nombreEstado"));
        notificacion.setTipoNotificacion("asesoria");
        return notificacion;
    }

    private Notificacion mapearTaller(ResultSet rs) throws SQLException {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(rs.getInt("idSolicitudTaller"));
        notificacion.setNombreEstado(rs.getString("nombreEstado"));
        notificacion.setTipoNotificacion("taller");
        return notificacion;
    }

    private Notificacion mapearTarea(ResultSet rs) throws SQLException {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(rs.getInt("idTarea"));
        notificacion.setNombreEstado(rs.getString("nombreEstado"));
        notificacion.setTipoNotificacion("tarea");
        return notificacion;
    }
}