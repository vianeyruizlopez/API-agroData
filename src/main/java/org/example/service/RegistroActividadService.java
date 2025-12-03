package org.example.service;
import org.example.model.RegistroActividad;
import org.example.repository.RegistroActividadRepository;

import java.util.List;

/**
 * Implementación del servicio para gestionar registros de actividades agrícolas.
 * Maneja la lógica de negocio y validaciones para el seguimiento de actividades.
 */
public class RegistroActividadService implements IRegistroActividadService {
    private final RegistroActividadRepository  registroActividadRepository;

    /**
     * Constructor del servicio de registro de actividades.
     * @param repo Repositorio de registro de actividades
     */
    public RegistroActividadService(RegistroActividadRepository repo) {
        this.registroActividadRepository = repo;
    }

    /**
     * Obtiene todos los registros de actividad disponibles.
     * @return Lista de registros de actividad
     */
    @Override
    public List<RegistroActividad> obtenerRegistroActividad() {
        return registroActividadRepository.obtenerRegistroActividad();
    }

    /**
     * Agrega un nuevo registro de actividad con validaciones.
     * @param registroActividad Registro de actividad a agregar
     * @throws IllegalArgumentException Si los datos no son válidos
     */
    @Override
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
