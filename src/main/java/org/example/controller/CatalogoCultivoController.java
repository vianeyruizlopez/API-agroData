package org.example.controller;

import io.javalin.http.Context;
import org.example.model.catalogoCultivo;
import org.example.service.CatalogoCultivoService;

import java.util.List;

public class CatalogoCultivoController {
    private final CatalogoCultivoService servicio;

    public CatalogoCultivoController(CatalogoCultivoService servicio) {
        this.servicio = servicio;
    }

    public void obtenerTodos(Context ctx) {
        ctx.json(servicio.obtenerCultivos());
    }

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

    /// //examen////

    public void obtenerPorNombre(Context ctx) {
        String nombre = ctx.pathParam("nombre");
        try {
            catalogoCultivo cultivo = servicio.obtenerCultivoPorNombre(nombre);
            ctx.json(cultivo);
        } catch (Exception e) {
            ctx.status(404).result(e.getMessage());
        }
    }


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
