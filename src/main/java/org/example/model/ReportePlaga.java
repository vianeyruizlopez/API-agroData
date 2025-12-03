package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * Clase que representa un reporte de plaga en los cultivos.
 * Los agricultores reportan plagas con imágenes y descripciones.
 */
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

    /**
     * Constructor vacío para crear un reporte de plaga sin datos.
     */
    public ReportePlaga() {}

    /**
     * Obtiene el ID del reporte de plaga.
     * @return el ID del reporte
     */
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

    /**
     * Obtiene la fecha del reporte de plaga.
     * @return la fecha y hora del reporte
     */
    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    /**
     * Obtiene el tipo de plaga reportada.
     * @return el tipo de plaga
     */
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

    /**
     * Obtiene la imagen de la plaga.
     * @return la imagen en formato base64 o URL
     */
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

    /**
     * Convierte el reporte de plaga a texto.
     * @return cadena con la información del reporte
     */
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