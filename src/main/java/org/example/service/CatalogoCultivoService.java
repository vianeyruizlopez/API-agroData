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
}
