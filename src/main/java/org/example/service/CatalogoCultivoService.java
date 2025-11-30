package org.example.service;

import org.example.model.catalogoCultivo;
import org.example.repository.CatalogoCultivoRepository;

import java.util.List;

public class CatalogoCultivoService implements ICatalogoCultivoService{
    CatalogoCultivoRepository catalogoCultivoRepository = new CatalogoCultivoRepository();

    @Override
    public List<catalogoCultivo> obtenerCultivos() {
        return catalogoCultivoRepository.obtener();
    }
    @Override
    public catalogoCultivo obtenerCultivoPorId(int id) {
        return catalogoCultivoRepository.obtenerPorId(id);
    }

    /// //examen////
    @Override
    public catalogoCultivo obtenerCultivoPorNombre(String nombre) throws Exception {
        catalogoCultivo cultivo = catalogoCultivoRepository.obtenerPorNombre(nombre);
        if (cultivo == null) {
            throw new Exception("Cultivo no encontrado con nombre: " + nombre);
        }
        return cultivo;
    }
    @Override
    public List<catalogoCultivo> obtenerCultivosPorCoincidencia(String parcial) {
        return catalogoCultivoRepository.obtenerPorCoincidencia(parcial);
    }

}
