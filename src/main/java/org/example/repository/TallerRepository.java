package org.example.repository;

import org.example.model.Taller;

import java.util.List;

/**
 * Interfaz del repositorio para gestionar talleres agrícolas.
 * Define las operaciones CRUD para el manejo de talleres.
 */
public interface TallerRepository {
    /**
     * Obtiene todos los talleres disponibles.
     * @return Lista de talleres
     */
    public List<Taller> obtenerTaller();
    /**
     * Obtiene un taller por su ID.
     * @param id ID del taller
     * @return Taller encontrado
     */
    Taller obtenerTallerPorId(int id);
    /**
     * Agrega un nuevo taller.
     * @param Taller Taller a agregar
     */
    public void agregarTaller(Taller Taller);
    /**
     * Elimina un taller por su ID.
     * @param id ID del taller a eliminar
     */
    public void eliminarTaller(int id);
    /**
     * Actualiza un taller existente.
     * @param id ID del taller a actualizar
     * @param tallerActualizado Datos actualizados del taller
     */
    public void actualizarTaller(int id, Taller tallerActualizado);

}
