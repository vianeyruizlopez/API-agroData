package org.example.controller;

import io.javalin.http.Context;
import org.example.Validator.SolicitudTallerValidator;
import org.example.model.SolicitudTaller;
import org.example.model.Solicitudes;
import org.example.service.SolicitudTallerService;

import java.util.List;
import java.util.Map;

public class SolicitudTallerController {
    private final SolicitudTallerService service;

    public SolicitudTallerController(SolicitudTallerService service) {
        this.service = service;
    }

    // Método robusto para extraer el rol como entero seguro
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

    // Método robusto para extraer el ID de usuario como entero seguro
    private int obtenerUsuarioId(Context ctx) {
        Object idAttr = ctx.attribute("usuarioId");

        if (idAttr instanceof Integer) {
            return (Integer) idAttr;
        } else if (idAttr instanceof Number) {
            return ((Number) idAttr).intValue();
        } else if (idAttr instanceof String) {
            try {
                return Integer.parseInt(((String) idAttr).trim());
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir ID de usuario a entero: " + idAttr);
            }
        } else {
            System.out.println("Tipo inesperado para ID de usuario: " + tipo(idAttr));
        }
        return -1;
    }

    // Devuelve el tipo de dato como texto para trazabilidad
    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }

    public void obtenerPorId(Context ctx) {
        int rol = obtenerRol(ctx);
        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo el agrónomo puede ver solicitudes");
            return;
        }

        int id = Integer.parseInt(ctx.pathParam("id"));
        SolicitudTaller solicitud = service.obtenerSolicitudTallerPorId(id);
        if (solicitud == null) {
            ctx.status(404).result("Solicitud no encontrada");
        } else {
            ctx.json(solicitud);
        }
    }

    public void obtenerTodas(Context ctx) {
        int rol = obtenerRol(ctx);
        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo el agrónomo puede ver todas las solicitudes");
            return;
        }

        List<SolicitudTaller> solicitudes = service.obtenerTodasLasSolicitudes();
        ctx.json(solicitudes);
    }

    public void obtenerPorEstado(Context ctx) {
        // Ambos roles pueden ver este endpoint
        int idEstado = Integer.parseInt(ctx.pathParam("idEstado"));
        List<SolicitudTaller> talleres = service.obtenerTalleresPorStatus(idEstado);
        ctx.json(talleres);
    }

    public void registrar(Context ctx) {
        int rol = obtenerRol(ctx);
        if (rol != 2) {
            ctx.status(403).result("Acceso denegado: solo agricultores pueden enviar solicitudes");
            return;
        }

        boolean confirmado = Boolean.parseBoolean(ctx.header("confirmado"));
        if (!confirmado) {
            ctx.status(400).result("Confirmación requerida antes de enviar la solicitud");
            return;
        }

        SolicitudTaller solicitudTaller = ctx.bodyAsClass(SolicitudTaller.class);
        String error = SolicitudTallerValidator.validar(solicitudTaller);
        if (error != null) {
            ctx.status(400).result(error);
            return;
        }

        service.agregarSolicitudTaller(solicitudTaller);
        ctx.status(201).json(solicitudTaller);
    }


    public void actualizarEstado(Context ctx) {
        int rol = obtenerRol(ctx);
        int id = Integer.parseInt(ctx.pathParam("id"));
        int estado = Integer.parseInt(ctx.pathParam("estado"));


        if (rol == 1 && (estado == 2 || estado == 1)) {
            estado = 5;
        }
        // --------------------

        if (estado == 4) {
            service.actualizarEstadoSolicitudTaller(id, estado);
            ctx.status(200).result("Estado actualizado correctamente a revisión");
        } else if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo el agrónomo puede actualizar solicitudes");
            return;
        } else {
            service.actualizarEstadoSolicitudTaller(id, estado);
            ctx.status(200).result("Estado actualizado correctamente");
        }
    }

    public void subirComprobante(Context ctx) {
        int rol = obtenerRol(ctx);
        if (rol != 2) {
            ctx.status(403).result("Solo el agricultor puede subir comprobantes");
            return;
        }

        int id = Integer.parseInt(ctx.pathParam("id"));
        // Esperamos un JSON { "imagen": "base64..." }
        Map body = ctx.bodyAsClass(Map.class);
        String imagen = (String) body.get("imagen");

        if (imagen == null || imagen.isEmpty()) {
            ctx.status(400).result("La imagen es obligatoria");
            return;
        }

        service.subirComprobante(id, imagen);
        ctx.status(200).result("Comprobante subido y solicitud en revisión");
    }


    public void eliminar(Context ctx) {
        int rol = obtenerRol(ctx);
        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo el agrónomo puede eliminar solicitudes");
            return;
        }

        int id = Integer.parseInt(ctx.pathParam("id"));
        service.eliminarSolicitudTaller(id);
        ctx.json(Map.of("mensaje", "Solicitud eliminada", "id", id));
    }

    public void obtenerSolicitudesTallerAsesoria(Context ctx) {
        int rol = obtenerRol(ctx);
        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo el agrónomo puede ver todas las solicitudes");
            return;
        }

        Solicitudes solicitudes = service.obtenerSolicitudesTallerAsesoria();
        ctx.json(solicitudes);
    }

    public void obtenerPorUsuario(Context ctx) {
        // Obtiene el ID del usuario del token validado
        System.out.println("→ Entrando a obtenerPorUsuario");
        System.out.println("→ ctx.attribute(\"usuarioId\"): " + ctx.attribute("usuarioId"));
        System.out.println("→ ctx.attribute(\"rol\"): " + ctx.attribute("rol"));
        int userId = obtenerUsuarioId(ctx);

        if (userId == -1) {
            ctx.status(401).result("Usuario no autenticado. ID de usuario es requerido.");
            return;
        }
        
        List<SolicitudTaller> solicitudes = service.obtenerSolicitudesPorUsuario(userId);
        ctx.json(solicitudes);
    }

    public void obtenerEstadisticas(Context ctx) {
        try {
            int rol = obtenerRol(ctx);
            if (rol != 1) {
                ctx.status(403).result("Acceso denegado");
                return;
            }

            List<Map<String, Object>> stats = service.obtenerEstadisticasTalleres();

            ctx.json(stats);

        } catch (Exception e) {
            System.err.println("❌ Error en obtenerEstadisticas:");
            e.printStackTrace();
            ctx.status(500).result("Error en el servidor: " + e.getMessage());
        }
    }

}