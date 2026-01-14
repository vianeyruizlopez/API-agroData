package org.example.service;
import org.example.model.catalogoCultivo;

import java.util.List;

/**
 * Interfaz que define servicios para el catálogo de cultivos.
 * Permite consultar cultivos por diferentes criterios.
 */
public interface ICatalogoCultivoService {
    /**
     * Obtiene todos los cultivos disponibles.
     * @return lista de todos los cultivos
     */
    List<catalogoCultivo> obtenerCultivos();
    /**
     * Obtiene un cultivo por su ID.
     * @param id el ID del cultivo
     * @return el cultivo encontrado o null
     */
    catalogoCultivo obtenerCultivoPorId(int id);
    /**
     * Obtiene un cultivo por su nombre exacto.
     * @param nombre el nombre del cultivo
     * @return el cultivo encontrado
     * @throws Exception si no se encuentra el cultivo
     */
    catalogoCultivo obtenerCultivoPorNombre(String nombre) throws Exception;
    /**
     * Busca cultivos que coincidan parcialmente con el término.
     * @param parcial término de búsqueda parcial
     * @return lista de cultivos que coinciden
     */
    List<catalogoCultivo> obtenerCultivosPorCoincidencia(String parcial);
}
