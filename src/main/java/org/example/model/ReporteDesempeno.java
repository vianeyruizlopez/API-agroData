package org.example.model;

import java.time.LocalDateTime;

public class ReporteDesempeno {
    int idReporte;
    int idPlan;
    LocalDateTime fechaGeneracion;
    String observaciones;
    int totalTareas;
    int tareasCompletadas;
    int tareasAceptadas;
    int tareasPendientes;
    int tareasAtrasadas; // <-- ★ NUEVO CAMPO ★
    float porcentageCompletadas;

    public ReporteDesempeno() {
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

    // --- ★ GETTER Y SETTER PARA 'tareasAtrasadas' ★ ---
    public int getTareasAtrasadas() {
        return tareasAtrasadas;
    }

    public void setTareasAtrasadas(int tareasAtrasadas) {
        this.tareasAtrasadas = tareasAtrasadas;
    }
    // --- ★ FIN DE GETTER Y SETTER ★ ---

    public float getPorcentageCompletadas() {
        return porcentageCompletadas;
    }

    public void setPorcentageCompletadas(float porcentageCompletadas) {
        this.porcentageCompletadas = porcentageCompletadas;
    }

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