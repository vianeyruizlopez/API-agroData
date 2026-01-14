package org.example.service;

import org.example.model.ReportePlaga;
import org.example.model.Tarea;
import org.example.repository.TareaRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que implementa la lógica de negocio para tareas.
 * Maneja operaciones CRUD y reportes de plaga.
 */
public class TareaService implements ITareaService{
    private final TareaRepository tareaRepository = new TareaRepository();

    /**
     * Obtiene una tarea por su ID.
     * @param idTarea el ID de la tarea
     * @return la tarea encontrada
     */
    @Override
    public Tarea obtenerTareaPorId(int idTarea) {
        return tareaRepository.obtenerPorId(idTarea);
    }

    /**
     * Obtiene todas las tareas del sistema.
     * @return lista de todas las tareas
     */
    @Override
    public List<Tarea> obtenerTodasLasTareas() {
        return tareaRepository.obtenerTodas();
    }

    @Override
    public void actualizarTarea(int idTarea, int nuevoEstado) {
        tareaRepository.actualizarEstado(idTarea, nuevoEstado);
    }

    @Override
    public void actualizarTareaCompleta(Tarea tarea) {
        tareaRepository.actualizar(tarea);
    }

    @Override
    public void eliminarTarea(int idTarea) {
        tareaRepository.eliminar(idTarea);
    }

    /**
     * Agrega una nueva tarea al sistema.
     * @param tarea la tarea a agregar
     */
    @Override
    public void agregarTarea(Tarea tarea) {
        tareaRepository.agregar(tarea);
    }

    @Override
    public List<Tarea> obtenerTareasConReportePlaga() {
        return tareaRepository.obtenerTareasConReportePlaga();
    }

    @Override
    public void registrarReportePlaga(ReportePlaga reporte) {
        try {
            tareaRepository.registrarReportePlaga(reporte);
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar reporte de plaga", e);
        }
    }

    /**
     * Obtiene las tareas asignadas a un usuario específico.
     * @param idUsuario el ID del usuario
     * @return lista de tareas del usuario
     */
    @Override
    public List<Tarea> obtenerTareasPorUsuario(int idUsuario) {
        List<Tarea> todas = tareaRepository.obtenerTodas();
        List<Tarea> propias = new ArrayList<>();

        for (Tarea tarea : todas) {
            if (tarea.getIdUsuario() == idUsuario) {
                propias.add(tarea);
            }
        }

        return propias;
    }
}