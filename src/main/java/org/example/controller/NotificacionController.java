package org.example.controller;

import io.javalin.http.Context;
import org.example.model.Notificacion;
import org.example.service.INotificacionService;
import org.example.service.NotificacionService;

import java.util.List;

/**
 * Controlador para manejar notificaciones del sistema.
 * Permite a agrónomos y agricultores consultar sus notificaciones.
 */
public class NotificacionController {
    private final INotificacionService notificacionService;

    /**
     * Constructor que recibe el servicio de notificaciones.
     * @param notificacionService servicio para manejar notificaciones
     */
    public NotificacionController(INotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }


    /**
     * Obtiene el rol del usuario desde el contexto.
     * @param ctx contexto de la petición HTTP
     * @return el rol del usuario o -1 si hay error
     */
    private int obtenerRol(Context ctx) {
        Object rolAttr = ctx.attribute("rol");
        Object idAttr = ctx.attribute("usuarioId");

        System.out.println("→ Verificando atributos en controlador:");
        System.out.println("usuarioId desde ctx: " + idAttr + " (tipo: " + tipo(idAttr) + ")");
        System.out.println("rol desde ctx: " + rolAttr + " (tipo: " + tipo(rolAttr) + ")");

        if (rolAttr instanceof Integer) {
            return (Integer) rolAttr;
        } else if (rolAttr instanceof Number) {
            return ((Number) rolAttr).intValue();
        } else if (rolAttr instanceof String) {
            try {
                return Integer.parseInt(((String) rolAttr).trim());
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir rol a entero: " + rolAttr);
            }
        } else {
            System.out.println("Tipo inesperado para rol: " + tipo(rolAttr));
        }
        return -1;
    }


    /**
     * Obtiene el nombre del tipo de un objeto.
     * @param obj el objeto
     * @return nombre del tipo o "null"
     */
    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }


    /**
     * Obtiene notificaciones para agrónomos.
     * 
     * <p><strong>Endpoint:</strong> GET /notificacionesagronomo</p>
     * <p><strong>Acceso:</strong> Solo agrónomos (rol 1)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * GET /notificacionesagronomo
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>
     * HTTP/1.1 200 OK
     * [
     *   {
     *     "idNotificacion": 1,
     *     "nombreEstado": "Pendiente",
     *     "tipoNotificacion": "asesoria"
     *   },
     *   {
     *     "idNotificacion": 2,
     *     "nombreEstado": "Pendiente",
     *     "tipoNotificacion": "taller"
     *   }
     * ]
     * </pre>
     * 
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerNotificaciones(Context ctx) {
        int rol = obtenerRol(ctx);
        System.out.println("Rol recibido en controlador (agrónomo): " + rol);

        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo el agrónomo puede ver todas las solicitudes");
            return;
        }

        List<Notificacion> notificaciones = notificacionService.getNotificaciones();
        ctx.json(notificaciones);
    }


    /**
     * Obtiene todas las notificaciones para agricultores.
     * 
     * <p><strong>Endpoint:</strong> GET /notificacionesagricultor</p>
     * <p><strong>Acceso:</strong> Solo agricultores (rol 2)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * GET /notificacionesagricultor
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>
     * HTTP/1.1 200 OK
     * [
     *   {
     *     "idNotificacion": 1,
     *     "titulo": "Nueva tarea asignada",
     *     "mensaje": "Se te ha asignado la tarea: Preparación del terreno",
     *     "fechaCreacion": "2024-01-20T08:00:00",
     *     "leida": false
     *   }
     * ]
     * </pre>
     * 
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerTodasNotificaciones(Context ctx) {
        int rol = obtenerRol(ctx);
        int usuarioId = ctx.attribute("usuarioId");

        System.out.println("Rol recibido en controlador (agricultor): " + rol);
        System.out.println("Usuario ID recibido en controlador: " + usuarioId);

        if (rol != 2) {
            ctx.status(403).result("Acceso denegado: solo el agricultor puede ver sus notificaciones");
            return;
        }

        List<Notificacion> notificaciones = notificacionService.getTodasNotificaciones(usuarioId);
        ctx.json(notificaciones);
    }
}