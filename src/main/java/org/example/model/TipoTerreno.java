package org.example.model;

/**
 * Clase que representa un tipo de riego para terrenos.
 * Define los diferentes sistemas de riego disponibles.
 */
public class TipoTerreno {
    private int idRiego;
    private String nombreRiego;

    /**
     * Constructor vacío para crear un tipo de terreno sin datos.
     */
    public TipoTerreno() {
    }

    /**
     * Obtiene el ID del tipo de riego.
     * @return el ID del tipo de riego
     */
    public int getIdRiego() {
        return idRiego;
    }

    /**
     * Establece el ID del tipo de riego.
     * @param idRiego el ID del tipo de riego
     */
    public void setIdRiego(int idRiego) {
        this.idRiego = idRiego;
    }

    /**
     * Obtiene el nombre del tipo de riego.
     * @return el nombre del riego (Goteo, Aspersión, Temporal)
     */
    public String getNombreRiego() {
        return nombreRiego;
    }

    /**
     * Establece el nombre del tipo de riego.
     * @param nombreRiego el nombre del tipo de riego
     */
    public void setNombreRiego(String nombreRiego) {
        this.nombreRiego = nombreRiego;
    }
}
