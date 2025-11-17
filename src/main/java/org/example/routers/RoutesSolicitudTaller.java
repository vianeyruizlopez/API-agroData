package org.example.routers;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.example.controller.SolicitudTallerController;
import org.example.service.SolicitudTallerService;
import org.example.util.JwtUtil;

public class RoutesSolicitudTaller {
    private final SolicitudTallerController controller;

    public RoutesSolicitudTaller() {
        SolicitudTallerService service = new SolicitudTallerService();
        this.controller = new SolicitudTallerController(service);
    }

    public void register(Javalin app) {
        // Middleware para validar token
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

        // Middleware OPTIONS (CORS Preflight)
        app.before("/solicitudtaller", ctx -> { if (ctx.method().equals("OPTIONS")) { ctx.status(200); return; } validarToken.handle(ctx); });
        app.before("/solicitudtaller/*", ctx -> { if (ctx.method().equals("OPTIONS")) { ctx.status(200); return; } validarToken.handle(ctx); });
        app.before("/getTallerForStatus/*", ctx -> { if (ctx.method().equals("OPTIONS")) { ctx.status(200); return; } validarToken.handle(ctx); });
        app.before("/solicitudesTallerAsesoria", ctx -> { if (ctx.method().equals("OPTIONS")) { ctx.status(200); return; } validarToken.handle(ctx); });
        app.before("/solicitudtaller/misolicitudes", ctx -> { if (ctx.method().equals("OPTIONS")) { ctx.status(200); return; } validarToken.handle(ctx); });

        // Rutas
        app.get("/solicitudtaller/misolicitudes", controller::obtenerPorUsuario);
        app.get("/solicitudtaller/{id}", controller::obtenerPorId);
        app.get("/solicitudtaller", controller::obtenerTodas);
        app.delete("/solicitudtaller/{id}", controller::eliminar);
        app.get("/solicitudesTallerAsesoria", controller::obtenerSolicitudesTallerAsesoria);
        app.get("/getTallerForStatus/{idEstado}", controller::obtenerPorEstado);
        app.post("/solicitudtaller", controller::registrar);


        app.patch("/solicitudtaller/{id}/comprobante", controller::subirComprobante);

        // Ruta genérica
        app.patch("/solicitudtaller/{id}/{estado}", controller::actualizarEstado);
    }
}