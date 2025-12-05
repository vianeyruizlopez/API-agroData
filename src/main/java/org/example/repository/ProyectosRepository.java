package org.example.repository;

import org.example.config.DataBase;
import org.example.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar proyectos y planes de cultivo.
 * Maneja las operaciones CRUD relacionadas con planes de cultivo, reportes de plaga y cultivos por solicitud.
 */
public class ProyectosRepository {

    /**
     * Constructor por defecto del repositorio de proyectos.
     */
    public ProyectosRepository() {
    }

    /**
     * Obtiene todos los planes de cultivo con información completa.
     * @return Lista de planes de cultivo con datos del agricultor y estadísticas
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public List<PlanCultivo> obtenerPlanCultivos() {
        List<PlanCultivo> planCultivoList = new ArrayList<>();
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("select * from plandecultivo \n" +
                     "inner join solicitudasesoria on plandecultivo.idSolicitud = solicitudasesoria.idSolicitud \n" +
                     "inner join usuario on solicitudasesoria.idAgricultor = usuario.idUsuario")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                planCultivoList.add(mapearPlanCultivo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planCultivoList;
    }

    /**
     * Actualiza el estado de un plan de cultivo.
     * @param idPlan ID del plan a actualizar
     * @param nuevoEstado Nuevo estado del plan
     * @throws RuntimeException Si no se encuentra el plan o hay error en la actualización
     */
    public void actualizarEstadoPlan(int idPlan, int nuevoEstado) {
        String sql = "UPDATE plandecultivo SET idEstado = ? WHERE idPlan = ?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nuevoEstado);
            stmt.setInt(2, idPlan);

            int filas = stmt.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se encontró el plan con ID: " + idPlan);
            }
            System.out.println("✅ Estado del Plan " + idPlan + " actualizado a " + nuevoEstado);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar estado del plan", e);
        }
    }

    /**
     * Obtiene los cultivos asociados a una solicitud específica.
     * @param idSolicitud ID de la solicitud
     * @return Lista de cultivos por solicitud
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public List<CultivoPorSolicitud> obtenerCultivosPorSolicitud(int idSolicitud) {
        List<CultivoPorSolicitud> cultivoPorSolicitudList = new ArrayList<>();
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("select * from cultivoporsolicitud inner join catalogocultivo on cultivoporsolicitud.idCultivo = catalogocultivo.idCultivo where cultivoporsolicitud.idSolicitud = ?")) {
            stmt.setInt(1, idSolicitud);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cultivoPorSolicitudList.add(mapearCultivoPorSolicitud(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cultivoPorSolicitudList;
    }

    /**
     * Obtiene los reportes de plaga asociados a un plan específico.
     * @param idPlan ID del plan de cultivo
     * @return Lista de reportes de plaga
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public List<ReportePlaga> obtenerReportePlagas(int idPlan) {
        List<ReportePlaga> reportePlagaList = new ArrayList<>();
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("select * from reporteplaga where idPlan = ?")) {
            stmt.setInt(1, idPlan);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reportePlagaList.add(mapearReportePlaga(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportePlagaList;
    }

    /**
     * Cuenta el total de tareas y tareas completadas para un plan.
     * @param idEstado Estado de las tareas completadas
     * @param idPlan ID del plan de cultivo
     * @return Array con [totalTareas, tareasCompletas]
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public int[] contarRegistros(int idEstado, int idPlan) {
        int[] registros = new int[2];
        String sql = "SELECT \n" +
                "  COUNT(*) AS totalTareas,\n" +
                "  SUM(CASE WHEN tarea.idEstado = ? THEN 1 ELSE 0 END) AS totalTareasCompletas\n" +
                "FROM tarea\n" +
                "INNER JOIN registrotarea ON tarea.idTarea = registrotarea.idTarea\n" +
                "WHERE tarea.idPlan = ?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEstado);
            stmt.setInt(2, idPlan);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    registros[0] = rs.getInt("totalTareas");
                    registros[1] = rs.getInt("totalTareasCompletas");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registros;
    }

    /**
     * Actualiza el objetivo y observaciones de un plan de cultivo.
     * @param idSolicitud ID de la solicitud de asesoría
     * @param objetivo Nuevo objetivo del plan
     * @param idPlan ID del plan de cultivo
     * @param observaciones Nuevas observaciones del plan
     * @throws RuntimeException Si hay error en la transacción
     */
    public void actualizarObjetivoYObservaciones(int idSolicitud, String objetivo, int idPlan, String observaciones) {
        String sqlObjetivo = "UPDATE solicitudasesoria SET motivoAsesoria = ? WHERE idSolicitud = ?";
        String sqlObservaciones = "UPDATE plandecultivo SET observaciones = ? WHERE idPlan = ? AND idSolicitud = ?";
        Connection conn = null;

        try {
            conn = DataBase.getDataSource().getConnection();
            conn.setAutoCommit(false);


            try (PreparedStatement stmtObjetivo = conn.prepareStatement(sqlObjetivo)) {
                stmtObjetivo.setString(1, objetivo);
                stmtObjetivo.setInt(2, idSolicitud);
                int filasObjetivo = stmtObjetivo.executeUpdate();
                if (filasObjetivo == 0) {
                    throw new SQLException("No se encontró la solicitud con id: " + idSolicitud);
                }
            }

            try (PreparedStatement stmtObs = conn.prepareStatement(sqlObservaciones)) {
                stmtObs.setString(1, observaciones);
                stmtObs.setInt(2, idPlan);
                stmtObs.setInt(3, idSolicitud);

                int filasObs = stmtObs.executeUpdate();
                if (filasObs == 0) {
                    throw new SQLException("No se encontró el plan con id: " + idPlan + " y solicitud: " + idSolicitud);
                }
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Error al hacer rollback: " + rollbackEx.getMessage());
                }
            }
            throw new RuntimeException("Error al actualizar objetivo y observaciones", e);
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

    /**
     * Mapea un ResultSet a un objeto ReportePlaga.
     * @param rs ResultSet con los datos del reporte
     * @return Objeto ReportePlaga mapeado
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private ReportePlaga mapearReportePlaga(ResultSet rs) throws SQLException {
        ReportePlaga reportePlaga = new ReportePlaga();
        reportePlaga.setIdReportePlaga(rs.getInt("idReportePlaga"));
        reportePlaga.setIdPlan(rs.getInt("idPlan"));

        java.sql.Timestamp ts = rs.getTimestamp("fechaReporte");
        if (ts != null) {
            reportePlaga.setFechaReporte(ts.toLocalDateTime());
        }

        reportePlaga.setTipoPlaga(rs.getString("tipoPlaga"));
        reportePlaga.setDescripcion(rs.getString("descripcion"));
        reportePlaga.setImagen(rs.getString("imagen"));
        reportePlaga.setIdEstado(rs.getInt("idEstado"));
        //reportePlaga.setIdTarea(rs.getInt("idTarea"));
        return reportePlaga;
    }

    /**
     * Mapea un ResultSet a un objeto CultivoPorSolicitud.
     * @param rs ResultSet con los datos del cultivo
     * @return Objeto CultivoPorSolicitud mapeado
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private CultivoPorSolicitud mapearCultivoPorSolicitud(ResultSet rs) throws SQLException {
        CultivoPorSolicitud cultivoPorSolicitud = new CultivoPorSolicitud();
        cultivoPorSolicitud.setIdSolicitud(rs.getInt("idSolicitud"));
        cultivoPorSolicitud.setIdCultivo(rs.getInt("idCultivo"));
        cultivoPorSolicitud.setNombreCultivo(rs.getString("nombreCultivo"));
        return  cultivoPorSolicitud;
    }

    /**
     * Mapea un ResultSet a un objeto PlanCultivo completo.
     * @param rs ResultSet con los datos del plan
     * @return Objeto PlanCultivo mapeado con cultivos y reportes
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private PlanCultivo mapearPlanCultivo(ResultSet rs) throws SQLException {
        PlanCultivo planCultivo = new PlanCultivo();
        planCultivo.setIdPlan(rs.getInt("idPlan"));
        planCultivo.setIdSolicitud(rs.getInt("idSolicitud"));
        planCultivo.setIdUsuario(rs.getInt("idUsuario"));
        planCultivo.setIdEstado(rs.getInt("idEstado"));
        planCultivo.setNombre(rs.getString("nombre"));
        planCultivo.setApellidoPaterno(rs.getString("apellidoPaterno"));
        planCultivo.setApellidoMaterno(rs.getString("apellidoMaterno"));
        planCultivo.setDireccionTerreno(rs.getString("direccionTerreno"));
        planCultivo.setMotivoAsesoria(rs.getString("motivoAsesoria"));
        planCultivo.setSuperficieTotal(rs.getFloat("superficieTotal"));
        planCultivo.setObservaciones(rs.getString("observaciones"));
        java.sql.Date dInicio = rs.getDate("fechaInicio");
        if (dInicio != null) {
            planCultivo.setFechaInicio(dInicio.toLocalDate());
        }

        java.sql.Date dFin = rs.getDate("fechaFin");
        if (dFin != null) {
            planCultivo.setFechaFin(dFin.toLocalDate());
        }

        int[] registros = contarRegistros(rs.getInt("idEstado"), rs.getInt("idPlan"));
        planCultivo.setTotalTareas(registros[0]);
        planCultivo.setTotalTareasCompletas(registros[1]);
        float porcentajeAvance = (registros[0] > 0) ? (registros[1] * 100 / registros[0]) : 0;
        planCultivo.setPorcentajeAvance(porcentajeAvance);

        planCultivo.setCultivoPorSolicitud(obtenerCultivosPorSolicitud(rs.getInt("idSolicitud")));
        planCultivo.setReportePlagas(obtenerReportePlagas(rs.getInt("idPlan")));
        return planCultivo;
    }
    /**
     * Registra un nuevo reporte de plaga en la base de datos.
     * @param reporte Objeto ReportePlaga a registrar
     * @throws SQLException Si ocurre un error en la inserción
     */
    public void registrarReportePlaga(ReportePlaga reporte) throws SQLException {
        String sql = "INSERT INTO reporteplaga (idPlan, fechaReporte, tipoPlaga, descripcion, imagen, idEstado, idTarea) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reporte.getIdPlan());

            LocalDateTime fecha = (reporte.getFechaReporte() != null) ? reporte.getFechaReporte() : LocalDateTime.now();
            stmt.setObject(2, fecha);

            stmt.setString(3, reporte.getTipoPlaga());
            stmt.setString(4, reporte.getDescripcion());
            stmt.setString(5, reporte.getImagen());
            stmt.setInt(6, reporte.getIdEstado());
            stmt.setInt(7, reporte.getIdTarea());

            stmt.executeUpdate();
        }
    }

        /**
         * Crea automáticamente un plan de cultivo desde una solicitud de asesoría.
         * @param idSolicitud ID de la solicitud de asesoría
         * @throws RuntimeException Si hay error al crear el plan
         */
        public void crearPlanCultivoDesdeSolicitud(int idSolicitud) {
            String sql = "INSERT INTO plandecultivo (idSolicitud, idEstado, fechaInicio, observaciones) VALUES (?, ?, CURDATE(), ?)";

            try (Connection conn = DataBase.getDataSource().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idSolicitud);
                stmt.setInt(2, 1);
                stmt.setString(3, "Plan generado automáticamente. Por favor, complete los detalles.");

                stmt.executeUpdate();
                System.out.println(" Plan de cultivo generado automáticamente para la solicitud: " + idSolicitud);

            } catch (SQLException e) {
                System.err.println(" Error al crear plan de cultivo automático:");
                e.printStackTrace();
                throw new RuntimeException("Error al crear el plan de cultivo", e);
            }
        }
    }
