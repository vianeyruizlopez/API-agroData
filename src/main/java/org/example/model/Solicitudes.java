package org.example.model;

import java.util.List;

/**
 * Clase que agrupa diferentes tipos de solicitudes.
 * Contiene listas de solicitudes de asesoría y talleres.
 */
public class Solicitudes {
    List<SolicitudAsesoria> solicitudAsesorias;
    List<SolicitudTaller> solicitudTalleres;

    /**
     * Constructor vacío para crear un contenedor de solicitudes.
     */
    public Solicitudes() {
    }

    /**
     * Obtiene la lista de solicitudes de asesoría.
     * @return lista de solicitudes de asesoría
     */
    public List<SolicitudAsesoria> getSolicitudAsesorias() {
        return solicitudAsesorias;
    }

    /**
     * Establece la lista de solicitudes de asesoría.
     * @param solicitudAsesorias lista de solicitudes de asesoría
     */
    public void setSolicitudAsesorias(List<SolicitudAsesoria> solicitudAsesorias) {
        this.solicitudAsesorias = solicitudAsesorias;
    }

    /**
     * Obtiene la lista de solicitudes de taller.
     * @return lista de solicitudes de taller
     */
    public List<SolicitudTaller> getSolicitudTalleres() {
        return solicitudTalleres;
    }

    /**
     * Establece la lista de solicitudes de taller.
     * @param solicitudTalleres lista de solicitudes de taller
     */
    public void setSolicitudTalleres(List<SolicitudTaller> solicitudTalleres) {
        this.solicitudTalleres = solicitudTalleres;
    }
}
