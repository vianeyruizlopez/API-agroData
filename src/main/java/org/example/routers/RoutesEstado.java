package org.example.routers;

import io.javalin.Javalin;
import org.example.controller.EstadoController;
import org.example.service.EstadoServiceImpl;

public class RoutesEstado {
    private final EstadoController controller;

    public RoutesEstado() {
        EstadoServiceImpl servicio = new EstadoServiceImpl();
        this.controller = new EstadoController(servicio);
    }

    public void register(Javalin app) {
        app.get("/catalogo/estados", controller::obtenerTodos);
        app.get("/catalogo/estados/{id}", controller::obtenerPorId);
    }
}