package org.example.controller;

import io.javalin.http.Context;
import org.example.Validator.SolicitudTareaValidator;
import org.example.model.ReportePlaga;
import org.example.model.Tarea;
import org.example.service.TareaService;

import java.util.List;
import java.util.Map;

public class TareaController {
    private final TareaService service;

    public TareaController(TareaService service) {
        this.service = service;
    }

    public void obtenerPorId(Context ctx) {
        int idTarea = Integer.parseInt(ctx.pathParam("idTarea"));
        int rol = ctx.attribute("rol");
        int usuarioId = ctx.attribute("usuarioId");

        Tarea tarea = service.obtenerTareaPorId(idTarea);
        if (tarea == null) {
            ctx.status(404).result("Solicitud no encontrada");
            return;
        }

        // Agricultor solo puede ver tareas propias
        if (rol == 2 && tarea.getIdUsuario() != usuarioId) {
            ctx.status(403).result("Acceso denegado: no puedes ver tareas de otros usuarios");
            return;
        }

        ctx.json(tarea);
    }

    public void obtenerTodas(Context ctx) {
        int usuarioId = ctx.attribute("usuarioId");
        int rol = ctx.attribute("rol");

        List<Tarea> tareas;
        if (rol == 2) {
            tareas = service.obtenerTareasPorUsuario(usuarioId);
        } else {
            tareas = service.obtenerTodasLasTareas();
        }

        ctx.json(tareas);
    }

    public void registrar(Context ctx) {
        int usuarioId = ctx.attribute("usuarioId");
        int rol = ctx.attribute("rol");

        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo agrónomos pueden agregar tareas");
            return;
        }

        boolean confirmado = Boolean.parseBoolean(ctx.header("confirmado"));
        if (!confirmado) {
            ctx.status(400).result("Confirmación requerida antes de enviar la solicitud");
            return;
        }

        Tarea tarea = ctx.bodyAsClass(Tarea.class);
        tarea.setIdUsuario(usuarioId);

        String error = SolicitudTareaValidator.validar(tarea);
        if (error != null) {
            ctx.status(400).result(error);
            return;
        }

        service.agregarTarea(tarea);
        ctx.status(201).json(tarea);
    }

    public void actualizarEstado(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int estado = Integer.parseInt(ctx.pathParam("estado"));
        service.actualizarTarea(id, estado);
        ctx.status(200).result("Estado actualizado correctamente");
    }

    public void eliminar(Context ctx) {
        int rol = ctx.attribute("rol");
        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo el agronomo puede eliminar tareas");
            return;
        }

        int id = Integer.parseInt(ctx.pathParam("id"));
        service.eliminarTarea(id);
        ctx.json(Map.of("mensaje", "Solicitud eliminada", "id", id));
    }

    public void obtenerConReportePlaga(Context ctx) {
        List<Tarea> tareas = service.obtenerTareasConReportePlaga();
        ctx.json(tareas);
    }

    public void registrarReportePlaga(Context ctx) {
        int rol = ctx.attribute("rol");
        if (rol < 2) {
            ctx.status(403).result("Acceso denegado: solo agricultores pueden registrar reportes de plaga");
            return;
        }

        ReportePlaga reporte = ctx.bodyAsClass(ReportePlaga.class);
        service.registrarReportePlaga(reporte);
        ctx.status(201).result("Reporte de plaga registrado correctamente");
    }
}