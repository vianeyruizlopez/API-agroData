package org.example.repository;

import org.example.model.Estado;

import java.util.List;

/**
 * Interfaz que define operaciones de base de datos para estados.
 * Los estados se usan en solicitudes, tareas y talleres.
 */
public interface EstadoRepository {
    /**
     * Obtiene todos los estados del sistema.
     * @return lista de todos los estados disponibles
     */
    public List<Estado> obtenerEstados();
}
