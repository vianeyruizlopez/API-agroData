package org.example.controller;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.model.RegistroActividad;
import org.example.service.IRegistroActividadService;
import org.example.service.RegistroActividadService;

import java.util.*;

/**
 * Controlador para manejar registros de actividades agrícolas.
 * Permite consultar y agregar registros de actividades.
 */
public class RegistroActividadController {
    private final IRegistroActividadService service;

    /**
     * Constructor que recibe el servicio de registro de actividades.
     * @param service servicio para manejar registros de actividad
     */
    public RegistroActividadController(IRegistroActividadService service) {
        this.service = service;
    }

    /**
     * Obtiene todos los registros de actividades.
     * 
     * <p><strong>Endpoint:</strong> GET /registroactividades/</p>
     * <p><strong>Acceso:</strong> Requiere autenticación</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * GET /registroactividades/
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>
     * HTTP/1.1 200 OK
     * [
     *   {
     *     "idRegistro": 1,
     *     "idUsuario": 1,
     *     "actividad": "Siembra de maíz",
     *     "fecha": "2024-01-20T08:00:00",
     *     "observaciones": "Siembra realizada en condiciones óptimas"
     *   }
     * ]
     * </pre>
     * 
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerRegistros(Context ctx) {

        Object idAttr = ctx.attribute("usuarioId");
        Object rolAttr = ctx.attribute("rol");

        System.out.println("→ Entrando a obtenerRegistros");
        System.out.println("usuarioId desde ctx: " + idAttr + " (tipo: " + tipo(idAttr) + ")");
        System.out.println("rol desde ctx: " + rolAttr + " (tipo: " + tipo(rolAttr) + ")");

        ctx.json(service.obtenerRegistroActividad());
    }

    /**
     * Agrega nuevos registros de actividades.
     * 
     * <p><strong>Endpoint:</strong> POST /registroactividades/</p>
     * <p><strong>Acceso:</strong> Solo agricultores (rol 2)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * POST /registroactividades/
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * Content-Type: application/json
     * 
     * [
     *   {
     *     "idUsuario": 1,
     *     "actividad": "Riego del cultivo",
     *     "fecha": "2024-01-21T06:00:00",
     *     "observaciones": "Riego matutino completado"
     *   }
     * ]
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>
     * HTTP/1.1 201 Created
     * {
     *   "mensaje": "Registro de actividad procesados correctamente",
     *   "registroActividadProcesados": [...]
     * }
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>201 Created: Registros procesados exitosamente</li>
     *   <li>403 Forbidden: Solo agricultores pueden registrar actividades</li>
     * </ul>
     * 
     * @param ctx contexto de la petición HTTP
     */
    public void agregarRegistros(Context ctx) {

        Object rolAttr = ctx.attribute("rol");
        int rol = extraerEnteroSeguro(rolAttr);

        System.out.println("→ Entrando a agregarRegistros");
        System.out.println("rol desde ctx: " + rolAttr + " (tipo: " + tipo(rolAttr) + ")");

        if (rol != 2) {
            ctx.status(403).result("Acceso denegado: solo agricultores pueden registrar actividades");
            return;
        }

        Gson gson = new Gson();
        RegistroActividad[] registroActividadsArray = gson.fromJson(ctx.body(), RegistroActividad[].class);
        List<RegistroActividad> talleres = Arrays.asList(registroActividadsArray);

        List<RegistroActividad> procesados = new ArrayList<>();
        for (RegistroActividad registroActividad : talleres) {
            service.agregarActividad(registroActividad);
            procesados.add(registroActividad);
        }

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Registro de actividad procesados correctamente");
        respuesta.put("registroActividadProcesados", procesados);

        ctx.status(201).json(respuesta);
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
}