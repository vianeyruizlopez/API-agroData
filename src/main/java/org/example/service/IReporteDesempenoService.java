package org.example.service;

import org.example.model.ReporteDesempeno;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Interfaz del servicio para gestionar reportes de desempeño.
 * Define las operaciones de negocio para el seguimiento de planes de cultivo.
 */
public interface IReporteDesempenoService {
    /**
     * Obtiene el reporte de desempeño de un plan.
     * @param idPlan ID del plan de cultivo
     * @return Reporte de desempeño
     * @throws SQLException Si hay error en la consulta
     */
    ReporteDesempeno obtenerReporteDesempeñoPorIdPlan(int idPlan) throws SQLException;
    /**
     * Registra un nuevo reporte de desempeño.
     * @param idPlan ID del plan
     * @param fechaGeneracion Fecha de generación
     * @param observaciones Observaciones del reporte
     * @throws SQLException Si hay error en el registro
     */
    void registrarReporteDesempeño(int idPlan, LocalDateTime fechaGeneracion, String observaciones) throws SQLException;
}
