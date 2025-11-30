package org.example.service;

import org.example.model.ReportePlaga;
import org.example.model.Tarea;
import org.example.repository.TareaRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TareaService implements ITareaService{
    private final TareaRepository tareaRepository = new TareaRepository();

    @Override
    public Tarea obtenerTareaPorId(int idTarea) {
        return tareaRepository.obtenerPorId(idTarea);
    }

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