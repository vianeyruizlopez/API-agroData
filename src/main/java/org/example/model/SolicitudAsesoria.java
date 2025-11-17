package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public class SolicitudAsesoria {
    private int idSolicitud;
    private int idAgricultor;
    private String nombreAgricultor;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaSolicitud;

    private boolean usoMaquinaria;
    private String nombreMaquinaria;
    private int tipoRiego;
    private String nombreRiego;
    private boolean tienePlaga;
    private String descripcionPlaga;
    private float superficieTotal;
    private String direccionTerreno;
    private String motivoAsesoria;
    private int idEstado;
    private List<CultivoPorSolicitud> cultivos;

    public String getNombreAgricultor() { return nombreAgricultor; }

    public void setNombreAgricultor(String nombreAgricultor) { this.nombreAgricultor = nombreAgricultor; }

    public SolicitudAsesoria() {}

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getIdAgricultor() {
        return idAgricultor;
    }

    public void setIdAgricultor(int idAgricultor) {
        this.idAgricultor = idAgricultor;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public boolean isUsoMaquinaria() {
        return usoMaquinaria;
    }

    public void setUsoMaquinaria(boolean usoMaquinaria) {
        this.usoMaquinaria = usoMaquinaria;
    }

    public String getNombreMaquinaria() {
        return nombreMaquinaria;
    }

    public void setNombreMaquinaria(String nombreMaquinaria) {
        this.nombreMaquinaria = nombreMaquinaria;
    }

    public int getTipoRiego() {
        return tipoRiego;
    }

    public void setTipoRiego(int tipoRiego) {
        this.tipoRiego = tipoRiego;
    }

    public String getNombreRiego() {
        return nombreRiego;
    }

    public void setNombreRiego(String nombreRiego) {
        this.nombreRiego = nombreRiego;
    }

    public boolean isTienePlaga() {
        return tienePlaga;
    }

    public void setTienePlaga(boolean tienePlaga) {
        this.tienePlaga = tienePlaga;
    }

    public String getDescripcionPlaga() {
        return descripcionPlaga;
    }

    public void setDescripcionPlaga(String descripcionPlaga) {
        this.descripcionPlaga = descripcionPlaga;
    }

    public float getSuperficieTotal() {
        return superficieTotal;
    }

    public void setSuperficieTotal(float superficieTotal) {
        this.superficieTotal = superficieTotal;
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

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public List<CultivoPorSolicitud> getCultivos() {
        return cultivos;
    }

    public void setCultivos(List<CultivoPorSolicitud> cultivos) {
        this.cultivos = cultivos;
    }
}
