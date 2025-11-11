package org.example;
import io.javalin.Javalin;
import org.example.routers.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create (/*configuracion*/).start(7000);

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