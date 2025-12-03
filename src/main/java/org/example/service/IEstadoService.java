package org.example.service;
import org.example.model.Estado;

import java.util.List;

/**
 * Interfaz que define servicios para manejar estados del sistema.
 * Los estados se usan en solicitudes, tareas y talleres.
 */
public interface IEstadoService {
    /**
     * Obtiene todos los estados del sistema.
     * @return lista de todos los estados
     */
    List<Estado> obtenerEstados();
    /**
     * Obtiene un estado por su ID.
     * @param id el ID del estado
     * @return el estado encontrado o null
     */
    Estado obtenerEstadoPorId(int id);
}
