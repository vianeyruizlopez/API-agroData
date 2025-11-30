package org.example.service;

import org.example.model.Estado;
import org.example.repository.EstadoRepositoryImpl;

import java.util.List;

public class EstadoService implements IEstadoService {

    EstadoRepositoryImpl estadoRepository = new EstadoRepositoryImpl();

    @Override
    public List<Estado> obtenerEstados() {
        return estadoRepository.obtenerEstados();
    }
    @Override
    public Estado obtenerEstadoPorId(int id) {
        return estadoRepository.obtenerPorId(id);
    }
}
