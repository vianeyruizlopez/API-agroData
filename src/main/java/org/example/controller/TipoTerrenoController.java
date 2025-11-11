package org.example.controller;

import io.javalin.http.Context;
import org.example.model.TipoTerreno;
import org.example.service.TipoTerrenoService;

public class TipoTerrenoController {
    private final TipoTerrenoService service;

    public TipoTerrenoController(TipoTerrenoService service) {
        this.service = service;
    }

    public void obtenerTodos(Context ctx) {
        ctx.json(service.obtenerTipoTerreno());
    }

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