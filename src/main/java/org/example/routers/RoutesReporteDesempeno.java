package org.example.routers;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.example.controller.ReporteDesempenoController;
import org.example.service.ReporteDesempenoService;
import org.example.util.JwtUtil;

public class RoutesReporteDesempeno {
    private final ReporteDesempenoController controller;

    public RoutesReporteDesempeno() {
        ReporteDesempenoService service = new ReporteDesempenoService();
        this.controller = new ReporteDesempenoController(service);
    }

    public void register(Javalin app) {
        // Middleware para validar token y rol
        Handler validarTokenAgronomo = ctx -> {
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

            if (rol != 1) {
                throw new UnauthorizedResponse("Acceso denegado: solo el agrónomo puede acceder a reportes de desempeño");
            }
        };

        // Protege ambas rutas
        app.before("/obtenerReporteDesempeno/{idPlan}", validarTokenAgronomo);
        app.before("/registrarReporteDesempeno", validarTokenAgronomo);

        // Rutas protegidas solo para agrónomo
        app.get("/obtenerReporteDesempeno/{idPlan}", controller::obtenerReporte);
        app.post("/registrarReporteDesempeno", controller::registrarReporte);
    }
}