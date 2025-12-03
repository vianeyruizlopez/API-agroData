package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase que representa un plan de cultivo generado por un agrónomo.
 * Contiene toda la información del plan incluyendo fechas, cultivos y progreso.
 */
public class PlanCultivo {
    private int idPlan;
    private int idSolicitud;
    private int idUsuario;
    private int idEstado;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String direccionTerreno;
    private String motivoAsesoria;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    private float superficieTotal;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    private String observaciones;
    private int totalTareas;
    private int totalTareasCompletas;
    private float porcentajeAvance;

    private List<CultivoPorSolicitud> cultivoPorSolicitud;
    private List<ReportePlaga> reportePlagas;

    /**
     * Constructor vacío para crear un plan de cultivo sin datos.
     */
    public PlanCultivo() {}

    /**
     * Obtiene el ID del plan de cultivo.
     * @return el ID del plan
     */
    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDireccionTerreno() {
        return direccionTerreno;
    }

    public void setDireccionTerreno(String direccionTerreno) {
        this.direccionTerreno = direccionTerreno;
    }

    public String getMotivoAsesoria() {
        return motivoAsesoria;
    }

    public void setMotivoAsesoria(String motivoAsesoria) {
        this.motivoAsesoria = motivoAsesoria;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la superficie total del terreno en hectáreas.
     * @return la superficie total
     */
    public float getSuperficieTotal() {
        return superficieTotal;
    }

    public void setSuperficieTotal(float superficieTotal) {
        this.superficieTotal = superficieTotal;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
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

    public int getTotalTareasCompletas() {
        return totalTareasCompletas;
    }

    public void setTotalTareasCompletas(int totalTareasCompletas) {
        this.totalTareasCompletas = totalTareasCompletas;
    }

    /**
     * Obtiene el porcentaje de avance del plan.
     * @return el porcentaje de avance (0-100)
     */
    public float getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(float porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    /**
     * Obtiene la lista de cultivos del plan.
     * @return lista de cultivos asociados al plan
     */
    public List<CultivoPorSolicitud> getCultivoPorSolicitud() {
        return cultivoPorSolicitud;
    }

    public void setCultivoPorSolicitud(List<CultivoPorSolicitud> cultivoPorSolicitud) {
        this.cultivoPorSolicitud = cultivoPorSolicitud;
    }

    /**
     * Obtiene la lista de reportes de plaga del plan.
     * @return lista de reportes de plaga
     */
    public List<ReportePlaga> getReportePlagas() {
        return reportePlagas;
    }

    public void setReportePlagas(List<ReportePlaga> reportePlagas) {
        this.reportePlagas = reportePlagas;
    }

    /**
     * Convierte el plan de cultivo a una representación de texto.
     * @return cadena con toda la información del plan
     */
    @Override
    public String toString() {
        return "PlanCultivo{" +
                "idPlan=" + idPlan +
                ", idSolicitud=" + idSolicitud +
                ", idUsuario=" + idUsuario +
                ", idEstado=" + idEstado +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", direccionTerreno='" + direccionTerreno + '\'' +
                ", motivoAsesoria='" + motivoAsesoria + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", superficieTotal=" + superficieTotal +
                ", fechaFin=" + fechaFin +
                ", observaciones='" + observaciones + '\'' +
                ", totalTareas=" + totalTareas +
                ", totalTareasCompletas=" + totalTareasCompletas +
                ", porcentajeAvance=" + porcentajeAvance +
                ", cultivoPorSolicitud=" + cultivoPorSolicitud +
                ", reportePlagas=" + reportePlagas +
                '}';
    }
}

