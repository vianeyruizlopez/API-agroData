package org.example.service;

import org.example.model.SolicitudAsesoria;
// 1. Importar el repositorio que vamos a usar
import org.example.repository.ProyectosRepository;
import org.example.repository.SolicitudAsesoriaRepository;
import org.example.model.CultivoPorSolicitud;
import java.util.List;

public class SolicitudAsesoriaService implements ISolicitudAsesoriaService {

    private final SolicitudAsesoriaRepository solicitudAsesoriaRepository = new SolicitudAsesoriaRepository();

    private final ProyectosRepository proyectosRepository = new ProyectosRepository();

    private static final int ESTADO_ACEPTADA = 2;

    @Override
    public SolicitudAsesoria obtenerSolicitudAsesoriaPorId(int id) {
        SolicitudAsesoria solicitud = solicitudAsesoriaRepository.obtenerPorId(id);
        if (solicitud != null) {
            List<CultivoPorSolicitud> cultivos = solicitudAsesoriaRepository.obtenerCultivosPorSolicitud(id);
            solicitud.setCultivos(cultivos);
        }
        return solicitud;
    }

    @Override
    public List<SolicitudAsesoria> obtenerTodasLasSolicitudes() {
        return solicitudAsesoriaRepository.obtenerTodas();
    }

    @Override
    public void actualizarEstadoSolicitudAsesoria(int id, int nuevoEstado) {

        solicitudAsesoriaRepository.actualizarEstado(id, nuevoEstado);

        if (nuevoEstado == ESTADO_ACEPTADA) {
            System.out.println("INFO: Solicitud " + id + " aceptada. Intentando generar plan de cultivo...");
            try {

                proyectosRepository.crearPlanCultivoDesdeSolicitud(id);

                System.out.println("SUCCESS: Plan de cultivo generado automáticamente para la solicitud " + id);
            } catch (Exception e) {

                System.err.println("ERROR: No se pudo generar el plan de cultivo automático para la solicitud " + id + ": " + e.getMessage());
                e.printStackTrace();

            }
        }
    }

    @Override
    public void eliminarSolicitudAsesoria(int id) {
        solicitudAsesoriaRepository.eliminar(id);
    }

    @Override
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