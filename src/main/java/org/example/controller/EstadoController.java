package org.example.controller;

import io.javalin.http.Context;
import org.example.model.Estado;
import org.example.service.EstadoServiceImpl;

public class EstadoController {
    private final EstadoServiceImpl servicio;

    public EstadoController(EstadoServiceImpl servicio) {
        this.servicio = servicio;
    }

    public void obtenerTodos(Context ctx) {
        ctx.json(servicio.obtenerEstados());
    }

    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Estado estado = servicio.obtenerEstadoPorId(id);
            if (estado != null) {
                ctx.json(estado);
            } else {
                ctx.status(404).result("Estado no encontrado");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        }
    }
}