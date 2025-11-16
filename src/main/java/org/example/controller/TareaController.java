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

        // Agricultor solo puede ver tareas propias (si idUsuario está asignado)
        if (rol == 2 && tarea.getIdUsuario() != 0 && tarea.getIdUsuario() != usuarioId) {
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
        // int usuarioId = ctx.attribute("usuarioId"); // <-- YA NO USAMOS ESTO para asignar
        int rol = ctx.attribute("rol"); // <-- SÍ lo usamos para verificar permisos

        // 1. Validar que quien llama es un Agrónomo (rol 1)
        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo agrónomos pueden agregar tareas");
            return;
        }

        // 2. Validar el header 'confirmado'
        boolean confirmado = Boolean.parseBoolean(ctx.header("confirmado"));
        if (!confirmado) {
            ctx.status(400).result("Confirmación requerida antes de enviar la solicitud");
            return;
        }

        // 3. Obtener la tarea del body (JSON)
        Tarea tarea = ctx.bodyAsClass(Tarea.class);

        System.out.println(">>> [DEBUG] Tarea recibida en Controller, idReportePlaga: " + tarea.getIdReportePlaga());

        // 4. (¡ESTA ES LA CORRECCIÓN!)
        // Se elimina la línea que sobrescribía el ID del cliente con el ID del agrónomo.
        // tarea.setIdUsuario(usuarioId); // <-- LÍNEA ORIGINAL ELIMINADA

        // 5. (NUEVA VALIDACIÓN)
        // Nos aseguramos de que el frontend (JS) SÍ haya enviado un idUsuario (el del cliente).
        if (tarea.getIdUsuario() <= 0) {
            ctx.status(400).result("El 'idUsuario' (del cliente) es obligatorio en el JSON de la tarea");
            return;
        }

        // 6. Validar el resto del objeto Tarea
        String error = SolicitudTareaValidator.validar(tarea);
        if (error != null) {
            ctx.status(400).result(error);
            return;
        }

        // 7. Guardar la tarea
        // El objeto 'tarea' ahora contiene el idUsuario correcto (del cliente)
        // que venía en el JSON enviado por proyecto-detalle.js.
        service.agregarTarea(tarea);
        ctx.status(201).json(tarea);
    }

    // --- NUEVO MÉTODO PARA EDICIÓN (AGRÓNOMO) ---
    public void actualizarCompleta(Context ctx) {
        int rol = ctx.attribute("rol");
        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo agrónomos pueden editar tareas");
            return;
        }

        int idTarea = Integer.parseInt(ctx.pathParam("idTarea"));
        Tarea tarea = ctx.bodyAsClass(Tarea.class);

        if (idTarea != tarea.getIdTarea()) {
            ctx.status(400).result("El ID de la URL no coincide con el ID del cuerpo");
            return;
        }

        // Validar que la tarea exista
        Tarea tareaExistente = service.obtenerTareaPorId(idTarea);
        if (tareaExistente == null) {
            ctx.status(404).result("Tarea no encontrada");
            return;
        }

        // Validar campos
        String error = SolicitudTareaValidator.validar(tarea);
        if (error != null) {
            ctx.status(400).result(error);
            return;
        }

        service.actualizarTareaCompleta(tarea);
        ctx.status(200).json(tarea);
    }

    public void actualizarEstado(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int estado = Integer.parseInt(ctx.pathParam("estado"));

        // Lógica de permisos (opcional): quién puede actualizar estado
        // Por ahora, se asume que el cliente lo hace (al subir evidencia)
        // y el agrónomo (al revisar)

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
        if (rol != 2) { // Cambiado a rol 2 (agricultor)
            ctx.status(403).result("Acceso denegado: solo agricultores pueden registrar reportes de plaga");
            return;
        }

        ReportePlaga reporte = ctx.bodyAsClass(ReportePlaga.class);
        service.registrarReportePlaga(reporte);
        ctx.status(201).result("Reporte de plaga registrado correctamente");
    }
}