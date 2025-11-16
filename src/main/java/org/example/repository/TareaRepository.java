// example/repository/TareaRepository.java
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
        // ... (código sin cambios)
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
        // ... (código sin cambios)
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

    // --- ★ ESTE ES EL MÉTODO CON LA LÓGICA TRANSACCIONAL ★ ---
    public void agregar(Tarea tarea) {
        String sqlTarea = """
        INSERT INTO tarea (
            idPlan,
            nombreTarea,
            fechaInicio,
            fechaVencimiento,
            idEstado,
            fechaCompletado,
            idUsuario
        ) VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        String sqlRelacion = """
        INSERT INTO tareareporteplaga (idReportePlaga, idTarea)
        VALUES (?, ?)
        """;

        Connection conn = null;
        try {
            conn = DataBase.getDataSource().getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            int newIdTarea = -1;

            // 1. Insertar la Tarea principal
            try (PreparedStatement stmtTarea = conn.prepareStatement(sqlTarea, Statement.RETURN_GENERATED_KEYS)) {
                stmtTarea.setInt(1, tarea.getIdPlan());
                stmtTarea.setString(2, tarea.getNombreTarea());
                LocalDate fechaInicio = tarea.getFechaInicio() != null ? tarea.getFechaInicio() : LocalDate.now();
                stmtTarea.setObject(3, fechaInicio);
                stmtTarea.setObject(4, tarea.getFechaVencimiento());
                stmtTarea.setInt(5, tarea.getIdEstado());
                stmtTarea.setObject(6, tarea.getFechaCompletado());
                stmtTarea.setInt(7, tarea.getIdUsuario());
                stmtTarea.executeUpdate();

                try (ResultSet generatedKeys = stmtTarea.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        newIdTarea = generatedKeys.getInt(1);
                        tarea.setIdTarea(newIdTarea);
                    } else {
                        throw new SQLException("Fallo al crear la tarea, no se obtuvo ID.");
                    }
                }
            }

            // 2. Si se proporcionó un idReportePlaga, crear el vínculo
            // ESTA ES LA LÍNEA QUE ESTÁ FALLANDO
            if (tarea.getIdReportePlaga() > 0 && newIdTarea > 0) {
                System.out.println(">>> [DEBUG] Vinculando tarea " + newIdTarea + " con reporte " + tarea.getIdReportePlaga());
                try (PreparedStatement stmtRelacion = conn.prepareStatement(sqlRelacion)) {
                    stmtRelacion.setInt(1, tarea.getIdReportePlaga());
                    stmtRelacion.setInt(2, newIdTarea);
                    stmtRelacion.executeUpdate();
                }
            } else {
                // VERÁS ESTE MENSAJE SI EL ID LLEGA COMO 0
                System.out.println(">>> [DEBUG] Tarea " + newIdTarea + " creada sin vínculo a reporte (idReportePlaga=" + tarea.getIdReportePlaga() + ")");
            }

            conn.commit(); // Confirmar transacción

        } catch (SQLException e) {
            System.err.println("Error en la transacción al agregar tarea/vínculo: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ... (El resto de TareaRepository.java: actualizar, actualizarEstado, eliminar, etc. sin cambios) ...
    public void actualizar(Tarea tarea) {
        String sql = """
        UPDATE tarea SET
            nombreTarea = ?,
            fechaVencimiento = ?,
            idEstado = ?,
            idUsuario = ?
        WHERE idTarea = ?
        """;
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tarea.getNombreTarea());
            stmt.setObject(2, tarea.getFechaVencimiento());
            stmt.setInt(3, tarea.getIdEstado());
            stmt.setInt(4, tarea.getIdUsuario());
            stmt.setInt(5, tarea.getIdTarea());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarEstado(int id, int nuevoEstado) {
        String sql;
        boolean marcarCompletado = (nuevoEstado == 2);

        if (marcarCompletado) {
            sql = "UPDATE tarea SET idEstado = ?, fechaCompletado = ? WHERE idTarea = ?";
        } else {
            sql = "UPDATE tarea SET idEstado = ?, fechaCompletado = NULL WHERE idTarea = ?";
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
             PreparedStatement stmtDep = conn.prepareStatement("DELETE FROM registrotarea WHERE idTarea = ?")) {
            stmtDep.setInt(1, id);
            stmtDep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmtDep = conn.prepareStatement("DELETE FROM tareareporteplaga WHERE idTarea = ?")) {
            stmtDep.setInt(1, id);
            stmtDep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        INNER JOIN tareareporteplaga trp ON t.idTarea = trp.idTarea
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
        String sqlReporte = """
        INSERT INTO reporteplaga (idPlan, fechaReporte, tipoPlaga, descripcion, imagen, idEstado)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

        String sqlRelacion = """
        INSERT INTO tareareporteplaga (idReportePlaga, idTarea)
        VALUES (?, ?)
    """;

        try (Connection conn = DataBase.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtReporte = conn.prepareStatement(sqlReporte, Statement.RETURN_GENERATED_KEYS)) {

                stmtReporte.setInt(1, reporte.getIdPlan());
                stmtReporte.setObject(2, reporte.getFechaReporte() != null ? reporte.getFechaReporte() : LocalDateTime.now());
                stmtReporte.setString(3, reporte.getTipoPlaga());
                stmtReporte.setString(4, reporte.getDescripcion());
                stmtReporte.setString(5, reporte.getImagen());
                stmtReporte.setInt(6, reporte.getIdEstado());

                stmtReporte.executeUpdate();

                int idReportePlaga = -1;
                try (ResultSet generatedKeys = stmtReporte.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idReportePlaga = generatedKeys.getInt(1);
                    }
                }

                if (idReportePlaga > 0 && reporte.getIdTarea() > 0) {
                    System.out.println("Asociando reporte " + idReportePlaga + " con tarea " + reporte.getIdTarea());
                    try (PreparedStatement stmtRelacion = conn.prepareStatement(sqlRelacion)) {
                        stmtRelacion.setInt(1, idReportePlaga);
                        stmtRelacion.setInt(2, reporte.getIdTarea());
                        stmtRelacion.executeUpdate();
                    }
                } else {
                    System.out.println("Reporte de plaga " + idReportePlaga + " registrado sin tarea asociada (idTarea=" + reporte.getIdTarea() + ")");
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error al registrar el reporte de plaga", e);
            }
        }
    }

    private Tarea mapear(ResultSet rs) throws SQLException {
        Tarea s = new Tarea();

        s.setIdTarea(rs.getInt("idTarea"));
        s.setIdPlan(rs.getInt("idPlan"));
        s.setNombreTarea(rs.getString("nombreTarea"));
        s.setIdUsuario(rs.getInt("idUsuario"));

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