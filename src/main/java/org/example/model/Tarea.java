// example/model/Tarea.java
package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

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

    // --- ★ ESTE CAMPO ES EL IMPORTANTE ★ ---
    private int idReportePlaga;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCompletado;

    // --- Getters y Setters ---

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

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

    // --- ★ GETTER Y SETTER PARA EL CAMPO IMPORTANTE ★ ---
    public int getIdReportePlaga() {
        return idReportePlaga;
    }

    public void setIdReportePlaga(int idReportePlaga) {
        this.idReportePlaga = idReportePlaga;
    }
}