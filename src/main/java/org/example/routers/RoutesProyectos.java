package org.example.routers;

import io.javalin.Javalin;
import org.example.controller.ProyectosController;
import org.example.service.ProyectosService;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.example.util.JwtUtil;

/**
 * Configurador de rutas para proyectos y planes de cultivo.
 * Define rutas para consultar y actualizar planes de cultivo.
 */
public class RoutesProyectos {
    private final ProyectosController controller;

    /**
     * Constructor que inicializa el servicio y controlador de proyectos.
     */
    public RoutesProyectos() {
        ProyectosService servicio = new ProyectosService();
        this.controller = new ProyectosController(servicio);
    }


    private Handler validarToken = ctx -> {
        System.out.println("Middleware ejecutado para: " + ctx.path());

        String authHeader = ctx.header("Authorization");
        System.out.println("Header Authorization recibido: [" + authHeader + "]");

        if (authHeader == null || !authHeader.trim().toLowerCase().startsWith("bearer ")) {
            System.out.println("Token no proporcionado o mal formado");
            throw new UnauthorizedResponse("Token no proporcionado o mal formado");
        }

        String token = authHeader.trim().substring("Bearer ".length()).trim();
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


    private Handler soloAgronomo = ctx -> {
        validarToken.handle(ctx); // primero valida el token

        Object rolAttr = ctx.attribute("rol");
        int rol = JwtUtil.extraerEnteroSeguro(rolAttr);

        if (rol != 1) {
            System.out.println("Acceso denegado: rol no autorizado para PUT");
            throw new UnauthorizedResponse("Acceso denegado: solo el agrónomo puede actualizar el plan");
        }
    };

    /**
     * Obtiene el nombre del tipo de un objeto.
     * @param obj el objeto
     * @return nombre del tipo o "null"
     */
    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }

    /**
     * Registra todas las rutas de proyectos y planes de cultivo.
     * @param app la instancia de Javalin donde registrar las rutas
     */
    public void register(Javalin app) {

        app.before("/obtenerPlanCultivos", validarToken);

        app.before("/planes/{idPlan}/estado/{idEstado}", soloAgronomo);


        app.before("/planes/{idSolicitud}/{idPlan}", soloAgronomo);

        app.patch("/planes/{idPlan}/estado/{idEstado}", controller::actualizarEstado);


        app.get("/obtenerPlanCultivos", controller::obtenerPlanCultivos);
        app.put("/planes/{idSolicitud}/{idPlan}", ctx -> {
            try {
                controller.actualizarObjetivoObservacion(ctx);
            } catch (Exception e) {
                ctx.status(500).result("Error al actualizar objetivo y observaciones");
            }
        });
    }
}