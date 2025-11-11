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
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("select idSolicitud, nombreEstado from solicitudasesoria inner join catalogoestado on solicitudasesoria.idEstado = catalogoestado.idEstado " + filtroNotificacion)) {
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
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("select idSolicitudTaller, nombreEstado from solicitudtaller inner join catalogoestado on solicitudtaller.idEstado = catalogoestado.idEstado " + filtroNotificacion)) {
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
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("select idTarea, nombreEstado from tarea inner join catalogoestado on tarea.idEstado = catalogoestado.idEstado " + filtroNotificacion)) {
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
