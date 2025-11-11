package org.example.routers;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.example.controller.SolicitudAsesoriaController;
import org.example.service.SolicitudAsesoriaService;
import org.example.util.JwtUtil;

public class RoutesSolicitudAsesoria {
    private final SolicitudAsesoriaController controller;

    public RoutesSolicitudAsesoria() {
        SolicitudAsesoriaService service = new SolicitudAsesoriaService();
        this.controller = new SolicitudAsesoriaController(service);
    }

    public void register(Javalin app) {
        // Middleware para validar token y extraer atributos
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
        };

        // Middleware solo para agrónomo (rol = 1)
        Handler soloAgronomo = ctx -> {
            validarToken.handle(ctx);
            int rol = ctx.attribute("rol");
            if (rol != 1) {
                throw new UnauthorizedResponse("Acceso denegado: solo el agrónomo puede acceder a esta ruta");
            }
        };

        // Middleware solo para agricultor (rol = 2)
        Handler soloAgricultor = ctx -> {
            validarToken.handle(ctx);
            int rol = ctx.attribute("rol");
            if (rol != 2) {
                throw new UnauthorizedResponse("Acceso denegado: solo el agricultor puede acceder a esta ruta");
            }
        };

        // Aplicar middleware según método HTTP
        app.before("/solicitudasesoria", ctx -> {
            validarToken.handle(ctx);
            if (ctx.method().equals("POST")) {
                soloAgricultor.handle(ctx);
            } else if (ctx.method().equals("GET")) {
                soloAgronomo.handle(ctx);
            }
        });

        app.before("/solicitudasesoria/{id}", soloAgronomo); // GET por ID, DELETE
        app.before("/solicitudasesoria/{id}/{estado}", soloAgronomo); // PATCH

        // Rutas protegidas
        app.get("/solicitudasesoria/{id}", controller::obtenerPorId);
        app.get("/solicitudasesoria", controller::obtenerTodas);
        app.post("/solicitudasesoria", controller::registrar);
        app.patch("/solicitudasesoria/{id}/{estado}", controller::actualizarEstado);
        app.delete("/solicitudasesoria/{id}", controller::eliminar);
    }
}