package org.example;

import io.javalin.Javalin;
import org.example.routers.*;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            // Aquí puedes agregar otras configuraciones si lo necesitas
        }).start(7000);

        // 🌐 Configuración CORS global
        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Authorization, Content-Type");
        });

        // 🔄 Manejo explícito de preflight OPTIONS con headers
        app.options("/*", ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Authorization, Content-Type");
            ctx.status(200);
        });

        // 📦 Registro de rutas
        new RoutesCatalogoCultivo().register(app);
        new RoutesEstado().register(app);
        new RoutesNotificacion().register(app);
        new RoutesProyectos().register(app);
        new RoutesRegistroActividad().register(app);
        new RoutesReporteDesempeno().register(app);
        new RoutesSolicitudAsesoria().register(app);
        new RoutesSolicitudTaller().register(app);
        new RoutesTaller().register(app);
        new RoutesTarea().register(app);
        new RoutesUsuario().register(app);
        new RoutesTipoTerreno().register(app);
    }
}