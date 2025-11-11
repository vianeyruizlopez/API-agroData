package org.example.model;

public class Estado {
    private int idEstado;
    private String nombreEstado;

    public Estado() {
    }
    public Estado(int idEstado, String nombreEstado) {
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombre(String nombre) {
        this.nombreEstado = nombre;
    }

    @Override
    public String toString() {
        return "Estado{idEstado=" + idEstado + ", nombreEstado='" + nombreEstado + "'}";
    }
}
