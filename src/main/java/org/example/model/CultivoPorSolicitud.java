package org.example.model;

public class CultivoPorSolicitud {
    int idSolicitud;
    int idCultivo;
    String nombreCultivo;

    public CultivoPorSolicitud() {
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    public String getNombreCultivo() {
        return nombreCultivo;
    }

    public void setNombreCultivo(String nombreCultivo) {
        this.nombreCultivo = nombreCultivo;
    }

    @Override
    public String toString() {
        return "CultivoPorSolicitud{" +
                "idSolicitud=" + idSolicitud +
                ", idCultivo=" + idCultivo +
                ", nombreCultivo='" + nombreCultivo + '\'' +
                '}';
    }
}
