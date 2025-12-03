package org.example.model;

/**
 * Clase que representa un estado del sistema.
 * Los estados se usan para solicitudes, tareas y talleres.
 */
public class Estado {
    private int idEstado;
    private String nombreEstado;

    /**
     * Constructor vacío para crear un estado sin datos.
     */
    public Estado() {
    }
    /**
     * Constructor que crea un estado con ID y nombre.
     * @param idEstado el ID del estado
     * @param nombreEstado el nombre del estado
     */
    public Estado(int idEstado, String nombreEstado) {
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
    }

    /**
     * Obtiene el ID del estado.
     * @return el ID del estado
     */
    public int getIdEstado() {
        return idEstado;
    }

    /**
     * Establece el ID del estado.
     * @param idEstado el ID del estado
     */
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * Obtiene el nombre del estado.
     * @return el nombre del estado (pendiente, aceptada, rechazada, etc.)
     */
    public String getNombreEstado() {
        return nombreEstado;
    }

    /**
     * Establece el nombre del estado.
     * @param nombre el nombre del estado
     */
    public void setNombre(String nombre) {
        this.nombreEstado = nombre;
    }

    /**
     * Convierte el estado a una representación de texto.
     * @return una cadena con el ID y nombre del estado
     */
    @Override
    public String toString() {
        return "Estado{idEstado=" + idEstado + ", nombreEstado='" + nombreEstado + "'}";
    }
}
