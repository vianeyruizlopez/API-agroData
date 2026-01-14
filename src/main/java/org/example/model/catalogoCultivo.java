package org.example.model;

/**
 * Clase que representa un cultivo del catálogo.
 * Contiene la información básica de los tipos de cultivos disponibles.
 */
public class catalogoCultivo {

    /**
     * Identificador único del cultivo dentro del catálogo.
     */
    private int idCultivo;

    /**
     * Nombre del cultivo (ejemplo: Chayote, Papa, Calabaza).
     */
    private String nombreCultivo;

    /**
     * Constructor vacío para crear un cultivo sin datos.
     */
    public catalogoCultivo() {

    }

    /**
     * Obtiene el ID del cultivo.
     * @return el ID del cultivo
     */
    public int getIdCultivo() {
        return idCultivo;
    }

    /**
     * Establece el ID del cultivo.
     * @param idCultivo el ID del cultivo
     */
    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    /**
     * Obtiene el nombre del cultivo.
     * @return el nombre del cultivo (ej: Chayote, Papa, Calabaza)
     */
    public String getNombreCultivo() {
        return nombreCultivo;
    }

    /**
     * Establece el nombre del cultivo.
     * @param nombreCultivo el nombre del cultivo
     */
    public void setNombreCultivo(String nombreCultivo) {
        this.nombreCultivo = nombreCultivo;
    }
}