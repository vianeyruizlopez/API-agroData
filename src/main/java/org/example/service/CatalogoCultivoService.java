package org.example.service;

import org.example.model.catalogoCultivo;
import org.example.repository.CatalogoCultivoRepository;

import java.util.List;

public class CatalogoCultivoService {
    CatalogoCultivoRepository catalogoCultivoRepository = new CatalogoCultivoRepository();

    public List<catalogoCultivo> obtenerCultivos() {
        return catalogoCultivoRepository.obtener();
    }
    public catalogoCultivo obtenerCultivoPorId(int id) {
        return catalogoCultivoRepository.obtenerPorId(id);
    }

    /// //examen////

    public catalogoCultivo obtenerCultivoPorNombre(String nombre) throws Exception {
        catalogoCultivo cultivo = catalogoCultivoRepository.obtenerPorNombre(nombre);
        if (cultivo == null) {
            throw new Exception("Cultivo no encontrado con nombre: " + nombre);
        }
        return cultivo;
    }

    public List<catalogoCultivo> obtenerCultivosPorCoincidencia(String parcial) {
        return catalogoCultivoRepository.obtenerPorCoincidencia(parcial);
    }

}
