package org.example.controller;

import io.javalin.http.Context;
import org.example.Validator.SolicitudTallerValidator;
import org.example.model.SolicitudTaller;
import org.example.model.Solicitudes;
import org.example.service.ISolicitudTallerService;
import org.example.service.SolicitudTallerService;

import java.util.List;
import java.util.Map;

/**
 * Controlador para manejar solicitudes de talleres.
 * Permite a agricultores solicitar talleres y a agrónomos gestionarlas.
 */
public class SolicitudTallerController {
    private final ISolicitudTallerService service;

    /**
     * Constructor que recibe el servicio de solicitudes de taller.
     * @param service servicio para manejar solicitudes de taller
     */
    public SolicitudTallerController(ISolicitudTallerService service) {
        this.service = service;
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
     * Obtiene el ID del usuario desde el contexto.
     * @param ctx contexto de la petición HTTP
     * @return el ID del usuario o -1 si hay error
     */
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


    /**
     * Obtiene el nombre del tipo de un objeto.
     * @param obj el objeto
     * @return nombre del tipo o "null"
     */
    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }

    /**
     * Obtiene una solicitud de taller por su ID.
     * Solo accesible para agrónomos.
     * @param ctx contexto de la petición HTTP
     */
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

    /**
     * Obtiene todas las solicitudes de taller.
     * Solo accesible para agrónomos.
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerTodas(Context ctx) {
        int rol = obtenerRol(ctx);
        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo el agrónomo puede ver todas las solicitudes");
            return;
        }

        List<SolicitudTaller> solicitudes = service.obtenerTodasLasSolicitudes();
        ctx.json(solicitudes);
    }

    /**
     * Obtiene talleres filtrados por estado.
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerPorEstado(Context ctx) {

        int idEstado = Integer.parseInt(ctx.pathParam("idEstado"));
        List<SolicitudTaller> talleres = service.obtenerTalleresPorStatus(idEstado);
        ctx.json(talleres);
    }

    /**
     * Registra una nueva solicitud de taller.
     * 
     * <p><strong>Endpoint:</strong> POST /solicitudtaller</p>
     * <p><strong>Acceso:</strong> Solo agricultores (rol 2)</p>
     * <p><strong>Headers requeridos:</strong> Authorization, confirmado: true</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * POST /solicitudtaller
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * confirmado: true
     * Content-Type: application/json
     * 
     * {
     *   "idAgricultor": 5,
     *   "idTaller": 4,
     *   "fechaAplicarTaller": "2025-11-20",
     *   "fechaFin": "2025-11-27",
     *   "direccion": "San Juan Diquiyu",
     *   "comentario": "Necesito la capacitación para mis empleados"
     * }
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>201 Created: Solicitud creada exitosamente</li>
     *   <li>400 Bad Request: Error de validación o falta confirmación</li>
     *   <li>403 Forbidden: Solo agricultores pueden crear solicitudes</li>
     * </ul>
     * 
     * @param ctx contexto de la petición HTTP
     */
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


    /**
     * Actualiza el estado de una solicitud de taller.
     * @param ctx contexto de la petición HTTP
     */
    public void actualizarEstado(Context ctx) {
        int rol = obtenerRol(ctx);


        int id = Integer.parseInt(ctx.pathParam("id"));
        int estado = Integer.parseInt(ctx.pathParam("estado"));
        if ( estado == 4  ) {
            service.actualizarEstadoSolicitudTaller(id, estado);
            ctx.status(200).result("Estado actualizado correctamente a revision");
        }else if(rol!=1){
            ctx.status(403).result("Acceso denegado: solo el agrónomo puede actualizar solicitudes");
            return;
        }
        service.actualizarEstadoSolicitudTaller(id, estado);
        ctx.status(200).result("Estado actualizado correctamente");
    }

    /**
     * Permite subir comprobante de pago.
     * 
     * <p><strong>Endpoint:</strong> PATCH /solicitudtaller/{id}/comprobante</p>
     * <p><strong>Acceso:</strong> Solo agricultores (rol 2)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * PATCH /solicitudtaller/1/comprobante
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * Content-Type: application/json
     * 
     * {
     *   "imagen": "base64_encoded_image_data"
     * }
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>200 OK: "Comprobante subido y solicitud en revisión"</li>
     *   <li>400 Bad Request: "La imagen es obligatoria"</li>
     *   <li>403 Forbidden: Solo agricultores pueden subir comprobantes</li>
     * </ul>
     * 
     * @param ctx contexto de la petición HTTP
     */
    public void subirComprobante(Context ctx) {
        int rol = obtenerRol(ctx);
        if (rol != 2) {
            ctx.status(403).result("Solo el agricultor puede subir comprobantes");
            return;
        }

        int id = Integer.parseInt(ctx.pathParam("id"));

        Map body = ctx.bodyAsClass(Map.class);
        String imagen = (String) body.get("imagen");

        if (imagen == null || imagen.isEmpty()) {
            ctx.status(400).result("La imagen es obligatoria");
            return;
        }

        service.subirComprobante(id, imagen);
        ctx.status(200).result("Comprobante subido y solicitud en revisión");
    }


    /**
     * Elimina una solicitud de taller.
     * Solo accesible para agrónomos.
     * @param ctx contexto de la petición HTTP
     */
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

    /**
     * Obtiene resumen de solicitudes de taller y asesoría.
     * 
     * <p><strong>Endpoint:</strong> GET /solicitudesTallerAsesoria</p>
     * <p><strong>Acceso:</strong> Solo agrónomos (rol 1)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * GET /solicitudesTallerAsesoria
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>
     * HTTP/1.1 200 OK
     * {
     *   "solicitudTalleres": [...],
     *   "solicitudAsesorias": [...]
     * }
     * </pre>
     * 
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerSolicitudesTallerAsesoria(Context ctx) {
        int rol = obtenerRol(ctx);
        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo el agrónomo puede ver todas las solicitudes");
            return;
        }

        Solicitudes solicitudes = service.obtenerSolicitudesTallerAsesoria();
        ctx.json(solicitudes);
    }

    /**
     * Obtiene solicitudes de taller de un usuario específico.
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerPorUsuario(Context ctx) {

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

    /**
     * Obtiene estadísticas de talleres.
     * Solo accesible para agrónomos.
     * @param ctx contexto de la petición HTTP
     */
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