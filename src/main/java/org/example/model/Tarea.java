package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

/**
 * Clase que representa una tarea agrícola asignada a un agricultor.
 * Las tareas tienen fechas de inicio, vencimiento y pueden tener reportes de plaga.
 */
public class Tarea {
    /** Identificador único de la tarea. */
    private int idTarea;

    /** Identificador del plan al que pertenece la tarea. */
    private int idPlan;

    /** Nombre descriptivo de la tarea agrícola. */
    private String nombreTarea;

    /** Identificador del usuario (agricultor) asignado a la tarea. */
    private int idUsuario;

    /** Fecha de inicio de la tarea. */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    /** Fecha límite o vencimiento de la tarea. */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento;

    /** Estado actual de la tarea (ej. pendiente, en curso, completada). */
    private int idEstado;

    /**
     * ID del reporte de plaga asociado a esta tarea.
     * Campo importante para vincular tareas con reportes de plaga.
     */
    private int idReportePlaga;

    /** Fecha en que la tarea fue marcada como completada. */
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

    /**
     * Obtiene el ID del plan asociado.
     * @return el ID del plan
     */
    public int getIdPlan() {
        return idPlan;
    }

    /**
     * Establece el ID del plan asociado.
     * @param idPlan identificador del plan
     */
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

    /**
     * Obtiene el ID del usuario asignado.
     * @return el ID del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el ID del usuario asignado.
     * @param idUsuario identificador del usuario
     */
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

    /**
     * Establece la fecha de inicio de la tarea.
     * @param fechaInicio fecha de inicio
     */
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

    /**
     * Establece la fecha de vencimiento de la tarea.
     * @param fechaVencimiento fecha límite
     */
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Obtiene el estado actual de la tarea.
     * @return el estado de la tarea
     */
    public int getIdEstado() {
        return idEstado;
    }

    /**
     * Establece el estado actual de la tarea.
     * @param idEstado estado de la tarea
     */
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * Obtiene la fecha en que la tarea fue completada.
     * @return la fecha de completado
     */
    public LocalDate getFechaCompletado() {
        return fechaCompletado;
    }

    /**
     * Establece la fecha en que la tarea fue completada.
     * @param fechaCompletado fecha de completado
     */
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