package org.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.example.model.PlanCultivo;
import org.example.service.IProyectosService;
import org.example.service.ProyectosService;

import java.util.List;
import java.util.Map;

public class ProyectosController {
    private final IProyectosService proyectosService;

    public ProyectosController(IProyectosService proyectosService) {
        this.proyectosService = proyectosService;
    }

    public void obtenerPlanCultivos(Context ctx) {
        Object rolAttr = ctx.attribute("rol");
        System.out.println("Rol recibido en controlador: " + rolAttr + " (tipo: " + tipo(rolAttr) + ")");

        int rol = extraerEnteroSeguro(rolAttr);
        System.out.println("Rol convertido en controlador: " + rol);


        List<PlanCultivo> planCultivosList = proyectosService.obtenerPlanCultivos();
        ctx.json(planCultivosList);
    }

    public void actualizarEstado(Context ctx) {

        Object rolAttr = ctx.attribute("rol");
        int rol = extraerEnteroSeguro(rolAttr);

        if (rol != 1) {
            ctx.status(403).result("Acceso denegado");
            return;
        }

        int idPlan = Integer.parseInt(ctx.pathParam("idPlan"));
        int nuevoEstado = Integer.parseInt(ctx.pathParam("idEstado"));

        proyectosService.actualizarEstadoPlan(idPlan, nuevoEstado);
        ctx.status(200).result("Estado del proyecto actualizado");
    }

    public void actualizarObjetivoObservacion(Context ctx) throws Exception {
        Object rolAttr = ctx.attribute("rol");
        int rol = extraerEnteroSeguro(rolAttr);
        System.out.println("Rol recibido en controlador (PUT): " + rol + " (tipo: " + tipo(rolAttr) + ")");

        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo el agrónomo puede actualizar el plan");
            return;
        }

        int idSolicitud = Integer.parseInt(ctx.pathParam("idSolicitud"));
        int idPlan = Integer.parseInt(ctx.pathParam("idPlan"));

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> body = mapper.readValue(ctx.body(), new TypeReference<Map<String, String>>() {});

        String objetivo = body.get("objetivo");
        String observaciones = body.get("observaciones");

        proyectosService.actualizarObjetivoObservacion(idSolicitud, objetivo, idPlan, observaciones);
        ctx.status(200).result("Objetivo y observaciones actualizados correctamente");
    }

    private int extraerEnteroSeguro(Object valor) {
        if (valor instanceof Integer) {
            return (Integer) valor;
        } else if (valor instanceof Number) {
            return ((Number) valor).intValue();
        } else if (valor instanceof String) {
            try {
                return Integer.parseInt(((String) valor).trim());
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir a entero: " + valor);
            }
        } else {
            System.out.println("Tipo inesperado para conversión a entero: " + tipo(valor));
        }
        return -1;
    }

    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }
}
