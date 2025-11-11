package org.example.service;
import org.example.model.SolicitudAsesoria;
import org.example.repository.SolicitudAsesoriaRepository;
import org.example.model.CultivoPorSolicitud;
import java.util.List;

public class SolicitudAsesoriaService {
    private SolicitudAsesoriaRepository solicitudAsesoriaRepository = new SolicitudAsesoriaRepository();

    public SolicitudAsesoria obtenerSolicitudAsesoriaPorId(int id) {
        SolicitudAsesoria solicitud = solicitudAsesoriaRepository.obtenerPorId(id);
        if (solicitud != null) {
            List<CultivoPorSolicitud> cultivos = solicitudAsesoriaRepository.obtenerCultivosPorSolicitud(id);
            solicitud.setCultivos(cultivos);
        }
        return solicitud;
    }
    public List<SolicitudAsesoria> obtenerTodasLasSolicitudes() {
        return solicitudAsesoriaRepository.obtenerTodas();
    }
    public void actualizarEstadoSolicitudAsesoria(int id, int nuevoEstado) {
        solicitudAsesoriaRepository.actualizarEstado(id, nuevoEstado);
    }
    public void eliminarSolicitudAsesoria(int id) {
        solicitudAsesoriaRepository.eliminar(id);
    }
    public void agregarSolicitudAsesoria(SolicitudAsesoria solicitud) {
        try {
            solicitudAsesoriaRepository.agregar(solicitud);

            if (solicitud.getIdSolicitud() > 0 && solicitud.getCultivos() != null && !solicitud.getCultivos().isEmpty()) {
                solicitudAsesoriaRepository.agregarCultivosPorSolicitud(
                        solicitud.getIdSolicitud(),
                        solicitud.getCultivos()
                );
            } else {
                System.err.println("No se pudo agregar cultivos: idSolicitud inválido o lista vacía.");
            }

        } catch (Exception e) {
            System.err.println("Error al agregar solicitud o cultivos:");
            e.printStackTrace();
        }
    }
}
