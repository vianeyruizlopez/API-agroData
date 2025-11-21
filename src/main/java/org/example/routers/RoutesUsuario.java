package org.example.routers;

import io.javalin.Javalin;
import org.example.controller.UsuarioController;
import org.example.repository.UsuarioRepository;
import org.example.service.UsuarioService;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import org.example.util.JwtUtil;

public class RoutesUsuario {
    public void register(Javalin app) {
        UsuarioRepository repository = new UsuarioRepository();
        UsuarioService service = new UsuarioService(repository);
        UsuarioController controller = new UsuarioController(service);

        // Rutas públicas
        app.post("/registro", controller::registrar);
        app.post("/login", controller::login);
        app.post("/encriptar-password", controller::encriptarPassword);
        app.post("/recuperar-password", controller::recuperarPassword);

        // Middleware JWT
        Handler requireToken = ctx -> {
            String authHeader = ctx.header("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedResponse("Token no proporcionado");
            }

            String token = authHeader.substring(7);
            try {
                Claims claims = JwtUtil.validarToken(token);

                Object rawId = claims.get("id");
                int usuarioId = (rawId instanceof Integer) ? (Integer) rawId :
                        (rawId instanceof Number) ? ((Number) rawId).intValue() :
                                (rawId instanceof String) ? Integer.parseInt((String) rawId) : -1;

                Object rawRol = claims.get("rol");
                int rol = (rawRol instanceof Integer) ? (Integer) rawRol :
                        (rawRol instanceof Number) ? ((Number) rawRol).intValue() :
                                (rawRol instanceof String) ? Integer.parseInt((String) rawRol) : -1;

                ctx.attribute("usuarioId", usuarioId);
                ctx.attribute("rol", rol);

                System.out.println("Middleware JWT → usuarioId: " + usuarioId + ", rol: " + rol);
            } catch (Exception e) {
                throw new UnauthorizedResponse("Token inválido o expirado");
            }
        };

        // Rutas protegidas
        app.before("/perfil/*", requireToken);
        app.before("/informacionGeneral", requireToken);
        app.before("/administrarClientes", requireToken);
        app.before("/administrarClientes/*", requireToken);


        app.put("/perfil/{id}", controller::editarPerfil);
        app.get("/perfil/{id}", controller::obtenerPerfil);
        app.get("/informacionGeneral", controller::obtenerInformacionGeneral);
        app.get("/administrarClientes", controller::verTodosClientes);
        app.delete("/administrarClientes/{id}", controller::eliminarClientes);
        app.put("/administrarClientes/{id}", controller::editarClientes);

    }
}