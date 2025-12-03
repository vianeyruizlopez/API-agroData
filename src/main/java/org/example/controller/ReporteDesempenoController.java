package org.example.controller;

import io.javalin.http.Context;
import org.example.model.ReporteDesempeno;
import org.example.service.IReporteDesempenoService;
import org.example.service.ReporteDesempenoService;

import java.sql.SQLException;

/**
 * Controlador para manejar reportes de desempeño de planes de cultivo.
 * Solo accesible para agrónomos.
 */
public class ReporteDesempenoController {
    private final IReporteDesempenoService service;

    /**
     * Constructor que recibe el servicio de reportes de desempeño.
     * @param service servicio para manejar reportes
     */
    public ReporteDesempenoController(IReporteDesempenoService service) {
        this.service = service;
    }

    /**
     * Obtiene un reporte de desempeño por ID de plan.
     * 
     * <p><strong>Endpoint:</strong> GET /obtenerReporteDesempeno/{idPlan}</p>
     * <p><strong>Acceso:</strong> Solo agrónomos (rol 1)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * GET /obtenerReporteDesempeno/1
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>
     * HTTP/1.1 200 OK
     * {
     *   "idReporte": 1,
     *   "idPlan": 1,
     *   "fechaGeneracion": "2024-01-25T10:00:00",
     *   "observaciones": "El plan se está ejecutando según lo esperado",
     *   "eficiencia": 85.5
     * }
     * </pre>
     * 
     * @param ctx contexto de la petición HTTP
     */
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

    /**
     * Registra un nuevo reporte de desempeño.
     * 
     * <p><strong>Endpoint:</strong> POST /registrarReporteDesempeno</p>
     * <p><strong>Acceso:</strong> Solo agrónomos (rol 1)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * POST /registrarReporteDesempeno
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * Content-Type: application/json
     * 
     * {
     *   "idPlan": 1,
     *   "fechaGeneracion": "2024-01-25T10:00:00",
     *   "observaciones": "Evaluación mensual del progreso del plan de cultivo"
     * }
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>201 Created: "Reporte registrado correctamente para el plan {id}"</li>
     *   <li>403 Forbidden: Solo agrónomos pueden registrar reportes</li>
     *   <li>400 Bad Request: Error en formato JSON</li>
     * </ul>
     * 
     * @param ctx contexto de la petición HTTP
     */
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

    /**
     * Convierte un objeto a entero de forma segura.
     * @param valor el valor a convertir
     * @return el entero convertido o -1 si hay error
     */
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


    /**
     * Obtiene el nombre del tipo de un objeto.
     * @param obj el objeto
     * @return nombre del tipo o "null"
     */
    private String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }
}