package org.example.service;

import org.example.model.ReporteDesempeno;
import org.example.repository.ReporteDesempenoRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Implementación del servicio para gestionar reportes de desempeño.
 * Maneja la lógica de negocio para el seguimiento y evaluación de planes de cultivo.
 */
public class ReporteDesempenoService implements IReporteDesempenoService {

    private final ReporteDesempenoRepository repository = new ReporteDesempenoRepository();

    /**
     * Constructor por defecto del servicio de reportes de desempeño.
     */
    public ReporteDesempenoService() {
    }

    /**
     * Obtiene el reporte de desempeño para un plan de cultivo específico.
     * @param idPlan ID del plan de cultivo
     * @return Reporte de desempeño con estadísticas de tareas
     * @throws SQLException Si ocurre un error en la consulta
     */
    @Override
    public ReporteDesempeno obtenerReporteDesempeñoPorIdPlan(int idPlan) throws SQLException {
        return repository.obtenerReporteDesempeñoPorIdPlan(idPlan);
    }

    /**
     * Registra un nuevo reporte de desempeño en la base de datos.
     * @param idPlan ID del plan de cultivo
     * @param fechaGeneracion Fecha de generación del reporte
     * @param observaciones Observaciones del reporte
     * @throws SQLException Si ocurre un error en el registro
     */
    @Override
    public void registrarReporteDesempeño(int idPlan, LocalDateTime fechaGeneracion, String observaciones) throws SQLException {
        repository.registrarReporteDesempeño(idPlan, fechaGeneracion, observaciones);
    }
}
