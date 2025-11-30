package org.example.service;
import org.example.model.TipoTerreno;
import org.example.repository.TipoTerrenoRepository;

import java.util.List;

public class TipoTerrenoService implements ITipoTerrenoService {

    TipoTerrenoRepository tipoTerrenoRepository = new TipoTerrenoRepository();

    @Override
    public List<TipoTerreno> obtenerTipoTerreno() {
        return tipoTerrenoRepository.obtener();
    }

    @Override
    public TipoTerreno obtenerTipoTerrenoPorId(int id) {
        return tipoTerrenoRepository.obtenerPorId(id);
    }

}
