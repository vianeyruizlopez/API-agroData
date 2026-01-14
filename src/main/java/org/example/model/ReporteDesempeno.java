package org.example.model;

import java.time.LocalDateTime;

/**
 * Clase que representa un reporte de desempeño de un plan de cultivo.
 * Contiene estadísticas sobre el progreso y cumplimiento de tareas.
 */
public class ReporteDesempeno {

    /**
     * Identificador único del reporte de desempeño.
     */
    int idReporte;

    /**
     * Identificador del plan de cultivo asociado al reporte.
     */
    int idPlan;

    /**
     * Fecha y hora en que se generó el reporte.
     */
    LocalDateTime fechaGeneracion;

    /**
     * Observaciones adicionales sobre el desempeño del plan.
     */
    String observaciones;

    /**
     * Número total de tareas registradas en el plan.
     */
    int totalTareas;

    /**
     * Número de tareas completadas dentro del plan.
     */
    int tareasCompletadas;

    /**
     * Número de tareas aceptadas como válidas.
     */
    int tareasAceptadas;

    /**
     * Número de tareas pendientes por realizar.
     */
    int tareasPendientes;

    /**
     * Número de tareas atrasadas en el plan.
     * Campo importante para evaluar el desempeño.
     */
    int tareasAtrasadas;

    /**
     * Porcentaje de tareas completadas respecto al total.
     */
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

    /**
     * Establece el ID del reporte.
     * @param idReporte el identificador único del reporte
     */
    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    /**
     * Obtiene el ID del plan asociado.
     * @return el ID del plan
     */
    public int getIdPlan() {
        return idPlan;
    }

    /**
     * Establece el ID del plan asociado.
     * @param idPlan el identificador del plan
     */
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

    /**
     * Establece la fecha de generación del reporte.
     * @param fechaGeneracion la fecha y hora de creación
     */
    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    /**
     * Obtiene las observaciones del reporte.
     * @return las observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Establece las observaciones del reporte.
     * @param observaciones comentarios adicionales
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Obtiene el número total de tareas.
     * @return el total de tareas
     */
    public int getTotalTareas() {
        return totalTareas;
    }

    /**
     * Establece el número total de tareas.
     * @param totalTareas cantidad de tareas
     */
    public void setTotalTareas(int totalTareas) {
        this.totalTareas = totalTareas;
    }

    /**
     * Obtiene el número de tareas completadas.
     * @return las tareas completadas
     */
    public int getTareasCompletadas() {
        return tareasCompletadas;
    }

    /**
     * Establece el número de tareas completadas.
     * @param tareasCompletadas cantidad de tareas completadas
     */
    public void setTareasCompletadas(int tareasCompletadas) {
        this.tareasCompletadas = tareasCompletadas;
    }

    /**
     * Obtiene el número de tareas aceptadas.
     * @return las tareas aceptadas
     */
    public int getTareasAceptadas() {
        return tareasAceptadas;
    }

    /**
     * Establece el número de tareas aceptadas.
     * @param tareasAceptadas cantidad de tareas aceptadas
     */
    public void setTareasAceptadas(int tareasAceptadas) {
        this.tareasAceptadas = tareasAceptadas;
    }

    /**
     * Obtiene el número de tareas pendientes.
     * @return las tareas pendientes
     */
    public int getTareasPendientes() {
        return tareasPendientes;
    }

    /**
     * Establece el número de tareas pendientes.
     * @param tareasPendientes cantidad de tareas pendientes
     */
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

    /**
     * Establece el porcentaje de tareas completadas.
     * @param porcentageCompletadas porcentaje de tareas completadas
     */
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
                ", tareasAtrasadas=" + tareasAtrasadas +
                ", porcentageCompletadas=" + porcentageCompletadas +
                '}';
    }
}