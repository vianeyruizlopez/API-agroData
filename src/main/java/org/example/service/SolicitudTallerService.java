package org.example.service;
import org.example.model.SolicitudTaller;
import org.example.repository.SolicitudAsesoriaRepository;
import org.example.repository.SolicitudTallerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SolicitudTallerService implements ISolicitudTallerService{
    private final SolicitudTallerRepository solicitudTallerRepository = new SolicitudTallerRepository();
    private final SolicitudAsesoriaRepository solicitudAsesoriaRepository = new SolicitudAsesoriaRepository();

    @Override
    public SolicitudTaller obtenerSolicitudTallerPorId(int id) {
        return solicitudTallerRepository.obtenerPorId(id);
    }

    @Override
    public List<SolicitudTaller> obtenerTodasLasSolicitudes() {
        return solicitudTallerRepository.obtenerTodas();
    }

    @Override
    public void actualizarEstadoSolicitudTaller(int id, int nuevoEstado) {
        solicitudTallerRepository.actualizarEstado(id, nuevoEstado);
    }

    @Override
    public void subirComprobante(int id, String imagenBase64) {

        solicitudTallerRepository.actualizarImagenPago(id, imagenBase64, 4);
    }

    @Override
    public void eliminarSolicitudTaller(int id) {
        solicitudTallerRepository.eliminar(id);
    }

    @Override
    public void agregarSolicitudTaller(SolicitudTaller solicitud) {

        if (solicitud.getFechaAplicarTaller() != null) {
            LocalDate fechaInicio = solicitud.getFechaAplicarTaller();
            LocalDate fechaFin = fechaInicio.plusDays(7); // Sumar 7 días
            solicitud.setFechaFin(fechaFin);
        }

        solicitudTallerRepository.agregar(solicitud);
    }

    @Override
    public List<SolicitudTaller> obtenerTalleresPorStatus(int idEstado) {
        return solicitudTallerRepository.obtenerTalleresPorStatus(idEstado);
    }

    @Override
    public org.example.model.Solicitudes obtenerSolicitudesTallerAsesoria() {
        org.example.model.Solicitudes solicitudes = new org.example.model.Solicitudes();
        solicitudes.setSolicitudTalleres(solicitudTallerRepository.obtenerTodas());
        solicitudes.setSolicitudAsesorias(solicitudAsesoriaRepository.obtenerTodas());
        return solicitudes;
    }

    @Override
    public List<SolicitudTaller> obtenerSolicitudesPorUsuario(int userId) {
        return solicitudTallerRepository.obtenerSolicitudesPorUsuario(userId);
    }

    @Override
    public List<Map<String, Object>> obtenerEstadisticasTalleres() {
        LocalDateTime fin = LocalDateTime.now();
        LocalDateTime inicio = fin.minusMonths(1);

        return solicitudTallerRepository.obtenerEstadisticas(inicio, fin);
    }
}