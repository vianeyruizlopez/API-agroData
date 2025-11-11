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
            System.out.println("→ Claims extraídos: " + claims);
            System.out.println("→ ID: " + claims.get("id") + " | ROL: " + claims.get("rol"));

            System.out.println("Raw ID en middleware: " + rawId + " (tipo: " + tipo(rawId) + ")");
            System.out.println("Raw ROL en middleware: " + rawRol + " (tipo: " + tipo(rawRol) + ")");

            int usuarioId = JwtUtil.extraerEnteroSeguro(rawId);
            int rol = JwtUtil.extraerEnteroSeguro(rawRol);

            ctx.attribute("usuarioId", usuarioId);
            ctx.attribute("rol", rol);

            System.out.println("Usuario ID seteado en ctx.attribute: " + usuarioId);
            System.out.println("Rol seteado en ctx.attribute: " + rol);
        };

        // Middleware aplicado a TODAS las rutas que requieren autenticación
        app.before("/solicitudtaller", validarToken); // ← ✅ necesario para POST directo
        app.before("/solicitudtaller/*", validarToken);
        app.before("/getTallerForStatus/*", validarToken);
        app.before("/solicitudesTallerAsesoria", validarToken);
        app.before("/solicitudtaller/misolicitudes", validarToken);

        // Rutas protegidas - Solo agrónomo (rol 1)
        app.get("/solicitudtaller/{id}", controller::obtenerPorId);
        app.get("/solicitudtaller", controller::obtenerTodas);
        app.patch("/solicitudtaller/{id}/{estado}", controller::actualizarEstado);
        app.delete("/solicitudtaller/{id}", controller::eliminar);
        app.get("/solicitudesTallerAsesoria", controller::obtenerSolicitudesTallerAsesoria);

        // Ruta protegida - Ambos roles pueden ver
        app.get("/getTallerForStatus/{idEstado}", controller::obtenerPorEstado);

        // Ruta protegida - Solo agricultor (rol 2)
        app.post("/solicitudtaller", controller::registrar);

        // Ruta protegida - Usuario ve sus propias solicitudes
        app.get("/solicitudtaller/misolicitudes", controller::obtenerPorUsuario);
    }

    // Método auxiliar para trazabilidad de tipos
    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }
}