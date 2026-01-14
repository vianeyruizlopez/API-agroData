package org.example.service;
import org.example.model.SolicitudTaller;
import org.example.repository.SolicitudAsesoriaRepository;
import org.example.repository.SolicitudTallerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Implementación del servicio para gestionar solicitudes de talleres.
 * Maneja la lógica de negocio relacionada con solicitudes de participación en talleres.
 */
public class SolicitudTallerService implements ISolicitudTallerService{
    private final SolicitudTallerRepository solicitudTallerRepository = new SolicitudTallerRepository();
    private final SolicitudAsesoriaRepository solicitudAsesoriaRepository = new SolicitudAsesoriaRepository();

    /**
     * Obtiene una solicitud de taller por su ID.
     * @param id ID de la solicitud
     * @return Solicitud de taller encontrada
     */
    @Override
    public SolicitudTaller obtenerSolicitudTallerPorId(int id) {
        return solicitudTallerRepository.obtenerPorId(id);
    }

    /**
     * Obtiene todas las solicitudes de taller disponibles.
     * @return Lista de solicitudes de taller
     */
    @Override
    public List<SolicitudTaller> obtenerTodasLasSolicitudes() {
        return solicitudTallerRepository.obtenerTodas();
    }

    /**
     * Actualiza el estado de una solicitud de taller.
     * @param id ID de la solicitud
     * @param nuevoEstado Nuevo estado de la solicitud
     */
    @Override
    public void actualizarEstadoSolicitudTaller(int id, int nuevoEstado) {
        solicitudTallerRepository.actualizarEstado(id, nuevoEstado);
    }

    /**
     * Sube el comprobante de pago para una solicitud de taller.
     * @param id ID de la solicitud
     * @param imagenBase64 Imagen del comprobante en formato base64
     */
    @Override
    public void subirComprobante(int id, String imagenBase64) {

        solicitudTallerRepository.actualizarImagenPago(id, imagenBase64, 4);
    }

    /**
     * Elimina una solicitud de taller de la base de datos.
     * @param id ID de la solicitud a eliminar
     */
    @Override
    public void eliminarSolicitudTaller(int id) {
        solicitudTallerRepository.eliminar(id);
    }

    /**
     * Agrega una nueva solicitud de taller con cálculo automático de fechas.
     * @param solicitud Solicitud de taller a agregar
     */
    @Override
    public void agregarSolicitudTaller(SolicitudTaller solicitud) {

        if (solicitud.getFechaAplicarTaller() != null) {
            LocalDate fechaInicio = solicitud.getFechaAplicarTaller();
            LocalDate fechaFin = fechaInicio.plusDays(7); // Sumar 7 días
            solicitud.setFechaFin(fechaFin);
        }

        solicitudTallerRepository.agregar(solicitud);
    }

    /**
     * Obtiene solicitudes de taller filtradas por estado.
     * @param idEstado ID del estado a filtrar
     * @return Lista de solicitudes con el estado especificado
     */
    @Override
    public List<SolicitudTaller> obtenerTalleresPorStatus(int idEstado) {
        return solicitudTallerRepository.obtenerTalleresPorStatus(idEstado);
    }

    /**
     * Obtiene un resumen de solicitudes de taller y asesoría.
     * @return Objeto con ambos tipos de solicitudes
     */
    @Override
    public org.example.model.Solicitudes obtenerSolicitudesTallerAsesoria() {
        org.example.model.Solicitudes solicitudes = new org.example.model.Solicitudes();
        solicitudes.setSolicitudTalleres(solicitudTallerRepository.obtenerTodas());
        solicitudes.setSolicitudAsesorias(solicitudAsesoriaRepository.obtenerTodas());
        return solicitudes;
    }

    /**
     * Obtiene las solicitudes de taller de un usuario específico.
     * @param userId ID del usuario agricultor
     * @return Lista de solicitudes del usuario
     */
    @Override
    public List<SolicitudTaller> obtenerSolicitudesPorUsuario(int userId) {
        return solicitudTallerRepository.obtenerSolicitudesPorUsuario(userId);
    }

    /**
     * Obtiene estadísticas de talleres del último mes.
     * @return Lista de mapas con estadísticas por taller
     */
    @Override
    public List<Map<String, Object>> obtenerEstadisticasTalleres() {
        LocalDateTime fin = LocalDateTime.now();
        LocalDateTime inicio = fin.minusMonths(1);

        return solicitudTallerRepository.obtenerEstadisticas(inicio, fin);
    }
}