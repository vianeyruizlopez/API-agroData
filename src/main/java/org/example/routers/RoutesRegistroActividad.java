package org.example.routers;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.example.config.DataBase;
import org.example.controller.RegistroActividadController;
import org.example.repository.RegistroActividadRepository;
import org.example.service.RegistroActividadService;
import org.example.util.JwtUtil;

public class RoutesRegistroActividad {
    private final RegistroActividadController controller;

    public RoutesRegistroActividad() {
        RegistroActividadRepository repo = new RegistroActividadRepository(DataBase.getDataSource());
        RegistroActividadService service = new RegistroActividadService(repo);
        this.controller = new RegistroActividadController(service);
    }

    public void register(Javalin app) {

        Handler validarToken = ctx -> {
            System.out.println("→ Middleware ejecutado para: " + ctx.path());

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

            Object rawId = claims.get("id");
            Object rawRol = claims.get("rol");

            int usuarioId = JwtUtil.extraerEnteroSeguro(rawId);
            int rol = JwtUtil.extraerEnteroSeguro(rawRol);

            ctx.attribute("usuarioId", usuarioId);
            ctx.attribute("rol", rol);

            System.out.println("→ usuarioId seteado: " + usuarioId);
            System.out.println("→ rol seteado: " + rol);
        };

        app.before("/registroactividades/", validarToken);

        app.get("/registroactividades/", controller::obtenerRegistros);

        app.post("/registroactividades/", controller::agregarRegistros);
    }
}