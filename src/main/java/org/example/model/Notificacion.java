package org.example.model;

/**
 * Clase que representa una notificación del sistema.
 * Las notificaciones informan a los usuarios sobre cambios de estado.
 */
public class Notificacion {

    /**
     * Identificador único de la notificación dentro del sistema.
     */
    int idNotificacion;

    /**
     * Tipo de notificación (ejemplo: alerta, aviso, confirmación).
     */
    String tipoNotificacion;

    /**
     * Nombre del estado asociado a la notificación.
     * Indica el estado del proceso o entidad que generó la notificación.
     */
    String nombreEstado;

    /**
     * Mensaje adicional de la notificación.
     * Campo que proporciona información extra sobre la notificación.
     */
    String mensajeAdicional;

    /**
     * Constructor vacío para crear una notificación sin datos.
     */
    public Notificacion() {
    }

    /**
     * Obtiene el ID de la notificación.
     * @return el ID de la notificación
     */
    public int getIdNotificacion() {
        return idNotificacion;
    }
    /**
     * Establece el ID de la notificación.
     * @param idNotificacion el identificador único de la notificación
     */
    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    /**
     * Obtiene el tipo de notificación.
     * @return el tipo de notificación
     */
    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    /**
     * Establece el tipo de notificación.
     * @param tipoNotificacion el tipo de notificación (ejemplo: alerta, aviso, confirmación)
     */
    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    /**
     * Obtiene el nombre del estado asociado.
     * @return el nombre del estado
     */
    public String getNombreEstado() {
        return nombreEstado;
    }

    /**
     * Establece el nombre del estado asociado a la notificación.
     * @param nombreEstado el nombre del estado que se desea asignar
     */
    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    /**
     * Obtiene el mensaje adicional de la notificación.
     * @return el mensaje adicional
     */
    public String getMensajeAdicional() {
        return mensajeAdicional;
    }

    /**
     * Establece el mensaje adicional de la notificación.
     * @param mensajeAdicional el mensaje adicional
     */
    public void setMensajeAdicional(String mensajeAdicional) {
        this.mensajeAdicional = mensajeAdicional;
    }

    /**
     * Convierte la notificación a una representación de texto.
     * @return cadena con la información de la notificación
     */
    @Override
    public String toString() {
        return "Notificacion{" +
                "idNotificacion=" + idNotificacion +
                ", tipoNotificacion='" + tipoNotificacion + '\'' +
                ", nombreEstado='" + nombreEstado + '\'' +
                ", mensajeAdicional='" + mensajeAdicional + '\'' +
                '}';
    }
}