package org.example.service;

import org.example.model.SolicitudTaller;

import java.util.List;
import java.util.Map;

public interface ISolicitudTallerService {
    SolicitudTaller obtenerSolicitudTallerPorId(int id);
    List<SolicitudTaller> obtenerTodasLasSolicitudes();
    void actualizarEstadoSolicitudTaller(int id, int nuevoEstado);
    void subirComprobante(int id, String imagenBase64);
    void eliminarSolicitudTaller(int id);
    void agregarSolicitudTaller(SolicitudTaller solicitud);
    List<SolicitudTaller> obtenerTalleresPorStatus(int idEstado);
    org.example.model.Solicitudes obtenerSolicitudesTallerAsesoria();
    List<SolicitudTaller> obtenerSolicitudesPorUsuario(int userId);
    List<Map<String, Object>> obtenerEstadisticasTalleres();
}
