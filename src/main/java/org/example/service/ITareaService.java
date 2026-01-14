package org.example.service;

import org.example.model.ReportePlaga;
import org.example.model.Tarea;

import java.util.List;

/**
 * Interfaz que define servicios para manejar tareas agrícolas.
 * Incluye operaciones CRUD y manejo de reportes de plaga.
 */
public interface ITareaService {
    /**
     * Obtiene una tarea por su ID.
     * @param idTarea el ID de la tarea
     * @return la tarea encontrada o null
     */
    Tarea obtenerTareaPorId(int idTarea);
    /**
     * Obtiene todas las tareas del sistema.
     * @return lista de todas las tareas
     */
    List<Tarea> obtenerTodasLasTareas();
    /**
     * Actualiza el estado de una tarea.
     * @param idTarea el ID de la tarea
     * @param nuevoEstado el nuevo estado
     */
    void actualizarTarea(int idTarea, int nuevoEstado);
    /**
     * Actualiza todos los datos de una tarea.
     * @param tarea la tarea con datos actualizados
     */
    void actualizarTareaCompleta(Tarea tarea);
    /**
     * Elimina una tarea del sistema.
     * @param idTarea el ID de la tarea a eliminar
     */
    void eliminarTarea(int idTarea);
    /**
     * Agrega una nueva tarea al sistema.
     * @param tarea la tarea a agregar
     */
    void agregarTarea(Tarea tarea);
    /**
     * Obtiene tareas que tienen reportes de plaga asociados.
     * @return lista de tareas con reportes de plaga
     */
    List<Tarea> obtenerTareasConReportePlaga();
    /**
     * Registra un nuevo reporte de plaga.
     * @param reporte el reporte de plaga a registrar
     */
    void registrarReportePlaga(ReportePlaga reporte);
    /**
     * Obtiene las tareas asignadas a un usuario específico.
     * @param idUsuario el ID del usuario
     * @return lista de tareas del usuario
     */
    List<Tarea> obtenerTareasPorUsuario(int idUsuario);
}
