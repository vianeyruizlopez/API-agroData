package org.example.service;

import org.example.model.SolicitudAsesoria;
// 1. Importar el repositorio que vamos a usar
import org.example.repository.ProyectosRepository;
import org.example.repository.SolicitudAsesoriaRepository;
import org.example.model.CultivoPorSolicitud;
import java.util.List;

public class SolicitudAsesoriaService {

    private SolicitudAsesoriaRepository solicitudAsesoriaRepository = new SolicitudAsesoriaRepository();

    // --- INICIO DE LA MODIFICACIÓN ---

    // 2. Instanciar el repositorio de Proyectos
    private ProyectosRepository proyectosRepository = new ProyectosRepository();

    // 3. Definir el ID de estado "Aceptada" (según tu JS, es 2)
    private static final int ESTADO_ACEPTADA = 2;

    // --- FIN DE LA MODIFICACIÓN ---

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

    // --- INICIO DE LA MODIFICACIÓN ---
    // 4. Modificamos este método
    public void actualizarEstadoSolicitudAsesoria(int id, int nuevoEstado) {

        // Primero, actualizamos el estado de la solicitud (como ya lo hacías)
        solicitudAsesoriaRepository.actualizarEstado(id, nuevoEstado);

        // Segundo, verificamos si el nuevo estado es "ACEPTADA"
        if (nuevoEstado == ESTADO_ACEPTADA) {
            System.out.println("INFO: Solicitud " + id + " aceptada. Intentando generar plan de cultivo...");
            try {
                // Si es aceptada, llamamos al repositorio de proyectos
                // para que cree el plan de cultivo base.
                // Esta es la línea que faltaba.
                proyectosRepository.crearPlanCultivoDesdeSolicitud(id);

                System.out.println("SUCCESS: Plan de cultivo generado automáticamente para la solicitud " + id);
            } catch (Exception e) {
                // Manejamos un posible error en la creación del plan
                System.err.println("ERROR: No se pudo generar el plan de cultivo automático para la solicitud " + id + ": " + e.getMessage());
                e.printStackTrace();
                // Opcional: Se podría revertir el cambio de estado si esto fuera transaccional
            }
        }
    }
    // --- FIN DE LA MODIFICACIÓN ---

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