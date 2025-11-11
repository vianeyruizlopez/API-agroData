package org.example.controller;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.model.RegistroActividad;
import org.example.service.RegistroActividadService;

import java.util.*;

public class RegistroActividadController {
    private final RegistroActividadService service;

    public RegistroActividadController(RegistroActividadService service) {
        this.service = service;
    }

    public void obtenerRegistros(Context ctx) {
        // Ambos roles pueden acceder
        Object idAttr = ctx.attribute("usuarioId");
        Object rolAttr = ctx.attribute("rol");

        System.out.println("→ Entrando a obtenerRegistros");
        System.out.println("usuarioId desde ctx: " + idAttr + " (tipo: " + tipo(idAttr) + ")");
        System.out.println("rol desde ctx: " + rolAttr + " (tipo: " + tipo(rolAttr) + ")");

        ctx.json(service.obtenerRegistroActividad());
    }

    public void agregarRegistros(Context ctx) {
        // Solo rol 2 (agricultor) puede registrar
        Object rolAttr = ctx.attribute("rol");
        int rol = extraerEnteroSeguro(rolAttr);

        System.out.println("→ Entrando a agregarRegistros");
        System.out.println("rol desde ctx: " + rolAttr + " (tipo: " + tipo(rolAttr) + ")");

        if (rol != 2) {
            ctx.status(403).result("Acceso denegado: solo agricultores pueden registrar actividades");
            return;
        }

        Gson gson = new Gson();
        RegistroActividad[] registroActividadsArray = gson.fromJson(ctx.body(), RegistroActividad[].class);
        List<RegistroActividad> talleres = Arrays.asList(registroActividadsArray);

        List<RegistroActividad> procesados = new ArrayList<>();
        for (RegistroActividad registroActividad : talleres) {
            service.agregarActividad(registroActividad);
            procesados.add(registroActividad);
        }

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Registro de actividad procesados correctamente");
        respuesta.put("registroActividadProcesados", procesados);

        ctx.status(201).json(respuesta);
    }

    // Método auxiliar para trazabilidad de tipos
    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }

    // Conversión robusta a entero seguro
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
}