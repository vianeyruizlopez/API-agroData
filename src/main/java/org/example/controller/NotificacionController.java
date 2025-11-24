package org.example.controller;

import io.javalin.http.Context;
import org.example.model.Notificacion;
import org.example.service.NotificacionService;

import java.util.List;

public class NotificacionController {
    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }


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


    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }


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