package org.example.model;

import java.time.LocalDateTime;

/**
 * Clase que representa un reporte de desempeño de un plan de cultivo.
 * Contiene estadísticas sobre el progreso y cumplimiento de tareas.
 */
public class ReporteDesempeno {
    int idReporte;
    int idPlan;
    LocalDateTime fechaGeneracion;
    String observaciones;
    int totalTareas;
    int tareasCompletadas;
    int tareasAceptadas;
    int tareasPendientes;
    /**
     * Número de tareas atrasadas en el plan.
     * Campo importante para evaluar el desempeño.
     */
    int tareasAtrasadas;
    float porcentageCompletadas;

    /**
     * Constructor vacío para crear un reporte sin datos.
     */
    public ReporteDesempeno() {
    }

    /**
     * Obtiene el ID del reporte.
     * @return el ID del reporte
     */
    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    /**
     * Obtiene la fecha de generación del reporte.
     * @return la fecha y hora de generación
     */
    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getTotalTareas() {
        return totalTareas;
    }

    public void setTotalTareas(int totalTareas) {
        this.totalTareas = totalTareas;
    }

    public int getTareasCompletadas() {
        return tareasCompletadas;
    }

    public void setTareasCompletadas(int tareasCompletadas) {
        this.tareasCompletadas = tareasCompletadas;
    }

    public int getTareasAceptadas() {
        return tareasAceptadas;
    }

    public void setTareasAceptadas(int tareasAceptadas) {
        this.tareasAceptadas = tareasAceptadas;
    }

    public int getTareasPendientes() {
        return tareasPendientes;
    }

    public void setTareasPendientes(int tareasPendientes) {
        this.tareasPendientes = tareasPendientes;
    }

    /**
     * Obtiene el número de tareas atrasadas.
     * @return el número de tareas atrasadas
     */
    public int getTareasAtrasadas() {
        return tareasAtrasadas;
    }

    /**
     * Establece el número de tareas atrasadas.
     * @param tareasAtrasadas el número de tareas atrasadas
     */
    public void setTareasAtrasadas(int tareasAtrasadas) {
        this.tareasAtrasadas = tareasAtrasadas;
    }

    /**
     * Obtiene el porcentaje de tareas completadas.
     * @return el porcentaje de completado (0-100)
     */
    public float getPorcentageCompletadas() {
        return porcentageCompletadas;
    }

    public void setPorcentageCompletadas(float porcentageCompletadas) {
        this.porcentageCompletadas = porcentageCompletadas;
    }

    /**
     * Convierte el reporte a una representación de texto.
     * @return cadena con toda la información del reporte
     */
    @Override
    public String toString() {
        return "ReporteDesempeno{" +
                "idReporte=" + idReporte +
                ", idPlan=" + idPlan +
                ", fechaGeneracion=" + fechaGeneracion +
                ", observaciones='" + observaciones + '\'' +
                ", totalTareas=" + totalTareas +
                ", tareasCompletadas=" + tareasCompletadas +
                ", tareasAceptadas=" + tareasAceptadas +
                ", tareasPendientes=" + tareasPendientes +
                ", tareasAtrasadas=" + tareasAtrasadas + // <-- ★ AÑADIDO ★
                ", porcentageCompletadas=" + porcentageCompletadas +
                '}';
    }
}