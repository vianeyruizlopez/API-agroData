package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SolicitudTaller {
    private int idSolicitudTaller;
    private int idAgricultor;
    private int idTaller;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaSolicitud;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAplicarTaller;

    private String direccion;
    private String comentario;
    private int idEstado;
    private String estadoPagoImagen; // ← cambiado de Blob a String

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    public int getIdSolicitudTaller() {
        return idSolicitudTaller;
    }

    public void setIdSolicitudTaller(int idSolicitudTaller) {
        this.idSolicitudTaller = idSolicitudTaller;
    }

    public int getIdAgricultor() {
        return idAgricultor;
    }

    public void setIdAgricultor(int idAgricultor) {
        this.idAgricultor = idAgricultor;
    }

    public int getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(int idTaller) {
        this.idTaller = idTaller;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDate getFechaAplicarTaller() {
        return fechaAplicarTaller;
    }

    public void setFechaAplicarTaller(LocalDate fechaAplicarTaller) {
        this.fechaAplicarTaller = fechaAplicarTaller;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstadoPagoImagen() {
        return estadoPagoImagen;
    }

    public void setEstadoPagoImagen(String estadoPagoImagen) {
        this.estadoPagoImagen = estadoPagoImagen;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}