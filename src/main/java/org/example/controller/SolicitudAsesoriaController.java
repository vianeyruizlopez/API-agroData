package org.example.controller;

import io.javalin.http.Context;
import org.example.Validator.SolicitudAsesoriaValidator;
import org.example.model.SolicitudAsesoria;
import org.example.service.SolicitudAsesoriaService;

import java.util.List;
import java.util.Map;

public class SolicitudAsesoriaController {
    private final SolicitudAsesoriaService service;

    public SolicitudAsesoriaController(SolicitudAsesoriaService service) {
        this.service = service;
    }

    public void obtenerPorId(Context ctx) {
        try {
            int rol = extraerRol(ctx);
            int usuarioId = extraerUsuarioId(ctx);
            System.out.println("→ Entrando a obtenerPorId | usuarioId: " + usuarioId + ", rol: " + rol);

            if (rol != 1) {
                ctx.status(403).result("Acceso denegado: solo el agrónomo puede ver solicitudes");
                return;
            }

            int id = Integer.parseInt(ctx.pathParam("id"));
            SolicitudAsesoria solicitud = service.obtenerSolicitudAsesoriaPorId(id);

            if (solicitud == null) {
                ctx.status(404).result("Solicitud no encontrada");
            } else {
                ctx.json(solicitud);
            }
        } catch (Exception e) {
            ctx.status(500).result("Error al obtener la solicitud: " + e.getMessage());
        }
    }

    public void obtenerTodas(Context ctx) {
        try {
            int rol = extraerRol(ctx);
            int usuarioId = extraerUsuarioId(ctx);
            System.out.println("→ Entrando a obtenerTodas | usuarioId: " + usuarioId + ", rol: " + rol);

            if (rol != 1) {
                ctx.status(403).result("Acceso denegado: solo el agrónomo puede ver todas las solicitudes");
                return;
            }

            List<SolicitudAsesoria> solicitudes = service.obtenerTodasLasSolicitudes();
            ctx.json(solicitudes);
        } catch (Exception e) {
            ctx.status(500).result("Error al obtener las solicitudes: " + e.getMessage());
        }
    }

    public void registrar(Context ctx) {
        try {
            int rol = extraerRol(ctx);
            int usuarioId = extraerUsuarioId(ctx);
            System.out.println("→ Entrando a registrar | usuarioId: " + usuarioId + ", rol: " + rol);

            if (rol != 2) {
                ctx.status(403).result("Acceso denegado: solo agricultores pueden enviar solicitudes");
                return;
            }

            boolean confirmado = Boolean.parseBoolean(ctx.header("confirmado"));
            if (!confirmado) {
                ctx.status(400).result("Confirmación requerida antes de enviar la solicitud");
                return;
            }

            SolicitudAsesoria solicitud = ctx.bodyAsClass(SolicitudAsesoria.class);
            String error = SolicitudAsesoriaValidator.validar(solicitud);

            if (error != null) {
                ctx.status(400).result(error);
                return;
            }

            service.agregarSolicitudAsesoria(solicitud);
            SolicitudAsesoria solicitudCompleta = service.obtenerSolicitudAsesoriaPorId(solicitud.getIdSolicitud());

            if (solicitudCompleta != null) {
                ctx.status(201).json(solicitudCompleta);
            } else {
                ctx.status(201).json(solicitud);
            }
        } catch (Exception e) {
            ctx.status(500).result("Error al registrar la solicitud: " + e.getMessage());
        }
    }

    public void actualizarEstado(Context ctx) {
        try {
            int rol = extraerRol(ctx);
            int usuarioId = extraerUsuarioId(ctx);
            System.out.println("→ Entrando a actualizarEstado | usuarioId: " + usuarioId + ", rol: " + rol);

            if (rol != 1) {
                ctx.status(403).result("Acceso denegado: solo el agrónomo puede actualizar solicitudes");
                return;
            }

            int id = Integer.parseInt(ctx.pathParam("id"));
            int estado = Integer.parseInt(ctx.pathParam("estado"));
            service.actualizarEstadoSolicitudAsesoria(id, estado);
            ctx.status(200).result("Estado actualizado correctamente");
        } catch (Exception e) {
            ctx.status(500).result("Error al actualizar estado: " + e.getMessage());
        }
    }

    public void eliminar(Context ctx) {
        try {
            int rol = extraerRol(ctx);
            int usuarioId = extraerUsuarioId(ctx);
            System.out.println("→ Entrando a eliminar | usuarioId: " + usuarioId + ", rol: " + rol);

            if (rol != 1) {
                ctx.status(403).result("Acceso denegado: solo el agrónomo puede eliminar solicitudes");
                return;
            }

            int id = Integer.parseInt(ctx.pathParam("id"));
            service.eliminarSolicitudAsesoria(id);
            ctx.json(Map.of("mensaje", "Solicitud eliminada", "id", id));
        } catch (Exception e) {
            ctx.status(500).result("Error al eliminar la solicitud: " + e.getMessage());
        }
    }


    private int extraerRol(Context ctx) {
        Object rolAttr = ctx.attribute("rol");
        return extraerEnteroSeguro(rolAttr);
    }

    private int extraerUsuarioId(Context ctx) {
        Object idAttr = ctx.attribute("usuarioId");
        return extraerEnteroSeguro(idAttr);
    }

    private int extraerEnteroSeguro(Object valor) {
        if (valor instanceof Integer) {
            return (Integer) valor;
        } else if (valor instanceof Number) {
            return ((Number) valor).intValue();
        } else if (valor instanceof String) {
            try {
                return Integer.parseInt(((String) valor).trim());
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir a entero: " + valor);
            }
        } else {
            System.out.println("Tipo inesperado para conversión a entero: " + tipo(valor));
        }
        return -1;
    }

    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }
}