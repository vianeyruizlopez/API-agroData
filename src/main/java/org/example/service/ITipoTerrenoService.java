package org.example.service;

import org.example.model.TipoTerreno;
import java.util.List;

public interface ITipoTerrenoService {
    List<TipoTerreno> obtenerTipoTerreno();
    TipoTerreno obtenerTipoTerrenoPorId(int id);
}
