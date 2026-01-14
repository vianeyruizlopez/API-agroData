package org.example.service;

import org.example.model.PlanCultivo;
import org.example.repository.ProyectosRepository;

import java.util.List;

/**
 * Implementación del servicio para gestionar proyectos y planes de cultivo.
 * Maneja la lógica de negocio relacionada con planes agrícolas.
 */
public class ProyectosService implements IProyectosService{
    private final ProyectosRepository proyectosRepository = new ProyectosRepository();

    /**
     * Constructor por defecto del servicio de proyectos.
     */
    public ProyectosService() {
    }

    /**
     * Actualiza el estado de un plan de cultivo.
     * @param idPlan ID del plan a actualizar
     * @param nuevoEstado Nuevo estado del plan
     */
    @Override
    public void actualizarEstadoPlan(int idPlan, int nuevoEstado) {
        proyectosRepository.actualizarEstadoPlan(idPlan, nuevoEstado);
    }

    /**
     * Obtiene todos los planes de cultivo disponibles.
     * @return Lista de planes de cultivo con información completa
     */
    @Override
    public List<PlanCultivo> obtenerPlanCultivos(){
        return proyectosRepository.obtenerPlanCultivos();
    }

    /**
     * Actualiza el objetivo y observaciones de un plan de cultivo.
     * @param idSolicitud ID de la solicitud de asesoría
     * @param objetivo Nuevo objetivo del plan
     * @param idPlan ID del plan de cultivo
     * @param observaciones Nuevas observaciones del plan
     */
    @Override
    public void actualizarObjetivoObservacion(int idSolicitud, String objetivo, int idPlan, String observaciones){
        proyectosRepository.actualizarObjetivoYObservaciones(idSolicitud,objetivo,idPlan,observaciones);
    }
}
