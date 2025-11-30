package org.example.service;

import org.example.model.PlanCultivo;
import org.example.repository.ProyectosRepository;

import java.util.List;

public class ProyectosService implements IProyectosService{
    private final ProyectosRepository proyectosRepository = new ProyectosRepository();

    public ProyectosService() {
    }

    @Override
    public void actualizarEstadoPlan(int idPlan, int nuevoEstado) {
        proyectosRepository.actualizarEstadoPlan(idPlan, nuevoEstado);
    }

    @Override
    public List<PlanCultivo> obtenerPlanCultivos(){
        return proyectosRepository.obtenerPlanCultivos();
    }

    @Override
    public void actualizarObjetivoObservacion(int idSolicitud, String objetivo, int idPlan, String observaciones){
        proyectosRepository.actualizarObjetivoYObservaciones(idSolicitud,objetivo,idPlan,observaciones);
    }
}
