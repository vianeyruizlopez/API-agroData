// example/model/Tarea.java
package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

/**
 * Clase que representa una tarea agrícola asignada a un agricultor.
 * Las tareas tienen fechas de inicio, vencimiento y pueden tener reportes de plaga.
 */
public class Tarea {
    private int idTarea;
    private int idPlan;
    private String nombreTarea;
    private int idUsuario;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento;

    private int idEstado;

    /**
     * ID del reporte de plaga asociado a esta tarea.
     * Campo importante para vincular tareas con reportes de plaga.
     */
    private int idReportePlaga;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCompletado;

    // --- Getters y Setters ---

    /**
     * Obtiene el ID de la tarea.
     * @return el ID de la tarea
     */
    public int getIdTarea() {
        return idTarea;
    }

    /**
     * Establece el ID de la tarea.
     * @param idTarea el ID de la tarea
     */
    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    /**
     * Obtiene el nombre de la tarea.
     * @return el nombre de la tarea
     */
    public String getNombreTarea() {
        return nombreTarea;
    }

    /**
     * Establece el nombre de la tarea.
     * @param nombreTarea el nombre de la tarea
     */
    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene la fecha de inicio de la tarea.
     * @return la fecha de inicio
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de vencimiento de la tarea.
     * @return la fecha límite para completar la tarea
     */
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public LocalDate getFechaCompletado() {
        return fechaCompletado;
    }

    public void setFechaCompletado(LocalDate fechaCompletado) {
        this.fechaCompletado = fechaCompletado;
    }

    /**
     * Obtiene el ID del reporte de plaga asociado.
     * @return el ID del reporte de plaga
     */
    public int getIdReportePlaga() {
        return idReportePlaga;
    }

    /**
     * Establece el ID del reporte de plaga asociado.
     * @param idReportePlaga el ID del reporte de plaga
     */
    public void setIdReportePlaga(int idReportePlaga) {
        this.idReportePlaga = idReportePlaga;
    }
}