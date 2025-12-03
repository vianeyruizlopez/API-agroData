package org.example.controller;

import io.javalin.http.Context;
import org.example.Validator.SolicitudAsesoriaValidator;
import org.example.model.SolicitudAsesoria;
import org.example.service.ISolicitudAsesoriaService;
import org.example.service.SolicitudAsesoriaService;

import java.util.List;
import java.util.Map;

/**
 * Controlador que maneja las peticiones HTTP relacionadas con solicitudes de asesoría.
 * Los agricultores crean solicitudes y los agrónomos las gestionan.
 */
public class SolicitudAsesoriaController {
    private final ISolicitudAsesoriaService service;

    /**
     * Constructor que recibe el servicio de solicitudes de asesoría.
     * @param service el servicio para manejar la lógica de solicitudes
     */
    public SolicitudAsesoriaController(ISolicitudAsesoriaService service) {
        this.service = service;
    }

    /**
     * Obtiene una solicitud de asesoría por su ID.
     * Solo accesible para agrónomos.
     * @param ctx el contexto de la petición HTTP
     */
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

    /**
     * Obtiene todas las solicitudes de asesoría.
     * 
     * <p><strong>Endpoint:</strong> GET /solicitudasesoria</p>
     * <p><strong>Acceso:</strong> Solo agrónomos (rol 1)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * GET /solicitudasesoria
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>
     * HTTP/1.1 200 OK
     * [
     *   {
     *     "idSolicitud": 1,
     *     "idAgricultor": 1,
     *     "nombreAgricultor": "Juan Pérez García",
     *     "fechaSolicitud": "2024-01-15T10:30:00",
     *     "usoMaquinaria": true,
     *     "nombreMaquinaria": "Tractor John Deere",
     *     "tipoRiego": 1,
     *     "nombreRiego": "Goteo",
     *     "tienePlaga": false,
     *     "superficieTotal": 10.5,
     *     "direccionTerreno": "Parcela 123, Sector Norte",
     *     "motivoAsesoria": "Optimización de cultivo de chayote",
     *     "idEstado": 1,
     *     "cultivos": [
     *       {
     *         "idCultivo": 1,
     *         "nombreCultivo": "Chayote",
     *         "cantidad": 100
     *       }
     *     ]
     *   }
     * ]
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>200 OK: Lista de solicitudes</li>
     *   <li>403 Forbidden: Solo agrónomos pueden ver solicitudes</li>
     *   <li>500 Internal Server Error: Error interno</li>
     * </ul>
     * 
     * @param ctx el contexto de la petición HTTP
     */
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

    /**
     * Registra una nueva solicitud de asesoría.
     * 
     * <p><strong>Endpoint:</strong> POST /solicitudasesoria</p>
     * <p><strong>Acceso:</strong> Solo agricultores (rol 2)</p>
     * <p><strong>Headers requeridos:</strong> Authorization, confirmado: true</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * POST /solicitudasesoria
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * confirmado: true
     * Content-Type: application/json
     * 
     * {
     *   "idAgricultor": 1,
     *   "usoMaquinaria": true,
     *   "nombreMaquinaria": "Tractor John Deere",
     *   "tipoRiego": 1,
     *   "tienePlaga": false,
     *   "superficieTotal": 10.5,
     *   "direccionTerreno": "Parcela 123, Sector Norte",
     *   "motivoAsesoria": "Optimización de cultivo de maíz",
     *   "cultivos": [
     *     {
     *       "idCultivo": 1,
     *       "cantidad": 100
     *     }
     *   ]
     * }
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>201 Created: Solicitud creada exitosamente</li>
     *   <li>400 Bad Request: Error de validación o falta confirmación</li>
     *   <li>403 Forbidden: Solo agricultores pueden crear solicitudes</li>
     *   <li>500 Internal Server Error: Error interno</li>
     * </ul>
     * 
     * @param ctx el contexto de la petición HTTP
     */
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

    /**
     * Actualiza el estado de una solicitud de asesoría.
     * 
     * <p><strong>Endpoint:</strong> PATCH /solicitudasesoria/{id}/{estado}</p>
     * <p><strong>Acceso:</strong> Solo agrónomos (rol 1)</p>
     * 
     * <p><strong>Estados disponibles:</strong></p>
     * <ul>
     *   <li>1: Pendiente</li>
     *   <li>2: Aceptada</li>
     *   <li>3: Rechazada</li>
     *   <li>4: En revisión</li>
     *   <li>5: Completado</li>
     * </ul>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * PATCH /solicitudasesoria/1/2
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>200 OK: "Estado actualizado correctamente"</li>
     *   <li>403 Forbidden: Solo agrónomos pueden actualizar</li>
     *   <li>500 Internal Server Error: Error interno</li>
     * </ul>
     * 
     * @param ctx el contexto de la petición HTTP
     */
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

    /**
     * Elimina una solicitud de asesoría.
     * Solo accesible para agrónomos.
     * @param ctx el contexto de la petición HTTP
     */
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


    /**
     * Extrae el rol del usuario del contexto.
     * @param ctx el contexto de la petición
     * @return el rol del usuario
     */
    private int extraerRol(Context ctx) {
        Object rolAttr = ctx.attribute("rol");
        return extraerEnteroSeguro(rolAttr);
    }

    /**
     * Extrae el ID del usuario del contexto.
     * @param ctx el contexto de la petición
     * @return el ID del usuario
     */
    private int extraerUsuarioId(Context ctx) {
        Object idAttr = ctx.attribute("usuarioId");
        return extraerEnteroSeguro(idAttr);
    }

    /**
     * Convierte un objeto a entero de forma segura.
     * @param valor el valor a convertir
     * @return el entero convertido o -1 si no se puede
     */
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

    /**
     * Obtiene el nombre del tipo de un objeto.
     * @param obj el objeto
     * @return el nombre del tipo
     */
    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }
}