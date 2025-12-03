package org.example.model;

/**
 * Clase que representa un cultivo asociado a una solicitud de asesoría.
 * Vincula cultivos específicos con solicitudes y sus cantidades.
 */
public class CultivoPorSolicitud {
    int idSolicitud;
    int idCultivo;
    String nombreCultivo;

    /**
     * Constructor vacío para crear un cultivo por solicitud sin datos.
     */
    public CultivoPorSolicitud() {
    }

    /**
     * Obtiene el ID de la solicitud asociada.
     * @return el ID de la solicitud
     */
    public int getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Establece el ID de la solicitud.
     * @param idSolicitud el ID de la solicitud
     */
    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
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
     * @return el nombre del cultivo
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

    /**
     * Convierte el objeto a una representación de texto.
     * @return cadena con la información del cultivo por solicitud
     */
    @Override
    public String toString() {
        return "CultivoPorSolicitud{" +
                "idSolicitud=" + idSolicitud +
                ", idCultivo=" + idCultivo +
                ", nombreCultivo='" + nombreCultivo + '\'' +
                '}';
    }
}
