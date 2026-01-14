package org.example.routers;

import io.javalin.Javalin;
import org.example.controller.EstadoController;
import org.example.service.EstadoService;

/**
 * Configurador de rutas para estados del sistema.
 * Define rutas públicas para consultar estados.
 */
public class RoutesEstado {
    private final EstadoController controller;

    /**
     * Constructor que inicializa el servicio y controlador de estados.
     */
    public RoutesEstado() {
        EstadoService servicio = new EstadoService();
        this.controller = new EstadoController(servicio);
    }

    /**
     * Registra todas las rutas de estados del sistema.
     * @param app la instancia de Javalin donde registrar las rutas
     */
    public void register(Javalin app) {
        app.get("/catalogo/estados", controller::obtenerTodos);
        app.get("/catalogo/estados/{id}", controller::obtenerPorId);
    }
}