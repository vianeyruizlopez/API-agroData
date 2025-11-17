package org.example.controller;

import io.javalin.http.Context;
import org.example.model.ReporteDesempeno;
import org.example.service.ReporteDesempenoService;

import java.sql.SQLException;

public class ReporteDesempenoController {
    private final ReporteDesempenoService service;

    public ReporteDesempenoController(ReporteDesempenoService service) {
        this.service = service;
    }

    public void obtenerReporte(Context ctx) {
        try {
            System.out.println("→ Entrando a obtenerReporte");
            Object rolAttr = ctx.attribute("rol");
            Object idAttr = ctx.attribute("usuarioId");

            System.out.println("usuarioId desde ctx: " + idAttr + " (tipo: " + tipo(idAttr) + ")");
            System.out.println("rol desde ctx: " + rolAttr + " (tipo: " + tipo(rolAttr) + ")");

            int rol = extraerEnteroSeguro(rolAttr);
            if (rol != 1) {
                ctx.status(403).result("Acceso denegado: solo el agrónomo puede ver reportes de desempeño");
                return;
            }

            int idPlan = Integer.parseInt(ctx.pathParam("idPlan"));
            ReporteDesempeno reporte = service.obtenerReporteDesempeñoPorIdPlan(idPlan);
            ctx.status(200).json(reporte);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener el reporte de desempeño: " + e.getMessage());
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID de plan inválido");
        }
    }

    public void registrarReporte(Context ctx) {
        try {
            System.out.println("→ Entrando a registrarReporte");
            Object rolAttr = ctx.attribute("rol");
            Object idAttr = ctx.attribute("usuarioId");

            System.out.println("usuarioId desde ctx: " + idAttr + " (tipo: " + tipo(idAttr) + ")");
            System.out.println("rol desde ctx: " + rolAttr + " (tipo: " + tipo(rolAttr) + ")");

            int rol = extraerEnteroSeguro(rolAttr);
            if (rol != 1) {
                ctx.status(403).result("Acceso denegado: solo el agrónomo puede registrar reportes");
                return;
            }

            ReporteDesempeno reporte = ctx.bodyAsClass(ReporteDesempeno.class);
            service.registrarReporteDesempeño(
                    reporte.getIdPlan(),
                    reporte.getFechaGeneracion(),
                    reporte.getObservaciones()
            );

            ctx.status(201).result("Reporte registrado correctamente para el plan " + reporte.getIdPlan());
        } catch (SQLException e) {
            ctx.status(500).result("Error al registrar el reporte de desempeño: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(400).result("Error en el formato del JSON: " + e.getMessage());
        }
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

    // Método auxiliar para trazabilidad de tipos
    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }
}