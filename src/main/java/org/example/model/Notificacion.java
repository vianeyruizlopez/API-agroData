package org.example.model;

import java.util.Date;

public class Notificacion {
    int idNotificacion;
    String tipoNotificacion;
    String nombreEstado;

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

    @Override
    public String toString() {
        return "Notificacion{" +
                "idNotificacion=" + idNotificacion +
                ", tipoNotificacion='" + tipoNotificacion + '\'' +
                ", nombreEstado='" + nombreEstado + '\'' +
                '}';
    }
}
