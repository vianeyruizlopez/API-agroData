package org.example.repository;

import org.example.config.DataBase;
import org.example.model.ReportePlaga;
import org.example.model.Tarea;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TareaRepository {

    public TareaRepository() {}

    public Tarea obtenerPorId(int idTarea) {
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tarea WHERE idTarea = ?")) {
            stmt.setInt(1, idTarea);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Tarea> obtenerTodas() {
        List<Tarea> lista = new ArrayList<>();
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tarea")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void agregar(Tarea tarea) {
        String sql = """
        INSERT INTO tarea (
            idPlan,
            nombreTarea,
            fechaInicio,
            fechaVencimiento,
            idEstado,
            fechaCompletado
        ) VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, tarea.getIdPlan());
            stmt.setString(2, tarea.getNombreTarea());

            LocalDate fechaInicio = tarea.getFechaInicio() != null ? tarea.getFechaInicio() : LocalDate.now();
            stmt.setObject(3, fechaInicio);

            stmt.setObject(4, tarea.getFechaVencimiento());
            stmt.setInt(5, tarea.getIdEstado());
            stmt.setObject(6, tarea.getFechaCompletado());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tarea.setIdTarea(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarEstado(int id, int nuevoEstado) {
        String sql;
        boolean marcarCompletado = (nuevoEstado == 3);

        if (marcarCompletado) {
            sql = "UPDATE tarea SET idEstado = ?, fechaCompletado = ? WHERE idTarea = ?";
        } else {
            sql = "UPDATE tarea SET idEstado = ? WHERE idTarea = ?";
        }

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nuevoEstado);

            if (marcarCompletado) {
                stmt.setObject(2, LocalDate.now());
                stmt.setInt(3, id);
            } else {
                stmt.setInt(2, id);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM tarea WHERE idTarea = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Tarea> obtenerTareasConReportePlaga() {
        List<Tarea> lista = new ArrayList<>();
        String sql = """
            SELECT DISTINCT t.*
            FROM tarea t
            INNER JOIN (
                SELECT idTarea FROM reportePlaga WHERE idTarea IS NOT NULL
                UNION
                SELECT idTarea FROM tareaReportePlaga
            ) AS tareasConReporte ON t.idTarea = tareasConReporte.idTarea
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

    public void registrarReportePlaga(ReportePlaga reporte) throws SQLException {
        String sql = "INSERT INTO reporteplaga (idPlan, fechaReporte, tipoPlaga, descripcion, imagen, idEstado, idTarea) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reporte.getIdPlan());
            stmt.setObject(2, reporte.getFechaReporte() != null ? reporte.getFechaReporte() : LocalDateTime.now());
            stmt.setString(3, reporte.getTipoPlaga());
            stmt.setString(4, reporte.getDescripcion());
            stmt.setString(5, reporte.getImagen());
            stmt.setInt(6, reporte.getIdEstado());
            stmt.setInt(7, reporte.getIdTarea());

            stmt.executeUpdate();
        }
    }

    private Tarea mapear(ResultSet rs) throws SQLException {
        Tarea s = new Tarea();

        s.setIdTarea(rs.getInt("idTarea"));
        s.setIdPlan(rs.getInt("idPlan"));
        s.setNombreTarea(rs.getString("nombreTarea"));

        Date dInicio = rs.getDate("fechaInicio");
        if (dInicio != null) {
            s.setFechaInicio(dInicio.toLocalDate());
        }

        Date dVencimiento = rs.getDate("fechaVencimiento");
        if (dVencimiento != null) {
            s.setFechaVencimiento(dVencimiento.toLocalDate());
        }

        s.setIdEstado(rs.getInt("idEstado"));

        Date dCompletado = rs.getDate("fechaCompletado");
        if (dCompletado != null) {
            s.setFechaCompletado(dCompletado.toLocalDate());
        }

        return s;
    }
}