package org.example.controller;

import io.javalin.http.Context;
import org.example.model.catalogoCultivo;
import org.example.service.CatalogoCultivoService;
import org.example.service.ICatalogoCultivoService;

import java.util.List;

/**
 * Controlador para manejar operaciones de catálogo de cultivos.
 * Permite consultar cultivos por ID, nombre y búsqueda por coincidencia.
 */
public class CatalogoCultivoController {
    private final ICatalogoCultivoService servicio;

    /**
     * Constructor que recibe el servicio de catálogo de cultivos.
     * @param servicio servicio para manejar cultivos
     */
    public CatalogoCultivoController(ICatalogoCultivoService servicio) {
        this.servicio = servicio;
    }

    /**
     * Obtiene todos los cultivos disponibles.
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerTodos(Context ctx) {
        ctx.json(servicio.obtenerCultivos());
    }

    /**
     * Obtiene un cultivo por su ID.
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            catalogoCultivo cultivo = servicio.obtenerCultivoPorId(id);
            if (cultivo != null) {
                ctx.json(cultivo);
            } else {
                ctx.status(404).result("Cultivo no encontrado");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        }
    }

    /**
     * Obtiene un cultivo por su nombre exacto.
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerPorNombre(Context ctx) {
        String nombre = ctx.pathParam("nombre");
        try {
            catalogoCultivo cultivo = servicio.obtenerCultivoPorNombre(nombre);
            ctx.json(cultivo);
        } catch (Exception e) {
            ctx.status(404).result(e.getMessage());
        }
    }


    /**
     * Busca cultivos que coincidan con el término de búsqueda.
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerPorCoincidencia(Context ctx) {
        String q = ctx.queryParam("q");
        if (q == null || q.isEmpty()) {
            ctx.status(400).result("Debe proporcionar un parámetro de búsqueda (?q=...)");
            return;
        }
        List<catalogoCultivo> cultivos = servicio.obtenerCultivosPorCoincidencia(q);
        ctx.json(cultivos);
    }

}
