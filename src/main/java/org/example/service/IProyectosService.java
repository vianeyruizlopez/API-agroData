package org.example.service;

import org.example.model.PlanCultivo;

import java.util.List;

/**
 * Interfaz del servicio para gestionar proyectos y planes de cultivo.
 * Define las operaciones de negocio relacionadas con planes agrícolas.
 */
public interface IProyectosService {
    /**
     * Actualiza el estado de un plan de cultivo.
     * @param idPlan ID del plan a actualizar
     * @param nuevoEstado Nuevo estado del plan
     */
    void actualizarEstadoPlan(int idPlan, int nuevoEstado);
    /**
     * Obtiene todos los planes de cultivo.
     * @return Lista de planes de cultivo
     */
    List<PlanCultivo> obtenerPlanCultivos();
    /**
     * Actualiza el objetivo y observaciones de un plan.
     * @param idSolicitud ID de la solicitud
     * @param objetivo Nuevo objetivo
     * @param idPlan ID del plan
     * @param observaciones Nuevas observaciones
     */
    void actualizarObjetivoObservacion(int idSolicitud, String objetivo, int idPlan, String observaciones);
}
