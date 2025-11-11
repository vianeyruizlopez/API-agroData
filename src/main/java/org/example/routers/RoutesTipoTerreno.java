package org.example.routers;

import io.javalin.Javalin;
import org.example.controller.TipoTerrenoController;
import org.example.service.TipoTerrenoService;

public class RoutesTipoTerreno {
    public void register(Javalin app) {
        TipoTerrenoService service = new TipoTerrenoService();
        TipoTerrenoController controller = new TipoTerrenoController(service);

        app.get("/catalogo/tipoterreno", controller::obtenerTodos);
        app.get("/catalogo/tipoterreno/{id}", controller::obtenerPorId);
    }
}
