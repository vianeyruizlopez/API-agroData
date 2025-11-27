package org.example.routers;
import io.javalin.Javalin;
import org.example.controller.CatalogoCultivoController;
import org.example.service.CatalogoCultivoService;


public class RoutesCatalogoCultivo {
    private final CatalogoCultivoController controller;

    public RoutesCatalogoCultivo() {
        CatalogoCultivoService servicio = new CatalogoCultivoService();
        this.controller = new CatalogoCultivoController(servicio);
    }

    public void register(Javalin app) {
        app.get("/catalogo/cultivos", controller::obtenerTodos);
        app.get("/catalogo/cultivos/{id}", controller::obtenerPorId);

        /// //examen////

        app.get("/cultivos/nombre/{nombre}", controller::obtenerPorNombre);
        app.get("/cultivos/buscar", controller::obtenerPorCoincidencia);

    }

}
