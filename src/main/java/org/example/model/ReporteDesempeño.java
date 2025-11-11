package org.example.model;

import java.time.LocalDateTime;

public class ReporteDesempeño {
    int idReporte;
    int idPlan;
    LocalDateTime fechaGeneracion;
    String observaciones;
    int totalTareas;
    int tareasCompletadas;
    int tareasAceptadas;
    int tareasPendientes;
    float porcentageCompletadas;

    public ReporteDesempeño() {
    }

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

    public float getPorcentageCompletadas() {
        return porcentageCompletadas;
    }

    public void setPorcentageCompletadas(float porcentageCompletadas) {
        this.porcentageCompletadas = porcentageCompletadas;
    }

    @Override
    public String toString() {
        return "ReporteDesempeño{" +
                "idReporte=" + idReporte +
                ", idPlan=" + idPlan +
                ", fechaGeneracion=" + fechaGeneracion +
                ", observaciones='" + observaciones + '\'' +
                ", totalTareas=" + totalTareas +
                ", tareasCompletadas=" + tareasCompletadas +
                ", tareasAceptadas=" + tareasAceptadas +
                ", tareasPendientes=" + tareasPendientes +
                ", porcentageCompletadas=" + porcentageCompletadas +
                '}';
    }
}
