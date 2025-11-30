package org.example.service;

import org.example.model.PlanCultivo;

import java.util.List;

public interface IProyectosService {
    void actualizarEstadoPlan(int idPlan, int nuevoEstado);
    List<PlanCultivo> obtenerPlanCultivos();
    void actualizarObjetivoObservacion(int idSolicitud, String objetivo, int idPlan, String observaciones);
}
