package org.example.service;

import org.example.model.ReportePlaga;
import org.example.model.Tarea;
import org.example.repository.TareaRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TareaService {
    private TareaRepository tareaRepository = new TareaRepository();

    public Tarea obtenerTareaPorId(int idTarea) {
        return tareaRepository.obtenerPorId(idTarea);
    }

    public List<Tarea> obtenerTodasLasTareas() {
        return tareaRepository.obtenerTodas();
    }

    public void actualizarTarea(int idTarea, int nuevoEstado) {
        tareaRepository.actualizarEstado(idTarea, nuevoEstado);
    }

    public void eliminarTarea(int idTarea) {
        tareaRepository.eliminar(idTarea);
    }

    public void agregarTarea(Tarea tarea) {
        tareaRepository.agregar(tarea);
    }

    public List<Tarea> obtenerTareasConReportePlaga() {
        return tareaRepository.obtenerTareasConReportePlaga();
    }

    public void registrarReportePlaga(ReportePlaga reporte) {
        try {
            tareaRepository.registrarReportePlaga(reporte);
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar reporte de plaga", e);
        }
    }

    // ✅ Filtrado por usuario en memoria (sin SQL)
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
