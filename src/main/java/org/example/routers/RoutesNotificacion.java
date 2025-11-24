package org.example.routers;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.example.controller.NotificacionController;
import org.example.service.NotificacionService;
import org.example.util.JwtUtil;

public class RoutesNotificacion {
    private final NotificacionController controller;

    public RoutesNotificacion() {
        NotificacionService servicio = new NotificacionService();
        this.controller = new NotificacionController(servicio);
    }

    public void register(Javalin app) {
        Handler validarToken = ctx -> {
            System.out.println("Middleware ejecutado para: " + ctx.path());

            String authHeader = ctx.header("Authorization");
            System.out.println("Header Authorization recibido: " + authHeader);

            if (authHeader == null || !authHeader.trim().toLowerCase().startsWith("bearer ")) {
                System.out.println("Token no proporcionado o mal formado");
                throw new UnauthorizedResponse("Token no proporcionado o mal formado");
            }

            String token = authHeader.trim().substring("Bearer ".length());
            System.out.println("Token extraído: " + token);

            Claims claims;
            try {
                claims = JwtUtil.validarToken(token);
            } catch (JwtException e) {
                System.out.println("Error al validar token: " + e.getClass().getSimpleName() + " → " + e.getMessage());
                throw new UnauthorizedResponse("Token inválido o expirado");
            }

            Object rawId = claims.get("id");
            Object rawRol = claims.get("rol");

            System.out.println("Raw ID en middleware: " + rawId + " (tipo: " + tipo(rawId) + ")");
            System.out.println("Raw ROL en middleware: " + rawRol + " (tipo: " + tipo(rawRol) + ")");

            int usuarioId = JwtUtil.extraerEnteroSeguro(rawId);
            int rol = JwtUtil.extraerEnteroSeguro(rawRol);

            ctx.attribute("usuarioId", usuarioId);
            ctx.attribute("rol", rol);

            System.out.println("Usuario ID seteado en ctx.attribute: " + usuarioId);
            System.out.println("Rol seteado en ctx.attribute: " + rol);
        };


        app.before("/notificacionesagricultor", validarToken);
        app.before("/notificacionesagronomo", validarToken);


        app.get("/notificacionesagricultor", controller::obtenerTodasNotificaciones);
        app.get("/notificacionesagronomo", controller::obtenerNotificaciones);
    }


    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }
}