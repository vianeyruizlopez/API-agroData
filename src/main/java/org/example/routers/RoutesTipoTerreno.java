package org.example.routers;

import io.javalin.Javalin;
import org.example.controller.TipoTerrenoController;
import org.example.service.TipoTerrenoService;

/**
 * Configurador de rutas para tipos de terreno.
 * Define rutas públicas para consultar tipos de riego.
 */
public class RoutesTipoTerreno {
    /**
     * Constructor vacío para crear un configurador de rutas de tipos de terreno sin inicializar datos.
     */
    public RoutesTipoTerreno() {
    }

    /**
     * Registra todas las rutas de tipos de terreno.
     * @param app la instancia de Javalin donde registrar las rutas
     */
    public void register(Javalin app) {
        TipoTerrenoService service = new TipoTerrenoService();
        TipoTerrenoController controller = new TipoTerrenoController(service);

        app.get("/catalogo/tipoterreno", controller::obtenerTodos);
        app.get("/catalogo/tipoterreno/{id}", controller::obtenerPorId);
    }
}
