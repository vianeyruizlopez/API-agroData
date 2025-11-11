package org.example.service;

import org.example.model.Estado;
import org.example.repository.EstadoRepositoryImpl;

import java.util.Arrays;
import java.util.List;

public class EstadoServiceImpl implements EstadoService {

    EstadoRepositoryImpl estadoRepository = new EstadoRepositoryImpl();

    @Override
    public List<Estado> obtenerEstados() {
        return estadoRepository.obtenerEstados();
    }
    public Estado obtenerEstadoPorId(int id) {
        return estadoRepository.obtenerPorId(id);
    }
}
