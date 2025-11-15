package org.example.controller;

import io.javalin.http.Context;
import org.example.model.Taller;
import org.example.service.TallerService;

import java.util.Map;

public class TallerController {
    private final TallerService service;

    public TallerController(TallerService service) {
        this.service = service;
    }

    public void obtenerTodos(Context ctx) {
        int usuarioId = extraerUsuarioId(ctx);
        int rol = extraerRol(ctx);
        System.out.println("→ Entrando a obtenerTodos | usuarioId: " + usuarioId + ", rol: " + rol);

        ctx.json(service.obtenerTaller());
    }

    public void obtenerPorId(Context ctx) {
        try {
            int usuarioId = extraerUsuarioId(ctx);
            int rol = extraerRol(ctx);
            System.out.println("→ Entrando a obtenerPorId | usuarioId: " + usuarioId + ", rol: " + rol);

            int id = Integer.parseInt(ctx.pathParam("id"));
            Taller taller = service.obtenerTallerPorId(id);
            ctx.json(taller);
        } catch (IllegalArgumentException e) {
            ctx.status(404).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error al obtener el taller: " + e.getMessage());
        }
    }

    public void agregar(Context ctx) {
        int usuarioId = extraerUsuarioId(ctx);
        int rol = extraerRol(ctx);
        System.out.println("→ Entrando a agregar | usuarioId: " + usuarioId + ", rol: " + rol);

        /*if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo agrónomos pueden agregar talleres");
            return;
        }*/

        Taller taller = ctx.bodyAsClass(Taller.class);
        service.agregarTaller(taller);
        ctx.status(201).json(taller);
    }

    public void actualizar(Context ctx) {
        int usuarioId = extraerUsuarioId(ctx);
        int rol = extraerRol(ctx);
        System.out.println("→ Entrando a actualizar | usuarioId: " + usuarioId + ", rol: " + rol);

        /*if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo agrónomos pueden actualizar talleres");
            return;
        }*/

        int id = Integer.parseInt(ctx.pathParam("id"));
        Taller actualizado = ctx.bodyAsClass(Taller.class);
        service.actualizarTaller(id, actualizado);
        ctx.status(200).json(actualizado);
    }

    public void actualizarEstado(Context ctx) {
        int usuarioId = extraerUsuarioId(ctx);
        int rol = extraerRol(ctx);
        System.out.println("→ Entrando a actualizarEstado | usuarioId: " + usuarioId + ", rol: " + rol);

       /* if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo agrónomos pueden actualizar estado de talleres");
            return;
        }*/

        int id = Integer.parseInt(ctx.pathParam("id"));
        int estado = Integer.parseInt(ctx.pathParam("estado"));
        service.actualizarEstadoTaller(id, estado);
        ctx.status(200).result("Estado del taller actualizado correctamente");
    }

    public void eliminar(Context ctx) {
        int usuarioId = extraerUsuarioId(ctx);
        int rol = extraerRol(ctx);
        System.out.println("→ Entrando a eliminar | usuarioId: " + usuarioId + ", rol: " + rol);

        /*if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo agrónomos pueden eliminar talleres");
            return;
        }*/

        int id = Integer.parseInt(ctx.pathParam("id"));
        service.eliminarTaller(id);
        ctx.json(Map.of("mensaje", "Taller eliminado", "id", id));
    }

    // Métodos auxiliares para trazabilidad y conversión segura
    private int extraerRol(Context ctx) {
        Object rolAttr = ctx.attribute("rol");
        return extraerEnteroSeguro(rolAttr);
    }

    private int extraerUsuarioId(Context ctx) {
        Object idAttr = ctx.attribute("usuarioId");
        return extraerEnteroSeguro(idAttr);
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