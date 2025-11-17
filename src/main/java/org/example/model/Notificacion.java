package org.example.model;

public class Notificacion {
    int idNotificacion;
    String tipoNotificacion;
    String nombreEstado;

    // --- ★ CAMPO NUEVO QUE FALTABA ★ ---
    String mensajeAdicional;
    // ----------------------------------

    public Notificacion() {
    }

    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    // --- ★ SETTER QUE FALTABA (Para corregir el error) ★ ---
    public String getMensajeAdicional() {
        return mensajeAdicional;
    }

    public void setMensajeAdicional(String mensajeAdicional) {
        this.mensajeAdicional = mensajeAdicional;
    }
    // -------------------------------------------------------

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