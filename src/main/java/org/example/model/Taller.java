package org.example.model;

/**
 * Clase que representa un taller agrícola disponible.
 * Los talleres tienen costo, descripción y estado.
 */
public class Taller {
    private int idTaller;
    private int idEstado;
    private String nombreTaller;
    private float costo;
    private String descripcion;

    /**
     * Constructor vacío para crear un taller sin datos.
     */
    public Taller() {}

    /**
     * Constructor que crea un taller con todos los datos.
     * @param idTaller el ID del taller
     * @param idEstado el estado del taller
     * @param nombreTaller el nombre del taller
     * @param costo el costo del taller
     * @param descripcion la descripción del taller
     */
    public Taller(int idTaller, int idEstado, String nombreTaller, float costo, String descripcion) {
        this.idTaller = idTaller;
        this.idEstado = idEstado;
        this.nombreTaller = nombreTaller;
        this.costo = costo;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el ID del taller.
     * @return el ID del taller
     */
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

    /**
     * Obtiene el costo del taller.
     * @return el costo en pesos
     */
    public float getCosto() {
        return costo;
    }

    /**
     * Establece el costo del taller.
     * @param costo el costo en pesos (debe ser positivo)
     * @throws IllegalArgumentException si el costo es negativo
     */
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

    /**
     * Obtiene el nombre visual del estado del taller.
     * @return el estado en formato legible
     */
    public String getNombreEstadoVisual() {
        return switch (idEstado) {
            case 1, 5 -> "Próximo";
            case 4 -> "Completado";
            default -> "En curso";
        };
    }

    /**
     * Convierte el taller a una representación de texto.
     * @return cadena con la información del taller
     */
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