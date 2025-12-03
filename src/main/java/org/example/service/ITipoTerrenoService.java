package org.example.service;

import org.example.model.TipoTerreno;
import java.util.List;

/**
 * Interfaz que define servicios para tipos de terreno.
 * Maneja consultas de tipos de riego disponibles.
 */
public interface ITipoTerrenoService {
    /**
     * Obtiene todos los tipos de terreno disponibles.
     * @return lista de tipos de terreno
     */
    List<TipoTerreno> obtenerTipoTerreno();
    /**
     * Obtiene un tipo de terreno por su ID.
     * @param id el ID del tipo de terreno
     * @return el tipo de terreno encontrado
     */
    TipoTerreno obtenerTipoTerrenoPorId(int id);
}
