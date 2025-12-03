package org.example.service;
import org.example.model.Taller;
import java.util.List;

/**
 * Interfaz del servicio para gestionar talleres agrícolas.
 * Define las operaciones de negocio relacionadas con la administración de talleres.
 */
public interface ITallerService {
    /**
     * Obtiene todos los talleres disponibles.
     * @return Lista de talleres
     */
    List<Taller> obtenerTaller();
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
    void agregarTaller(Taller Taller);
    /**
     * Elimina un taller.
     * @param id ID del taller a eliminar
     */
    void eliminarTaller(int id);
    /**
     * Actualiza el estado de un taller.
     * @param id ID del taller
     * @param nuevoEstado Nuevo estado
     */
    void actualizarEstadoTaller(int id, int nuevoEstado);
    /**
     * Actualiza un taller existente.
     * @param id ID del taller
     * @param tallerActualizar Datos actualizados
     */
    void actualizarTaller(int id, Taller tallerActualizar);

}
