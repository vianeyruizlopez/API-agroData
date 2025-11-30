package org.example.service;

import org.example.model.ReportePlaga;
import org.example.model.Tarea;

import java.util.List;

public interface ITareaService {
    Tarea obtenerTareaPorId(int idTarea);
    List<Tarea> obtenerTodasLasTareas();
    void actualizarTarea(int idTarea, int nuevoEstado);
    void actualizarTareaCompleta(Tarea tarea);
    void eliminarTarea(int idTarea);
    void agregarTarea(Tarea tarea);
    List<Tarea> obtenerTareasConReportePlaga();
    void registrarReportePlaga(ReportePlaga reporte);
    List<Tarea> obtenerTareasPorUsuario(int idUsuario);
}
