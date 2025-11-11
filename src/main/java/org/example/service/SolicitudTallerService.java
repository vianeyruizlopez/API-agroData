package org.example.service;
import org.example.model.SolicitudTaller;
import org.example.model.Solicitudes;
import org.example.repository.SolicitudAsesoriaRepository;
import org.example.repository.SolicitudTallerRepository;

import java.util.ArrayList;
import java.util.List;

public class SolicitudTallerService {
    private SolicitudTallerRepository solicitudTallerRepository = new SolicitudTallerRepository();
    private SolicitudAsesoriaRepository solicitudAsesoriaRepository = new SolicitudAsesoriaRepository();

    public SolicitudTaller obtenerSolicitudTallerPorId(int id) {
        return solicitudTallerRepository.obtenerPorId(id);
    }
    public List<SolicitudTaller> obtenerTodasLasSolicitudes() {
        return solicitudTallerRepository.obtenerTodas();
    }
    public void actualizarEstadoSolicitudTaller(int id, int nuevoEstado) {
        solicitudTallerRepository.actualizarEstado(id, nuevoEstado);
    }
    public void eliminarSolicitudTaller(int id) {
        solicitudTallerRepository.eliminar(id);
    }
    public void agregarSolicitudTaller(SolicitudTaller solicitud) {
        solicitudTallerRepository.agregar(solicitud);
    }

    public List<SolicitudTaller> obtenerTalleresPorStatus(int idEstado) {
        return solicitudTallerRepository.obtenerTalleresPorStatus(idEstado);
    }

    public Solicitudes obtenerSolicitudesTallerAsesoria() {
        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setSolicitudTalleres(solicitudTallerRepository.obtenerTodas());
        solicitudes.setSolicitudAsesorias(solicitudAsesoriaRepository.obtenerTodas());
        return solicitudes;
    }
    public List<SolicitudTaller> obtenerSolicitudesPorUsuario(int userId) {
        // Delega la ejecución de la consulta filtrada a la capa de Repositorio
        return solicitudTallerRepository.obtenerSolicitudesPorUsuario(userId);
    }
}
