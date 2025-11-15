package org.example.repository;

import org.example.config.DataBase;
import org.example.model.ReporteDesempeño;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReporteDesempeñoRepository {

    public ReporteDesempeñoRepository() {}

    public ReporteDesempeño obtenerReporteDesempeñoPorIdPlan(int idPlan) {
        System.out.println("→ Buscando reporte de desempeño para idPlan: " + idPlan);
        ReporteDesempeño reporteDesempeño = null;

        // --- ★ CORRECCIÓN SQL ★ ---
        // Se actualiza la consulta para usar IDs de estado (1=Pendiente, 2=Completada)
        // y se añade el cálculo de 'tareasAtrasadas'.
        String sqlDesempeño = """
            SELECT
                COUNT(*) AS totalTareas,
                SUM(CASE WHEN tarea.idEstado = 2 THEN 1 ELSE 0 END) AS tareasCompletadas,
                0 AS tareasAceptadas, -- Campo 'Aceptada' ya no se usa, se deja en 0
                SUM(CASE WHEN tarea.idEstado = 1 AND tarea.fechaVencimiento >= CURDATE() THEN 1 ELSE 0 END) AS tareasPendientes,
                SUM(CASE WHEN tarea.idEstado = 1 AND tarea.fechaVencimiento < CURDATE() THEN 1 ELSE 0 END) AS tareasAtrasadas,
                CASE 
                    WHEN COUNT(*) = 0 THEN 0
                    ELSE ROUND(
                        SUM(CASE WHEN tarea.idEstado = 2 THEN 1 ELSE 0 END) * 100.0 / COUNT(*),
                        2
                    )
                END AS porcentajeCompletadas
            FROM tarea
            WHERE tarea.idPlan = ?
        """;

        String sqlReporte = """
            SELECT idReporte, fechaGeneracion, observaciones
            FROM reportedesempeño
            WHERE idPlan = ?
            ORDER BY fechaGeneracion DESC
            LIMIT 1
        """;

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt1 = conn.prepareStatement(sqlDesempeño);
             PreparedStatement stmt2 = conn.prepareStatement(sqlReporte)) {

            stmt1.setInt(1, idPlan);
            ResultSet rs1 = stmt1.executeQuery();

            if (rs1.next()) {
                reporteDesempeño = mapearReporteDesempeño(rs1); // Mapear los nuevos campos
                System.out.println("→ Reporte calculado: " + reporteDesempeño);
            } else {
                System.out.println("⚠️ No se encontró desempeño para idPlan: " + idPlan);
                return null;
            }

            stmt2.setInt(1, idPlan);
            ResultSet rs2 = stmt2.executeQuery();

            if (rs2.next()) {
                reporteDesempeño.setIdReporte(rs2.getInt("idReporte"));
                reporteDesempeño.setIdPlan(idPlan);
                reporteDesempeño.setFechaGeneracion(rs2.getTimestamp("fechaGeneracion").toLocalDateTime());
                reporteDesempeño.setObservaciones(rs2.getString("observaciones"));
                System.out.println("→ Datos registrados complementados");
            } else {
                System.out.println("⚠️ No hay reporte registrado para idPlan: " + idPlan);
                reporteDesempeño.setIdReporte(0);
                reporteDesempeño.setIdPlan(idPlan);
                reporteDesempeño.setFechaGeneracion(null);
                reporteDesempeño.setObservaciones(null);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener reporte de desempeño:");
            e.printStackTrace();
        }

        return reporteDesempeño;
    }

    // --- (Método 'registrarReporteDesempeño' sin cambios) ---
    public void registrarReporteDesempeño(int idPlan, LocalDateTime fechaGeneracion, String observaciones) throws SQLException {
        String sql = "INSERT INTO reportedesempeño (idPlan, fechaGeneracion, observaciones) VALUES (?, ?, ?)";

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPlan);
            stmt.setObject(2, fechaGeneracion);
            stmt.setString(3, observaciones);
            stmt.executeUpdate();

            System.out.println("✅ Reporte registrado para idPlan: " + idPlan + " con fecha: " + fechaGeneracion);

        } catch (SQLException e) {
            System.err.println("❌ Error al registrar reporte de desempeño:");
            throw e;
        }
    }


    private ReporteDesempeño mapearReporteDesempeño(ResultSet rs) throws SQLException {
        ReporteDesempeño reporte = new ReporteDesempeño();
        reporte.setTotalTareas(rs.getInt("totalTareas"));
        reporte.setTareasCompletadas(rs.getInt("tareasCompletadas"));
        reporte.setTareasAceptadas(rs.getInt("tareasAceptadas")); // Se mapea (será 0)
        reporte.setTareasPendientes(rs.getInt("tareasPendientes"));
        reporte.setTareasAtrasadas(rs.getInt("tareasAtrasadas")); // <-- ★ NUEVO CAMPO MAPEADO ★
        reporte.setPorcentageCompletadas(rs.getFloat("porcentajeCompletadas")); // <-- ★ CORREGIDO a getFloat ★

        System.out.println("→ Mapeo completo: " +
                "total=" + reporte.getTotalTareas() +
                ", completadas=" + reporte.getTareasCompletadas() +
                ", aceptadas=" + reporte.getTareasAceptadas() +
                ", pendientes=" + reporte.getTareasPendientes() +
                ", atrasadas=" + reporte.getTareasAtrasadas() + // <-- ★ AÑADIDO A LOG ★
                ", %=" + reporte.getPorcentageCompletadas());

        return reporte;
    }
}