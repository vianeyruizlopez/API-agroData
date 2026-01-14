package org.example.controller;

import io.javalin.http.Context;
import org.example.model.Estado;
import org.example.service.EstadoService;
import org.example.service.IEstadoService;

/**
 * Controlador para manejar operaciones de estados del sistema.
 * Permite consultar estados por ID y obtener todos los estados.
 */
public class EstadoController {
    private final IEstadoService servicio;

    /**
     * Constructor que recibe el servicio de estados.
     * @param servicio servicio para manejar estados
     */
    public EstadoController(IEstadoService servicio) {
        this.servicio = servicio;
    }

    /**
     * Obtiene todos los estados del sistema.
     * @param ctx contexto de la petición HTTP
     */
    public void obtenerTodos(Context ctx) {
        ctx.json(servicio.obtenerEstados());
    }

    /**
     * Obtiene un estado por su ID.
     * @param ctx contexto de la petición HTTP
     */
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