package org.example;

import io.javalin.Javalin;
import org.example.routers.*;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start("0.0.0.0", 7000);

        // 🌐 CORS para localhost:3000, localhost:80 + JWT
        app.before("/*", ctx -> {
            String origin = ctx.header("Origin");
            if (origin != null && (origin.contains("localhost") || origin.contains("127.0.0.1"))) {
                ctx.header("Access-Control-Allow-Origin", origin);
            } else {
                ctx.header("Access-Control-Allow-Origin", "http://localhost");
            }

            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-Requested-With, Cache-Control");
            ctx.header("Access-Control-Allow-Credentials", "true");
            ctx.header("Access-Control-Max-Age", "86400");

            if ("OPTIONS".equals(ctx.method().toString())) {
                ctx.status(204);
                ctx.skipRemainingHandlers();
            }
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