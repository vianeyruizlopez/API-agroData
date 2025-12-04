package org.example.model;

/**
 * Clase que representa un taller agrícola disponible.
 * Los talleres tienen costo, descripción y estado.
 */
public class Taller {
    /** Identificador único del taller. */
    private int idTaller;

    /** Estado actual del taller (ej. próximo, en curso, completado). */
    private int idEstado;

    /** Nombre del taller agrícola. */
    private String nombreTaller;

    /** Costo del taller expresado en pesos. */
    private float costo;

    /** Descripción detallada del taller. */
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

    /**
     * Establece el ID del taller.
     * @param idTaller identificador único del taller
     */
    public void setIdTaller(int idTaller) {
        this.idTaller = idTaller;
    }

    /**
     * Obtiene el estado actual del taller.
     * @return el estado del taller
     */
    public int getIdEstado() {
        return idEstado;
    }

    /**
     * Establece el estado actual del taller.
     * @param idEstado estado del taller
     */
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * Obtiene el nombre del taller.
     * @return el nombre del taller
     */
    public String getNombreTaller() {
        return nombreTaller;
    }

    /**
     * Establece el nombre del taller.
     * @param nombreTaller nombre del taller
     */
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

    /**
     * Obtiene la descripción del taller.
     * @return descripción del taller
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del taller.
     * @param descripcion descripción del taller
     */
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