package org.example.service;

import org.example.model.RegistroActividad;

import java.util.List;

/**
 * Interfaz del servicio para gestionar registros de actividades agrícolas.
 * Define las operaciones de negocio para el seguimiento de actividades.
 */
public interface IRegistroActividadService {
    /**
     * Obtiene todos los registros de actividad.
     * @return Lista de registros de actividad
     */
    List<RegistroActividad> obtenerRegistroActividad();
    /**
     * Agrega un nuevo registro de actividad.
     * @param registroActividad Registro a agregar
     */
    void agregarActividad(RegistroActividad registroActividad);
}
