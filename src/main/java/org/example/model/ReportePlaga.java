package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * Clase que representa un reporte de plaga en los cultivos.
 * Los agricultores reportan plagas con imágenes y descripciones.
 */
public class ReportePlaga {

    /**
     * Identificador único del reporte de plaga.
     */
    private int idReportePlaga;

    /**
     * Identificador del plan de cultivo asociado al reporte.
     */
    private int idPlan;

    /**
     * Fecha y hora en que se generó el reporte de plaga.
     * Se serializa en formato ISO con patrón yyyy-MM-dd'T'HH:mm:ss.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaReporte;

    /**
     * Tipo de plaga reportada (ejemplo: gusano, hongo, insecto).
     */
    private String tipoPlaga;

    /**
     * Descripción detallada de la plaga y su impacto.
     */
    private String descripcion;

    /**
     * Imagen asociada al reporte (formato base64 o URL).
     */
    private String imagen;

    /**
     * Identificador del estado del reporte (ejemplo: pendiente, revisado).
     */
    private int idEstado;

    /**
     * Identificador de la tarea vinculada al reporte de plaga.
     */
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

    /**
     * Establece el ID del reporte de plaga.
     * @param idReportePlaga identificador único del reporte
     */
    public void setIdReportePlaga(int idReportePlaga) {
        this.idReportePlaga = idReportePlaga;
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
     * @param idPlan identificador del plan de cultivo
     */
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

    /**
     * Establece la fecha del reporte de plaga.
     * @param fechaReporte fecha y hora del reporte
     */
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

    /**
     * Establece el tipo de plaga reportada.
     * @param tipoPlaga tipo de plaga (ejemplo: gusano, hongo)
     */
    public void setTipoPlaga(String tipoPlaga) {
        this.tipoPlaga = tipoPlaga;
    }

    /**
     * Obtiene la descripción del reporte de plaga.
     * @return la descripción de la plaga
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del reporte de plaga.
     * @param descripcion detalles de la plaga
     */
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

    /**
     * Establece la imagen de la plaga.
     * @param imagen la imagen en formato base64 o URL
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el ID del estado del reporte.
     * @return el ID del estado
     */
    public int getIdEstado() {
        return idEstado;
    }

    /**
     * Establece el ID del estado del reporte.
     * @param idEstado identificador del estado
     */
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * Obtiene el ID de la tarea vinculada.
     * @return el ID de la tarea
     */
    public int getIdTarea() {
        return idTarea;
    }

    /**
     * Establece el ID de la tarea vinculada.
     * @param idTarea identificador de la tarea
     */
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