package org.example.service;

import org.example.model.PlanCultivo;
import org.example.repository.ProyectosRepository;

import java.util.List;

public class ProyectosService {
    private ProyectosRepository proyectosRepository = new ProyectosRepository();

    public ProyectosService() {
    }

    public List<PlanCultivo> obtenerPlanCultivos(){
        return proyectosRepository.obtenerPlanCultivos();
    }

    public void actualizarObjetivoObservacion(int idSolicitud, String objetivo, int idPlan, String observaciones){
        proyectosRepository.actualizarObjetivoYObservaciones(idSolicitud,objetivo,idPlan,observaciones);
    }
}
