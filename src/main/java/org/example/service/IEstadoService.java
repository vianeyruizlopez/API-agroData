package org.example.service;
import org.example.model.Estado;

import java.util.List;

public interface IEstadoService {
    List<Estado> obtenerEstados();
    Estado obtenerEstadoPorId(int id);
}
