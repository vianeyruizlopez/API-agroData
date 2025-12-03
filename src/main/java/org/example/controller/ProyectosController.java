package org.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.example.model.PlanCultivo;
import org.example.service.IProyectosService;
import org.example.service.ProyectosService;

import java.util.List;
import java.util.Map;

/**
 * Controlador para manejar proyectos y planes de cultivo.
 * Permite consultar y actualizar planes de cultivo.
 */
public class ProyectosController {
    private final IProyectosService proyectosService;

    /**
     * Constructor que recibe el servicio de proyectos.
     * @param proyectosService servicio para manejar proyectos
     */
    public ProyectosController(IProyectosService proyectosService) {
        this.proyectosService = proyectosService;
    }

    /**
     * Obtiene todos los planes de cultivo.
     * 
     * <p><strong>Endpoint:</strong> GET /obtenerPlanCultivos</p>
     * <p><strong>Acceso:</strong> Requiere autenticación</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * GET /obtenerPlanCultivos
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>
     * HTTP/1.1 200 OK
     * [
     *   {
     *     "idPlan": 1,
     *     "idSolicitud": 1,
     *     "objetivo": "Maximizar la producción de maíz en 10 hectáreas",
     *     "observaciones": "Terreno con buen drenaje, ideal para maíz",
     *     "fechaCreacion": "2024-01-15T10:30:00",
     *     "idEstado": 1,
     *     "nombreEstado": "En Planificación"
     *   }
     * ]
     * </pre>
     * 
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerPlanCultivos(Context ctx) {
        Object rolAttr = ctx.attribute("rol");
        System.out.println("Rol recibido en controlador: " + rolAttr + " (tipo: " + tipo(rolAttr) + ")");

        int rol = extraerEnteroSeguro(rolAttr);
        System.out.println("Rol convertido en controlador: " + rol);


        List<PlanCultivo> planCultivosList = proyectosService.obtenerPlanCultivos();
        ctx.json(planCultivosList);
    }

    /**
     * Actualiza el estado de un plan de cultivo.
     * 
     * <p><strong>Endpoint:</strong> PATCH /planes/{idPlan}/estado/{idEstado}</p>
     * <p><strong>Acceso:</strong> Solo agrónomos (rol 1)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * PATCH /planes/1/estado/2
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>200 OK: "Estado del proyecto actualizado"</li>
     *   <li>403 Forbidden: "Acceso denegado"</li>
     * </ul>
     * 
     * @param ctx contexto de la petición HTTP
     */
    public void actualizarEstado(Context ctx) {

        Object rolAttr = ctx.attribute("rol");
        int rol = extraerEnteroSeguro(rolAttr);

        if (rol != 1) {
            ctx.status(403).result("Acceso denegado");
            return;
        }

        int idPlan = Integer.parseInt(ctx.pathParam("idPlan"));
        int nuevoEstado = Integer.parseInt(ctx.pathParam("idEstado"));

        proyectosService.actualizarEstadoPlan(idPlan, nuevoEstado);
        ctx.status(200).result("Estado del proyecto actualizado");
    }

    /**
     * Actualiza el objetivo y observaciones de un plan.
     * 
     * <p><strong>Endpoint:</strong> PUT /planes/{idSolicitud}/{idPlan}</p>
     * <p><strong>Acceso:</strong> Solo agrónomos (rol 1)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * PUT /planes/1/1
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * Content-Type: application/json
     * 
     * {
     *   "objetivo": "Maximizar la producción de maíz orgánico en 10 hectáreas",
     *   "observaciones": "Implementar técnicas de agricultura orgánica certificada"
     * }
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>200 OK: "Objetivo y observaciones actualizados correctamente"</li>
     *   <li>403 Forbidden: Solo agrónomos pueden actualizar</li>
     * </ul>
     * 
     * @param ctx contexto de la petición HTTP
     * @throws Exception si hay error en el procesamiento
     */
    public void actualizarObjetivoObservacion(Context ctx) throws Exception {
        Object rolAttr = ctx.attribute("rol");
        int rol = extraerEnteroSeguro(rolAttr);
        System.out.println("Rol recibido en controlador (PUT): " + rol + " (tipo: " + tipo(rolAttr) + ")");

        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo el agrónomo puede actualizar el plan");
            return;
        }

        int idSolicitud = Integer.parseInt(ctx.pathParam("idSolicitud"));
        int idPlan = Integer.parseInt(ctx.pathParam("idPlan"));

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> body = mapper.readValue(ctx.body(), new TypeReference<Map<String, String>>() {});

        String objetivo = body.get("objetivo");
        String observaciones = body.get("observaciones");

        proyectosService.actualizarObjetivoObservacion(idSolicitud, objetivo, idPlan, observaciones);
        ctx.status(200).result("Objetivo y observaciones actualizados correctamente");
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
