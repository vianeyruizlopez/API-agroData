package org.example.controller;

import io.javalin.http.Context;
import org.example.model.TipoTerreno;
import org.example.service.ITipoTerrenoService;
import org.example.service.TipoTerrenoService;

/**
 * Controlador para manejar tipos de terreno y riego.
 * Permite consultar catálogos de tipos de riego disponibles.
 */
public class TipoTerrenoController {
    private final ITipoTerrenoService service;

    /**
     * Constructor que recibe el servicio de tipos de terreno.
     * @param service servicio para manejar tipos de terreno
     */
    public TipoTerrenoController(ITipoTerrenoService service) {
        this.service = service;
    }

    /**
     * Obtiene todos los tipos de terreno disponibles.
     * 
     * <p><strong>Endpoint:</strong> GET /catalogo/tipoterreno</p>
     * <p><strong>Acceso:</strong> Público</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * GET /catalogo/tipoterreno
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>
     * HTTP/1.1 200 OK
     * [
     *   {
     *     "idTipoTerreno": 1,
     *     "nombre": "Arcilloso",
     *     "descripcion": "Terreno con alta concentración de arcilla"
     *   }
     * ]
     * </pre>
     * 
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerTodos(Context ctx) {
        ctx.json(service.obtenerTipoTerreno());
    }

    /**
     * Obtiene un tipo de terreno por su ID.
     * 
     * <p><strong>Endpoint:</strong> GET /catalogo/tipoterreno/{id}</p>
     * <p><strong>Acceso:</strong> Público</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * GET /catalogo/tipoterreno/1
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>200 OK: Datos del tipo de terreno</li>
     *   <li>404 Not Found: "Tipo de terreno no encontrado"</li>
     *   <li>400 Bad Request: "ID inválido"</li>
     * </ul>
     * 
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TipoTerreno terreno = service.obtenerTipoTerrenoPorId(id);

            if (terreno != null) {
                ctx.json(terreno);
            } else {
                ctx.status(404).result("Tipo de terreno no encontrado");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        }
    }
}