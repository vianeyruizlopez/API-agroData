package org.example;

import io.javalin.Javalin;
import org.example.routers.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
/// /solo probando que todo este en mi nuevo git

/**
 * Clase principal que inicia la aplicación AgroData API.
 * Configura el servidor Javalin, CORS y registra todas las rutas.
 */
public class Main {


    private static final Set<String> ALLOWED_ORIGINS = new HashSet<>(Arrays.asList(
            
            "http://13.219.151.36",
            "https://3.223.91.98",
            "http://3.223.91.98",
            "http://agrodata.servehalflife.com",
            "http://www.agrodata.servehalflife.com",
            "https://agrodata.servehalflife.com",
            "https://www.agrodata.servehalflife.com"

    ));


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


    private static final Pattern[] LOCAL_PATTERNS = {
            Pattern.compile("http://localhost(:\\d+)?"),
            Pattern.compile("http://127\\.0\\.0\\.1(:\\d+)?"),
            Pattern.compile("https://localhost(:\\d+)?"),
            Pattern.compile("https://127\\.0\\.0\\.1(:\\d+)?")
    };

    /**
     * Método principal que inicia el servidor en el puerto 7000.
     * Configura CORS para permitir conexiones desde diferentes orígenes.
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        Javalin app = Javalin.create().start("0.0.0.0", 7000);


        app.before("/*", ctx -> {
            String origin = ctx.header("Origin");
            String allowedOrigin = determineAllowedOrigin(origin);


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
        System.out.println(" AgroData API iniciada en puerto 7000");
        System.out.println(" CORS configurado para AWS y desarrollo local (cualquier puerto)");
    }

    /**
     * Determina qué origen está permitido para las peticiones CORS.
     * @param origin el origen de la petición HTTP
     * @return el origen permitido para CORS
     */
    private static String determineAllowedOrigin(String origin) {
        if (origin == null || origin.trim().isEmpty()) {
            return getDefaultOrigin();
        }


        for (Pattern pattern : LOCAL_PATTERNS) {
            if (pattern.matcher(origin).matches()) {
                return origin;
            }
        }

        if (ALLOWED_ORIGINS.contains(origin)) {
            return origin;
        }

        for (Pattern pattern : AWS_PATTERNS) {
            if (pattern.matcher(origin).matches()) {
                return origin;
            }
        }

        String envOrigins = System.getenv("ALLOWED_ORIGINS");
        if (envOrigins != null) {
            String[] origins = envOrigins.split(",");
            for (String allowed : origins) {
                if (origin.equals(allowed.trim())) {
                    return origin;
                }
            }
        }

        return getDefaultOrigin();
    }

    /**
     * Obtiene el origen por defecto según el entorno.
     * @return origen por defecto para desarrollo o producción
     */
    private static String getDefaultOrigin() {
        String env = System.getenv("NODE_ENV");
        if ("production".equals(env)) {
            return "https://agrodata.com";
        }
        return "http://localhost:3000";
    }

    /**
     * Registra todas las rutas de la aplicación.
     * @param app instancia de Javalin donde registrar las rutas
     */
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