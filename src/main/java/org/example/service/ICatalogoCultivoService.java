package org.example.service;
import org.example.model.catalogoCultivo;

import java.util.List;

public interface ICatalogoCultivoService {
    List<catalogoCultivo> obtenerCultivos();
    catalogoCultivo obtenerCultivoPorId(int id);
    catalogoCultivo obtenerCultivoPorNombre(String nombre) throws Exception;
    List<catalogoCultivo> obtenerCultivosPorCoincidencia(String parcial);
}
