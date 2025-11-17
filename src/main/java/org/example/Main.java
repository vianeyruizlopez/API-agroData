package org.example;

import io.javalin.Javalin;
import org.example.routers.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Main {

    // 🛡 Configuración de CORS profesional
    private static final Set<String> ALLOWED_ORIGINS = new HashSet<>(Arrays.asList(
            // Producción personalizada 🌐
            "http://13.219.151.36",
            "https://3.223.91.98",
            "http://3.223.91.98"
            //"https://agrodata.com",
            //"https://www.agrodata.com"
    ));

    // 🎯 -Patrones para dominios AWS automáticos
    private static final Pattern[] AWS_PATTERNS = {
            Pattern.compile("https://.*\\.elasticbeanstalk\\.com"),
            Pattern.compile("https://.*\\.cloudfront\\.net"),
            Pattern.compile("https://.*\\.s3\\.amazonaws\\.com"),
            Pattern.compile("https://.\\.s3-website.\\.amazonaws\\.com"),
            Pattern.compile("https://.\\.execute-api\\..\\.amazonaws\\.com"),
            Pattern.compile("https://.*\\.amplifyapp\\.com"),
            Pattern.compile("https://.*\\.vercel\\.app"),
            Pattern.compile("https://.*\\.netlify\\.app")
    };

    // 🏠 Patrones para desarrollo local - CUALQUIER PUERTO
    private static final Pattern[] LOCAL_PATTERNS = {
            Pattern.compile("http://localhost(:\\d+)?"),
            Pattern.compile("http://127\\.0\\.0\\.1(:\\d+)?"),
            Pattern.compile("https://localhost(:\\d+)?"),
            Pattern.compile("https://127\\.0\\.0\\.1(:\\d+)?")
    };

    public static void main(String[] args) {
        Javalin app = Javalin.create().start("0.0.0.0", 7000);

        // 🌐 CORS Profesional Multi-Ambiente
        app.before("/*", ctx -> {
            String origin = ctx.header("Origin");
            String allowedOrigin = determineAllowedOrigin(origin);

            // Headers CORS optimizados 🎯
            ctx.header("Access-Control-Allow-Origin", allowedOrigin);
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
            ctx.header("Access-Control-Allow-Headers",
                    "Authorization, Content-Type, Accept, X-Requested-With, Cache-Control, confirmado, X-API-Key");
            ctx.header("Access-Control-Allow-Credentials", "true");
            ctx.header("Access-Control-Max-Age", "86400");
            ctx.header("Vary", "Origin");

            if ("OPTIONS".equals(ctx.method().toString())) {
                ctx.status(204);
                ctx.skipRemainingHandlers();
            }
        });

        registerRoutes(app);
        System.out.println("🚀 AgroData API iniciada en puerto 7000");
        System.out.println("🌐 CORS configurado para AWS y desarrollo local (cualquier puerto)");
    }

    private static String determineAllowedOrigin(String origin) {
        if (origin == null || origin.trim().isEmpty()) {
            return getDefaultOrigin();
        }

        // 1️⃣ Verificar desarrollo local - CUALQUIER PUERTO 🏠
        for (Pattern pattern : LOCAL_PATTERNS) {
            if (pattern.matcher(origin).matches()) {
                return origin; // ✅ Permite cualquier puerto local
            }
        }

        // 2️⃣ Verificar lista exacta de producción
        if (ALLOWED_ORIGINS.contains(origin)) {
            return origin;
        }

        // 3️⃣ Verificar patrones AWS
        for (Pattern pattern : AWS_PATTERNS) {
            if (pattern.matcher(origin).matches()) {
                return origin;
            }
        }

        // 4️⃣ Variables de entorno dinámicas 🌍
        String envOrigins = System.getenv("ALLOWED_ORIGINS");
        if (envOrigins != null) {
            String[] origins = envOrigins.split(",");
            for (String allowed : origins) {
                if (origin.equals(allowed.trim())) {
                    return origin;
                }
            }
        }

        // 5️⃣ Fallback seguro
        return getDefaultOrigin();
    }

    private static String getDefaultOrigin() {
        String env = System.getenv("NODE_ENV");
        if ("production".equals(env)) {
            return "https://agrodata.com";
        }
        return "http://localhost:3000";
    }

    private static void registerRoutes(Javalin app) {
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