package org.example.service;

import org.example.model.Estado;
import org.example.repository.EstadoRepositoryImpl;

import java.util.List;

/**
 * Servicio que implementa la lógica de negocio para estados.
 * Maneja consultas de estados del sistema.
 */
public class EstadoService implements IEstadoService {

    EstadoRepositoryImpl estadoRepository = new EstadoRepositoryImpl();

    /**
     * Obtiene todos los estados disponibles.
     * @return lista completa de estados
     */
    @Override
    public List<Estado> obtenerEstados() {
        return estadoRepository.obtenerEstados();
    }
    /**
     * Obtiene un estado específico por su ID.
     * @param id el ID del estado
     * @return el estado encontrado o null
     */
    @Override
    public Estado obtenerEstadoPorId(int id) {
        return estadoRepository.obtenerPorId(id);
    }
}
