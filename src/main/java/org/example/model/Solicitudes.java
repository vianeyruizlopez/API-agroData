package org.example.model;

import java.util.List;

public class Solicitudes {
    List<SolicitudAsesoria> solicitudAsesorias;
    List<SolicitudTaller> solicitudTalleres;

    public Solicitudes() {
    }

    public List<SolicitudAsesoria> getSolicitudAsesorias() {
        return solicitudAsesorias;
    }

    public void setSolicitudAsesorias(List<SolicitudAsesoria> solicitudAsesorias) {
        this.solicitudAsesorias = solicitudAsesorias;
    }

    public List<SolicitudTaller> getSolicitudTalleres() {
        return solicitudTalleres;
    }

    public void setSolicitudTalleres(List<SolicitudTaller> solicitudTalleres) {
        this.solicitudTalleres = solicitudTalleres;
    }
}
