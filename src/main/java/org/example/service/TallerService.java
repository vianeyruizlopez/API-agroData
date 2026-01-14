package org.example.service;
import org.example.model.Taller;
import org.example.repository.TallerRepositoryImpl;

import java.util.List;

/**
 * Implementación del servicio para gestionar talleres agrícolas.
 * Maneja la lógica de negocio y validaciones para la administración de talleres.
 */
public class TallerService implements ITallerService {
    private final TallerRepositoryImpl tallerRepository;

    /**
     * Constructor del servicio de talleres.
     * @param repo Repositorio de talleres
     */
    public TallerService(TallerRepositoryImpl repo) {
        this.tallerRepository = repo;
    }

    /**
     * Obtiene todos los talleres disponibles.
     * @return Lista de talleres
     */
    @Override
    public List<Taller> obtenerTaller() {

        return tallerRepository.obtenerTaller();
    }
    /**
     * Obtiene un taller por su ID con validación de existencia.
     * @param id ID del taller
     * @return Taller encontrado
     * @throws IllegalArgumentException Si el taller no existe
     */
    @Override
    public Taller obtenerTallerPorId(int id) {
        Taller taller = tallerRepository.obtenerTallerPorId(id);
        if (taller == null) {
            throw new IllegalArgumentException("No se encontró un taller con ID " + id);
        }
        return taller;
    }
    /**
     * Agrega un nuevo taller con validaciones de datos.
     * @param taller Taller a agregar
     * @throws IllegalArgumentException Si los datos no son válidos
     */
    @Override
    public void agregarTaller(Taller taller) {
        if (taller.getIdTaller() != 0) {
            throw new IllegalArgumentException("El ID debe ser generado automáticamente. No lo incluyas en la solicitud.");
        }
        if (taller.getCosto()<0){
            throw new IllegalArgumentException("El costo no puede ser negativo o 0.");
        }
        if (taller.getDescripcion()==null || taller.getDescripcion().isBlank()){
            throw new IllegalArgumentException("El nombre del taller es obligatorio.");
        }
        if (taller.getNombreTaller()==null || taller.getNombreTaller().isBlank()){
            throw new IllegalArgumentException("El nombre del taller es obligatorio.");
        }
        tallerRepository.agregarTaller(taller);
    }
    /**
     * Elimina un taller con validación de existencia.
     * @param id ID del taller a eliminar
     * @throws IllegalArgumentException Si el taller no existe
     */
    @Override
    public void eliminarTaller(int id) {
        boolean existe = tallerRepository.obtenerTaller().stream()
                .anyMatch(t -> t.getIdTaller() == id);
        if (!existe) {
            throw new IllegalArgumentException("No existe un taller con ID " + id);
        }
        tallerRepository.eliminarTaller(id);
    }
    /**
     * Actualiza el estado de un taller.
     * @param id ID del taller
     * @param nuevoEstado Nuevo estado del taller
     */
    @Override
    public void actualizarEstadoTaller(int id, int nuevoEstado) {
        tallerRepository.actualizarEstado(id, nuevoEstado);
    }
    /**
     * Actualiza un taller existente con validación de duplicados.
     * @param id ID del taller a actualizar
     * @param tallerActualizar Datos actualizados del taller
     * @throws IllegalArgumentException Si hay conflicto de IDs
     */
    @Override
    public void actualizarTaller(int id, Taller tallerActualizar) {
        List<Taller> existentes = tallerRepository.obtenerTaller();
        for (Taller t : existentes) {
            if (t.getIdTaller() == tallerActualizar.getIdTaller() && t.getIdTaller() != id) {
                throw new IllegalArgumentException("El ID " + tallerActualizar.getIdTaller() + " ya está en uso por otro taller.");
            }
        }
        tallerRepository.actualizarTaller(id,tallerActualizar);
    }

}