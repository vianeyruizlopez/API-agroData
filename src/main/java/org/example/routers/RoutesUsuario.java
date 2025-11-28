package org.example.routers;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.example.controller.UsuarioController;
import org.example.repository.UsuarioRepository;
import org.example.service.UsuarioService;
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

        // --- Middleware JWT Estandarizado ---
        Handler requireToken = ctx -> {
            // Permitir OPTIONS sin token (para CORS)
            if (ctx.method().equals("OPTIONS")) {
                return;
            }

            String authHeader = ctx.header("Authorization");
            if (authHeader == null || !authHeader.trim().toLowerCase().startsWith("bearer ")) {
                throw new UnauthorizedResponse("Token no proporcionado o mal formado");
            }

            String token = authHeader.trim().substring("Bearer ".length());
            try {
                Claims claims = JwtUtil.validarToken(token);

                // Usamos JwtUtil para extracción segura, igual que en SolicitudTaller
                int usuarioId = JwtUtil.extraerEnteroSeguro(claims.get("id"));
                int rol = JwtUtil.extraerEnteroSeguro(claims.get("rol"));

                ctx.attribute("usuarioId", usuarioId);
                ctx.attribute("rol", rol);

                System.out.println("✅ Middleware Usuario → ID: " + usuarioId + ", Rol: " + rol + ", Ruta: " + ctx.path());

            } catch (JwtException e) {
                throw new UnauthorizedResponse("Token inválido o expirado");
            }
        };

        // --- Rutas Protegidas ---

        // Perfil e Información General
        app.before("/perfil/*", requireToken);
        app.before("/informacionGeneral", requireToken);

        app.get("/perfil/{id}", controller::obtenerPerfil);
        app.put("/perfil/{id}", controller::editarPerfil);
        app.get("/informacionGeneral", controller::obtenerInformacionGeneral);

        // Administración de Clientes
        app.before("/administrarClientes", requireToken);
        app.before("/administrarClientes/*", requireToken);

        app.get("/administrarClientes", controller::obtenerClientes);
        app.delete("/administrarClientes/{id}", controller::eliminarCliente);
        app.put("/administrarClientes/{id}", controller::actualizarClienteAdmin);
    }
}