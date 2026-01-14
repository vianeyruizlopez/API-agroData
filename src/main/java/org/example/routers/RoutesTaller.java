package org.example.routers;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.example.controller.TallerController;
import org.example.repository.TallerRepositoryImpl;
import org.example.service.ITallerService;
import org.example.service.TallerService;
import org.example.util.JwtUtil;

/**
 * Configurador de rutas para talleres.
 * Define rutas CRUD para talleres agrícolas.
 */
public class RoutesTaller {
    private final TallerController controller;

    /**
     * Constructor que inicializa repositorio, servicio y controlador.
     */
    public RoutesTaller() {
        ITallerService service = new TallerService(new TallerRepositoryImpl());
        this.controller = new TallerController(service);
    }
    /**
     * Registra todas las rutas de talleres con autenticación.
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


        Handler soloAgronomo = ctx -> {
            validarToken.handle(ctx);
            int rol = ctx.attribute("rol");
            if (rol != 1) {
                throw new UnauthorizedResponse("Acceso denegado: solo agrónomos pueden modificar talleres");
            }
        };

        // Middleware para todos (GETs)
        app.before("/talleres/", validarToken);
        app.before("/talleres/{id}", validarToken);


        app.before("/talleres/", ctx -> {
            if (ctx.method().equals("POST")) soloAgronomo.handle(ctx);
        });

        app.before("/talleres/{id}", ctx -> {
            if (ctx.method().equals("PUT") || ctx.method().equals("DELETE")) soloAgronomo.handle(ctx);
        });

        app.before("/catalogotaller/{id}/{estado}", soloAgronomo); // PATCH


        app.get("/talleres/", controller::obtenerTodos);
        app.get("/talleres/{id}", controller::obtenerPorId);
        app.post("/talleres/", controller::agregar);
        app.put("/talleres/{id}", controller::actualizar);
        app.patch("/catalogotaller/{id}/{estado}", controller::actualizarEstado);
        app.delete("/talleres/{id}", controller::eliminar);
    }
}
