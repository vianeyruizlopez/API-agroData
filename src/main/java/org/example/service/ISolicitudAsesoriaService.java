package org.example.service;

import org.example.model.SolicitudAsesoria;

import java.util.List;

public interface ISolicitudAsesoriaService {
    SolicitudAsesoria obtenerSolicitudAsesoriaPorId(int id);
    List<SolicitudAsesoria> obtenerTodasLasSolicitudes();
    void actualizarEstadoSolicitudAsesoria(int id, int nuevoEstado);
    void eliminarSolicitudAsesoria(int id);
    void agregarSolicitudAsesoria(SolicitudAsesoria solicitud);
}
