package org.example.controller;

import io.javalin.http.Context;
import org.example.Validator.SolicitudTareaValidator;
import org.example.model.ReportePlaga;
import org.example.model.Tarea;
import org.example.service.ITareaService;
import org.example.service.TareaService;

import java.util.List;
import java.util.Map;

/**
 * Controlador que maneja las peticiones HTTP relacionadas con tareas.
 * Permite crear, consultar, actualizar y eliminar tareas agrícolas.
 */
public class TareaController {
    private final ITareaService service;

    /**
     * Constructor que recibe el servicio de tareas.
     * @param service el servicio para manejar la lógica de tareas
     */
    public TareaController(ITareaService service) {
        this.service = service;
    }

    /**
     * Obtiene una tarea por su ID.
     * Los agricultores solo pueden ver sus propias tareas.
     * @param ctx el contexto de la petición HTTP
     */
    public void obtenerPorId(Context ctx) {
        int idTarea = Integer.parseInt(ctx.pathParam("idTarea"));
        int rol = ctx.attribute("rol");
        int usuarioId = ctx.attribute("usuarioId");

        Tarea tarea = service.obtenerTareaPorId(idTarea);
        if (tarea == null) {
            ctx.status(404).result("Solicitud no encontrada");
            return;
        }


        if (rol == 2 && tarea.getIdUsuario() != 0 && tarea.getIdUsuario() != usuarioId) {
            ctx.status(403).result("Acceso denegado: no puedes ver tareas de otros usuarios");
            return;
        }

        ctx.json(tarea);
    }

    /**
     * Obtiene todas las tareas.
     * 
     * <p><strong>Endpoint:</strong> GET /tarea</p>
     * <p><strong>Acceso:</strong> Requiere autenticación</p>
     * <p><strong>Comportamiento:</strong> Agrónomos ven todas, agricultores solo las suyas</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * GET /tarea
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>
     * HTTP/1.1 200 OK
     * [
     *   {
     *     "idTarea": 1,
     *     "idUsuario": 1,
     *     "titulo": "Preparación del terreno",
     *     "descripcion": "Arar y preparar el terreno para la siembra",
     *     "fechaInicio": "2024-01-20T08:00:00",
     *     "fechaFin": "2024-01-22T17:00:00",
     *     "prioridad": "Alta",
     *     "idEstado": 1,
     *     "nombreEstado": "Pendiente"
     *   }
     * ]
     * </pre>
     * 
     * @param ctx el contexto de la petición HTTP
     */
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

    /**
     * Registra una nueva tarea en el sistema.
     * 
     * <p><strong>Endpoint:</strong> POST /tarea</p>
     * <p><strong>Acceso:</strong> Solo agrónomos (rol 1)</p>
     * <p><strong>Headers requeridos:</strong> Authorization, confirmado: true</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * POST /tarea
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * confirmado: true
     * Content-Type: application/json
     * 
     * {
     *   "idUsuario": 1,
     *   "titulo": "Preparación del terreno",
     *   "descripcion": "Arar y preparar el terreno para la siembra",
     *   "fechaInicio": "2024-01-20T08:00:00",
     *   "fechaFin": "2024-01-22T17:00:00",
     *   "prioridad": "Alta",
     *   "idEstado": 1
     * }
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>201 Created: Tarea creada exitosamente</li>
     *   <li>400 Bad Request: Error de validación o falta confirmación</li>
     *   <li>403 Forbidden: Solo agrónomos pueden crear tareas</li>
     * </ul>
     * 
     * @param ctx el contexto de la petición HTTP
     */
    public void registrar(Context ctx) {

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

        System.out.println(">>> [DEBUG] Tarea recibida en Controller, idReportePlaga: " + tarea.getIdReportePlaga());

        if (tarea.getIdUsuario() <= 0) {
            ctx.status(400).result("El 'idUsuario' (del cliente) es obligatorio en el JSON de la tarea");
            return;
        }

        String error = SolicitudTareaValidator.validar(tarea);
        if (error != null) {
            ctx.status(400).result(error);
            return;
        }

        service.agregarTarea(tarea);
        ctx.status(201).json(tarea);
    }

    /**
     * Actualiza todos los datos de una tarea existente.
     * Solo los agrónomos pueden editar tareas.
     * @param ctx el contexto de la petición HTTP
     */
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


        Tarea tareaExistente = service.obtenerTareaPorId(idTarea);
        if (tareaExistente == null) {
            ctx.status(404).result("Tarea no encontrada");
            return;
        }


        String error = SolicitudTareaValidator.validar(tarea);
        if (error != null) {
            ctx.status(400).result(error);
            return;
        }

        service.actualizarTareaCompleta(tarea);
        ctx.status(200).json(tarea);
    }

    /**
     * Actualiza solo el estado de una tarea.
     * Tanto agrónomos como agricultores pueden cambiar el estado.
     * @param ctx el contexto de la petición HTTP
     */
    public void actualizarEstado(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int estado = Integer.parseInt(ctx.pathParam("estado"));


        service.actualizarTarea(id, estado);
        ctx.status(200).result("Estado actualizado correctamente");
    }

    /**
     * Elimina una tarea del sistema.
     * Solo los agrónomos pueden eliminar tareas.
     * @param ctx el contexto de la petición HTTP
     */
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

    /**
     * Obtiene todas las tareas que tienen reportes de plaga asociados.
     * @param ctx el contexto de la petición HTTP
     */
    public void obtenerConReportePlaga(Context ctx) {
        List<Tarea> tareas = service.obtenerTareasConReportePlaga();
        ctx.json(tareas);
    }

    /**
     * Registra un nuevo reporte de plaga.
     * 
     * <p><strong>Endpoint:</strong> POST /reporteplaga</p>
     * <p><strong>Acceso:</strong> Solo agricultores (rol 2)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * POST /reporteplaga
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * Content-Type: application/json
     * 
     * {
     *   "idUsuario": 1,
     *   "nombrePlaga": "Pulgón verde",
     *   "descripcion": "Presencia de pulgones en hojas de maíz",
     *   "nivelSeveridad": "Medio",
     *   "ubicacion": "Sector A, Parcela 5"
     * }
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>201 Created: "Reporte de plaga registrado correctamente"</li>
     *   <li>403 Forbidden: Solo agricultores pueden registrar reportes</li>
     * </ul>
     * 
     * @param ctx el contexto de la petición HTTP
     */
    public void registrarReportePlaga(Context ctx) {
        int rol = ctx.attribute("rol");
        if (rol != 2) {
            ctx.status(403).result("Acceso denegado: solo agricultores pueden registrar reportes de plaga");
            return;
        }

        ReportePlaga reporte = ctx.bodyAsClass(ReportePlaga.class);
        service.registrarReportePlaga(reporte);
        ctx.status(201).result("Reporte de plaga registrado correctamente");
    }
}