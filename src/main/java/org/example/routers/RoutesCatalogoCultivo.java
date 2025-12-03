package org.example.routers;
import io.javalin.Javalin;
import org.example.controller.CatalogoCultivoController;
import org.example.service.CatalogoCultivoService;

/**
 * Configurador de rutas para el catálogo de cultivos.
 * Define rutas públicas para consultar cultivos.
 */
public class RoutesCatalogoCultivo {
    private final CatalogoCultivoController controller;

    /**
     * Constructor que inicializa el servicio y controlador de cultivos.
     */
    public RoutesCatalogoCultivo() {
        CatalogoCultivoService servicio = new CatalogoCultivoService();
        this.controller = new CatalogoCultivoController(servicio);
    }

    /**
     * Registra todas las rutas del catálogo de cultivos.
     * @param app la instancia de Javalin donde registrar las rutas
     */
    public void register(Javalin app) {
        app.get("/catalogo/cultivos", controller::obtenerTodos);
        app.get("/catalogo/cultivos/{id}", controller::obtenerPorId);

        /// //examen////

        app.get("/cultivos/nombre/{nombre}", controller::obtenerPorNombre);
        app.get("/cultivos/buscar", controller::obtenerPorCoincidencia);

    }

}
