package org.example.controller;

import io.javalin.http.Context;
import org.example.model.Taller;
import org.example.service.ITallerService;

import java.util.Map;

/**
 * Controlador para manejar operaciones de talleres.
 * Permite CRUD completo de talleres agrícolas.
 */
public class TallerController {
    private final ITallerService service;

    /**
     * Constructor que recibe el servicio de talleres.
     * @param service servicio para manejar talleres
     */
    public TallerController(ITallerService service) {
        this.service = service;
    }

    /**
     * Obtiene todos los talleres disponibles.
     * 
     * <p><strong>Endpoint:</strong> GET /talleres/</p>
     * <p><strong>Acceso:</strong> Requiere autenticación</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * GET /talleres/
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>
     * HTTP/1.1 200 OK
     * [
     *   {
     *     "idTaller": 4,
     *     "nombreTaller": "Control Orgánico de Plagas",
     *     "descripcion": "Métodos naturales y ecológicos para el control de plagas",
     *     "idEstado": 2,
     *     "costo": 900.00
     *   },
     *   {
     *     "idTaller": 5,
     *     "nombreTaller": "Fertilización Orgánica",
     *     "descripcion": "Técnicas de compostaje y fertilización natural",
     *     "idEstado": 4,
     *     "costo": 450.00
     *   }
     * ]
     * </pre>
     * 
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerTodos(Context ctx) {
        int usuarioId = extraerUsuarioId(ctx);
        int rol = extraerRol(ctx);
        System.out.println("→ Entrando a obtenerTodos | usuarioId: " + usuarioId + ", rol: " + rol);

        ctx.json(service.obtenerTaller());
    }

    /**
     * Obtiene un taller por su ID.
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerPorId(Context ctx) {
        try {
            int usuarioId = extraerUsuarioId(ctx);
            int rol = extraerRol(ctx);
            System.out.println("→ Entrando a obtenerPorId | usuarioId: " + usuarioId + ", rol: " + rol);

            int id = Integer.parseInt(ctx.pathParam("id"));
            Taller taller = service.obtenerTallerPorId(id);
            ctx.json(taller);
        } catch (IllegalArgumentException e) {
            ctx.status(404).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error al obtener el taller: " + e.getMessage());
        }
    }

    /**
     * Agrega un nuevo taller al sistema.
     * 
     * <p><strong>Endpoint:</strong> POST /talleres/</p>
     * <p><strong>Acceso:</strong> Solo agrónomos (rol 1)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * POST /talleres/
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * Content-Type: application/json
     * 
     * {
     *   "nombreTaller": "Manejo Integrado de Plagas",
     *   "descripcion": "Estrategias para el control sostenible de plagas usando métodos naturales y ecológicos",
     *   "costo": 750.00,
     *   "idEstado": 1
     * }
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>201 Created: Taller creado exitosamente</li>
     *   <li>400 Bad Request: Error de validación</li>
     *   <li>403 Forbidden: Solo agrónomos pueden crear talleres</li>
     * </ul>
     * 
     * @param ctx contexto de la petición HTTP
     */
    public void agregar(Context ctx) {
        int usuarioId = extraerUsuarioId(ctx);
        int rol = extraerRol(ctx);
        System.out.println("→ Entrando a agregar | usuarioId: " + usuarioId + ", rol: " + rol);



        Taller taller = ctx.bodyAsClass(Taller.class);
        service.agregarTaller(taller);
        ctx.status(201).json(taller);
    }

    /**
     * Actualiza un taller existente.
     * @param ctx contexto de la petición HTTP
     */
    public void actualizar(Context ctx) {
        int usuarioId = extraerUsuarioId(ctx);
        int rol = extraerRol(ctx);
        System.out.println("→ Entrando a actualizar | usuarioId: " + usuarioId + ", rol: " + rol);



        int id = Integer.parseInt(ctx.pathParam("id"));
        Taller actualizado = ctx.bodyAsClass(Taller.class);
        service.actualizarTaller(id, actualizado);
        ctx.status(200).json(actualizado);
    }

    /**
     * Actualiza el estado de un taller.
     * @param ctx contexto de la petición HTTP
     */
    public void actualizarEstado(Context ctx) {
        int usuarioId = extraerUsuarioId(ctx);
        int rol = extraerRol(ctx);
        System.out.println("→ Entrando a actualizarEstado | usuarioId: " + usuarioId + ", rol: " + rol);



        int id = Integer.parseInt(ctx.pathParam("id"));
        int estado = Integer.parseInt(ctx.pathParam("estado"));
        service.actualizarEstadoTaller(id, estado);
        ctx.status(200).result("Estado del taller actualizado correctamente");
    }

    /**
     * Elimina un taller del sistema.
     * @param ctx contexto de la petición HTTP
     */
    public void eliminar(Context ctx) {
        int usuarioId = extraerUsuarioId(ctx);
        int rol = extraerRol(ctx);
        System.out.println("→ Entrando a eliminar | usuarioId: " + usuarioId + ", rol: " + rol);



        int id = Integer.parseInt(ctx.pathParam("id"));
        service.eliminarTaller(id);
        ctx.json(Map.of("mensaje", "Taller eliminado", "id", id));
    }


    /**
     * Extrae el rol del usuario del contexto.
     * @param ctx contexto de la petición
     * @return el rol del usuario
     */
    private int extraerRol(Context ctx) {
        Object rolAttr = ctx.attribute("rol");
        return extraerEnteroSeguro(rolAttr);
    }

    /**
     * Extrae el ID del usuario del contexto.
     * @param ctx contexto de la petición
     * @return el ID del usuario
     */
    private int extraerUsuarioId(Context ctx) {
        Object idAttr = ctx.attribute("usuarioId");
        return extraerEnteroSeguro(idAttr);
    }

    /**
     * Convierte un objeto a entero de forma segura.
     * @param valor el valor a convertir
     * @return el entero convertido o -1 si hay error
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
     * @return nombre del tipo o "null"
     */
    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }
}