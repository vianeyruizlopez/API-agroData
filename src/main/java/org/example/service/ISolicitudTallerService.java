package org.example.service;

import org.example.model.SolicitudTaller;

import java.util.List;
import java.util.Map;

/**
 * Interfaz del servicio para gestionar solicitudes de talleres.
 * Define las operaciones de negocio relacionadas con solicitudes de participación en talleres.
 */
public interface ISolicitudTallerService {
    /**
     * Obtiene una solicitud de taller por ID.
     * @param id ID de la solicitud
     * @return Solicitud de taller
     */
    SolicitudTaller obtenerSolicitudTallerPorId(int id);
    /**
     * Obtiene todas las solicitudes de taller.
     * @return Lista de solicitudes
     */
    List<SolicitudTaller> obtenerTodasLasSolicitudes();
    /**
     * Actualiza el estado de una solicitud.
     * @param id ID de la solicitud
     * @param nuevoEstado Nuevo estado
     */
    void actualizarEstadoSolicitudTaller(int id, int nuevoEstado);
    /**
     * Sube el comprobante de pago de una solicitud.
     * @param id ID de la solicitud
     * @param imagenBase64 Imagen en base64
     */
    void subirComprobante(int id, String imagenBase64);
    /**
     * Elimina una solicitud de taller.
     * @param id ID de la solicitud
     */
    void eliminarSolicitudTaller(int id);
    /**
     * Agrega una nueva solicitud de taller.
     * @param solicitud Solicitud a agregar
     */
    void agregarSolicitudTaller(SolicitudTaller solicitud);
    /**
     * Obtiene talleres filtrados por estado.
     * @param idEstado ID del estado
     * @return Lista de solicitudes
     */
    List<SolicitudTaller> obtenerTalleresPorStatus(int idEstado);
    /**
     * Obtiene resumen de solicitudes de taller y asesoría.
     * @return Objeto con ambos tipos de solicitudes
     */
    org.example.model.Solicitudes obtenerSolicitudesTallerAsesoria();
    /**
     * Obtiene solicitudes de un usuario específico.
     * @param userId ID del usuario
     * @return Lista de solicitudes del usuario
     */
    List<SolicitudTaller> obtenerSolicitudesPorUsuario(int userId);
    /**
     * Obtiene estadísticas de talleres.
     * @return Lista de mapas con estadísticas
     */
    List<Map<String, Object>> obtenerEstadisticasTalleres();
}
