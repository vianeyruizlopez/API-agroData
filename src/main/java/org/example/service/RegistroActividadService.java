package org.example.service;
import org.example.model.RegistroActividad;
import org.example.repository.RegistroActividadRepository;

import java.util.List;

public class RegistroActividadService {
    private final RegistroActividadRepository  registroActividadRepository;
    public RegistroActividadService(RegistroActividadRepository repo) {
        this.registroActividadRepository = repo;
    }
    public List<RegistroActividad> obtenerRegistroActividad() {
        return registroActividadRepository.obtenerRegistroActividad();
    }
    public void agregarActividad(RegistroActividad registroActividad) {
        if (registroActividad.getDescripcion()==null || registroActividad.getDescripcion().isBlank()){
            throw new IllegalArgumentException("La descripcion no puede estar vacia");
        }
        if (registroActividad.getIdTarea() <= 0) {
            throw new IllegalArgumentException("Debes asociar la actividad a una tarea válida");
        }


        registroActividadRepository.agregarActividad(registroActividad);
    }
}
