package org.example.repository;

import org.example.config.DataBase;
import org.example.model.ReporteDesempeño;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class ReporteDesempeñoRepository {

    public ReporteDesempeñoRepository() {
    }

    public ReporteDesempeño obtenerReporteDesempeñoPorIdPlan(int idPlan) {
        ReporteDesempeño reporteDesempeño = new ReporteDesempeño();
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT\n" +
                     "    COUNT(*) AS totalTareas,\n" +
                     "    SUM(CASE WHEN catalogoestado.nombreEstado = 'completada' THEN 1 ELSE 0 END) AS tareasCompletadas,\n" +
                     "    SUM(CASE WHEN catalogoestado.nombreEstado = 'Aceptada' THEN 1 ELSE 0 END) AS tareasAceptadas,\n" +
                     "    SUM(CASE WHEN catalogoestado.nombreEstado = 'Pendiente' THEN 1 ELSE 0 END) AS tareasPendientes,\n" +
                     "    ROUND(\n" +
                     "        SUM(CASE WHEN catalogoestado.nombreEstado = 'completada' THEN 1 ELSE 0 END) * 100.0 / COUNT(*),\n" +
                     "        2\n" +
                     "    ) AS porcentajeCompletadas\n" +
                     "FROM \n" +
                     "    tarea\n" +
                     "INNER JOIN registrotarea ON tarea.idTarea = registrotarea.idTarea\n" +
                     "INNER JOIN catalogoestado ON tarea.idEstado = catalogoestado.idEstado\n" +
                     "WHERE tarea.idPlan = ?")) {
            stmt.setInt(1, idPlan);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                reporteDesempeño = mapearReporteDesempeño(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reporteDesempeño;
    }

    public void registrarReporteDesempeño(int idPlan, LocalDateTime fechaGeneracion, String observaciones) throws SQLException {
        String sql ="INSERT INTO reportedesempeño (idPlan, fechaGeneracion, observaciones) VALUES (?, ?, ?)";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPlan);
            stmt.setObject(2, fechaGeneracion);
            stmt.setString(3, observaciones);
            stmt.executeUpdate();
        }
    }

    private ReporteDesempeño mapearReporteDesempeño(ResultSet rs) throws SQLException {
        ReporteDesempeño reporteDesempeño = new ReporteDesempeño();
        reporteDesempeño.setTotalTareas(rs.getInt("totalTareas"));
        reporteDesempeño.setTareasCompletadas(rs.getInt("tareasCompletadas"));
        reporteDesempeño.setTareasAceptadas(rs.getInt("tareasAceptadas"));
        reporteDesempeño.setTareasPendientes(rs.getInt("tareasPendientes"));
        reporteDesempeño.setPorcentageCompletadas(rs.getInt("porcentajeCompletadas"));
        return reporteDesempeño;
    }
}
