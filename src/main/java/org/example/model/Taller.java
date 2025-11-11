package org.example.model;

public class Taller {
    private int idTaller;
    private int idEstado;
    private String nombreTaller;
    private float costo;
    private String descripcion;

    public Taller() {}

    public Taller(int idTaller, int idEstado, String nombreTaller, float costo, String descripcion) {
        this.idTaller = idTaller;
        this.idEstado = idEstado;
        this.nombreTaller = nombreTaller;
        this.costo = costo;
        this.descripcion = descripcion;
    }

    public int getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(int idTaller) {
        this.idTaller = idTaller;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreTaller() {
        return nombreTaller;
    }

    public void setNombreTaller(String nombreTaller) {
        this.nombreTaller = nombreTaller;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        if (costo < 0) throw new IllegalArgumentException("El costo no puede ser negativo");
        this.costo = costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreEstadoVisual() {
        return switch (idEstado) {
            case 1, 5 -> "Próximo";
            case 4 -> "Completado";
            default -> "En curso";
        };
    }

    @Override
    public String toString() {
        return "Taller{idTaller=" + idTaller +
                ", nombreTaller='" + nombreTaller + '\'' +
                ", costo=" + costo +
                ", descripcion='" + descripcion + '\'' +
                ", idEstado=" + idEstado +
                ", nombreEstadoVisual='" + getNombreEstadoVisual() + '\'' +
                '}';
    }
}