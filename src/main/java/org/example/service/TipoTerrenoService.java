package org.example.service;
import org.example.model.TipoTerreno;
import org.example.repository.TipoTerrenoRepository;

import java.util.List;

public class TipoTerrenoService {

    TipoTerrenoRepository tipoTerrenoRepository = new TipoTerrenoRepository();

    public List<TipoTerreno> obtenerTipoTerreno() {
        return tipoTerrenoRepository.obtener();
    }
    public TipoTerreno obtenerTipoTerrenoPorId(int id) {
        return tipoTerrenoRepository.obtenerPorId(id);
    }

}
