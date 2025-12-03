package org.example.service;

import org.example.model.catalogoCultivo;
import org.example.repository.CatalogoCultivoRepository;

import java.util.List;

/**
 * Servicio que implementa la lógica de negocio para el catálogo de cultivos.
 * Maneja consultas y búsquedas de cultivos.
 */
public class CatalogoCultivoService implements ICatalogoCultivoService{
    CatalogoCultivoRepository catalogoCultivoRepository = new CatalogoCultivoRepository();

    /**
     * Obtiene todos los cultivos del catálogo.
     * @return lista completa de cultivos
     */
    @Override
    public List<catalogoCultivo> obtenerCultivos() {
        return catalogoCultivoRepository.obtener();
    }
    /**
     * Obtiene un cultivo específico por su ID.
     * @param id el ID del cultivo
     * @return el cultivo encontrado o null
     */
    @Override
    public catalogoCultivo obtenerCultivoPorId(int id) {
        return catalogoCultivoRepository.obtenerPorId(id);
    }

    /**
     * Obtiene un cultivo por su nombre exacto.
     * @param nombre el nombre del cultivo
     * @return el cultivo encontrado
     * @throws Exception si no existe el cultivo
     */
    @Override
    public catalogoCultivo obtenerCultivoPorNombre(String nombre) throws Exception {
        catalogoCultivo cultivo = catalogoCultivoRepository.obtenerPorNombre(nombre);
        if (cultivo == null) {
            throw new Exception("Cultivo no encontrado con nombre: " + nombre);
        }
        return cultivo;
    }
    /**
     * Busca cultivos que contengan el término de búsqueda.
     * @param parcial término de búsqueda
     * @return lista de cultivos que coinciden
     */
    @Override
    public List<catalogoCultivo> obtenerCultivosPorCoincidencia(String parcial) {
        return catalogoCultivoRepository.obtenerPorCoincidencia(parcial);
    }

}
