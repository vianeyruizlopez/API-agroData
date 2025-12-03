package org.example.service;

import org.example.model.SolicitudAsesoria;
// 1. Importar el repositorio que vamos a usar
import org.example.repository.ProyectosRepository;
import org.example.repository.SolicitudAsesoriaRepository;
import org.example.model.CultivoPorSolicitud;
import java.util.List;

/**
 * Servicio que implementa la lógica de negocio para solicitudes de asesoría.
 * Maneja la creación, consulta y actualización de solicitudes.
 */
public class SolicitudAsesoriaService implements ISolicitudAsesoriaService {

    private final SolicitudAsesoriaRepository solicitudAsesoriaRepository = new SolicitudAsesoriaRepository();

    private final ProyectosRepository proyectosRepository = new ProyectosRepository();

    private static final int ESTADO_ACEPTADA = 2;

    /**
     * Obtiene una solicitud por ID incluyendo sus cultivos asociados.
     * @param id el ID de la solicitud
     * @return la solicitud con sus cultivos o null si no existe
     */
    @Override
    public SolicitudAsesoria obtenerSolicitudAsesoriaPorId(int id) {
        SolicitudAsesoria solicitud = solicitudAsesoriaRepository.obtenerPorId(id);
        if (solicitud != null) {
            List<CultivoPorSolicitud> cultivos = solicitudAsesoriaRepository.obtenerCultivosPorSolicitud(id);
            solicitud.setCultivos(cultivos);
        }
        return solicitud;
    }

    /**
     * Obtiene todas las solicitudes de asesoría del sistema.
     * @return lista de todas las solicitudes
     */
    @Override
    public List<SolicitudAsesoria> obtenerTodasLasSolicitudes() {
        return solicitudAsesoriaRepository.obtenerTodas();
    }

    /**
     * Actualiza el estado de una solicitud y genera plan de cultivo si es aceptada.
     * @param id el ID de la solicitud
     * @param nuevoEstado el nuevo estado (2 = aceptada genera plan automáticamente)
     */
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

    /**
     * Elimina una solicitud de asesoría del sistema.
     * @param id el ID de la solicitud a eliminar
     */
    @Override
    public void eliminarSolicitudAsesoria(int id) {
        solicitudAsesoriaRepository.eliminar(id);
    }

    /**
     * Agrega una nueva solicitud incluyendo sus cultivos asociados.
     * @param solicitud la solicitud a agregar con sus cultivos
     */
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