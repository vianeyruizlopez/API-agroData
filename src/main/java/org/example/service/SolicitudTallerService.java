package org.example.service;
import org.example.model.SolicitudTaller;
import org.example.repository.SolicitudAsesoriaRepository;
import org.example.repository.SolicitudTallerRepository;

import java.time.LocalDate;
import java.util.List;

import java.time.LocalDateTime;

import java.util.Map;
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

    // método para actualizar la imagen del pago
    public void subirComprobante(int id, String imagenBase64) {
        // Estado 4 = En Revisión
        solicitudTallerRepository.actualizarImagenPago(id, imagenBase64, 4);
    }

    public void eliminarSolicitudTaller(int id) {
        solicitudTallerRepository.eliminar(id);
    }

    public void agregarSolicitudTaller(SolicitudTaller solicitud) {
        // Lógica de negocio: Duración de 1 semana
        if (solicitud.getFechaAplicarTaller() != null) {
            LocalDate fechaInicio = solicitud.getFechaAplicarTaller();
            LocalDate fechaFin = fechaInicio.plusDays(7); // Sumar 7 días
            solicitud.setFechaFin(fechaFin);
        }

        solicitudTallerRepository.agregar(solicitud);
    }

    public List<SolicitudTaller> obtenerTalleresPorStatus(int idEstado) {
        return solicitudTallerRepository.obtenerTalleresPorStatus(idEstado);
    }

    public org.example.model.Solicitudes obtenerSolicitudesTallerAsesoria() {
        org.example.model.Solicitudes solicitudes = new org.example.model.Solicitudes();
        solicitudes.setSolicitudTalleres(solicitudTallerRepository.obtenerTodas());
        solicitudes.setSolicitudAsesorias(solicitudAsesoriaRepository.obtenerTodas());
        return solicitudes;
    }

    public List<SolicitudTaller> obtenerSolicitudesPorUsuario(int userId) {
        return solicitudTallerRepository.obtenerSolicitudesPorUsuario(userId);
    }

    public List<Map<String, Object>> obtenerEstadisticasTalleres() {
        LocalDateTime fin = LocalDateTime.now();
        LocalDateTime inicio = fin.minusMonths(1);

        return solicitudTallerRepository.obtenerEstadisticas(inicio, fin);
    }
}