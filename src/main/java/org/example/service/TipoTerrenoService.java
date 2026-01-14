package org.example.service;
import org.example.model.TipoTerreno;
import org.example.repository.TipoTerrenoRepository;

import java.util.List;

/**
 * Servicio que implementa la lógica de negocio para tipos de terreno.
 * Maneja consultas de tipos de riego disponibles.
 */
public class TipoTerrenoService implements ITipoTerrenoService {

    TipoTerrenoRepository tipoTerrenoRepository = new TipoTerrenoRepository();

    /**
     * Obtiene todos los tipos de terreno disponibles.
     * @return lista completa de tipos de terreno
     */
    @Override
    public List<TipoTerreno> obtenerTipoTerreno() {
        return tipoTerrenoRepository.obtener();
    }

    /**
     * Obtiene un tipo de terreno específico por su ID.
     * @param id el ID del tipo de terreno
     * @return el tipo de terreno encontrado
     */
    @Override
    public TipoTerreno obtenerTipoTerrenoPorId(int id) {
        return tipoTerrenoRepository.obtenerPorId(id);
    }

}
