package org.example.routers;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.example.controller.TareaController;
import org.example.service.TareaService;
import org.example.util.JwtUtil;

/**
 * Configurador de rutas para tareas agrícolas.
 * Define las rutas HTTP para manejo de tareas y reportes de plaga.
 */
public class RoutesTarea {
    private final TareaController controller;

    /**
     * Constructor que inicializa el servicio y controlador de tareas.
     */
    public RoutesTarea() {
        TareaService service = new TareaService();
        this.controller = new TareaController(service);
    }

    /**
     * Registra todas las rutas de tareas en la aplicación.
     * Incluye rutas para tareas y reportes de plaga con autenticación.
     * @param app la instancia de Javalin donde registrar las rutas
     */
    public void register(Javalin app) {

        Handler validarToken = ctx -> {
            String authHeader = ctx.header("Authorization");
            if (authHeader == null || !authHeader.trim().toLowerCase().startsWith("bearer ")) {
                throw new UnauthorizedResponse("Token no proporcionado o mal formado");
            }

            String token = authHeader.trim().substring("Bearer ".length());
            Claims claims;
            try {
                claims = JwtUtil.validarToken(token);
            } catch (JwtException e) {
                throw new UnauthorizedResponse("Token inválido o expirado");
            }

            int usuarioId = JwtUtil.extraerEnteroSeguro(claims.get("id"));
            int rol = JwtUtil.extraerEnteroSeguro(claims.get("rol"));

            ctx.attribute("usuarioId", usuarioId);
            ctx.attribute("rol", rol);

            System.out.println("→ Token validado | usuarioId: " + usuarioId + ", rol: " + rol);
        };


        app.before("/tarea", validarToken);
        app.before("/tarea/*", validarToken);
        app.before("/tarea/conreporteplaga", validarToken);
        app.before("/reporteplaga", validarToken);


        app.get("/tarea/conreporteplaga", controller::obtenerConReportePlaga);
        app.post("/reporteplaga", controller::registrarReportePlaga);
        app.get("/tarea/{idTarea}", controller::obtenerPorId);
        app.get("/tarea", controller::obtenerTodas);
        app.post("/tarea", controller::registrar);
        app.put("/tarea/{idTarea}", controller::actualizarCompleta);
        app.patch("/tarea/{id}/{estado}", controller::actualizarEstado);
        app.delete("/tarea/{id}", controller::eliminar);
    }
}