package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ReportePlaga {
    private int idReportePlaga;
    private int idPlan;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaReporte;

    private String tipoPlaga;
    private String descripcion;
    private String imagen;
    private int idEstado;
    private int idTarea;

    public ReportePlaga() {}

    public int getIdReportePlaga() {
        return idReportePlaga;
    }

    public void setIdReportePlaga(int idReportePlaga) {
        this.idReportePlaga = idReportePlaga;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public String getTipoPlaga() {
        return tipoPlaga;
    }

    public void setTipoPlaga(String tipoPlaga) {
        this.tipoPlaga = tipoPlaga;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    @Override
    public String toString() {
        return "ReportePlaga{" +
                "idReportePlaga=" + idReportePlaga +
                ", idPlan=" + idPlan +
                ", fechaReporte=" + fechaReporte +
                ", tipoPlaga='" + tipoPlaga + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", idEstado=" + idEstado +
                ", idTarea=" + idTarea +
                '}';
    }
}