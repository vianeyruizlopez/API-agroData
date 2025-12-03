package org.example.service;

import org.example.model.SolicitudAsesoria;

import java.util.List;

/**
 * Interfaz que define los servicios para manejar solicitudes de asesoría.
 * Incluye operaciones CRUD y lógica de negocio para solicitudes.
 */
public interface ISolicitudAsesoriaService {
    /**
     * Obtiene una solicitud de asesoría por su ID.
     * @param id el ID de la solicitud
     * @return la solicitud encontrada o null si no existe
     */
    SolicitudAsesoria obtenerSolicitudAsesoriaPorId(int id);
    /**
     * Obtiene todas las solicitudes de asesoría del sistema.
     * @return lista de todas las solicitudes
     */
    List<SolicitudAsesoria> obtenerTodasLasSolicitudes();
    /**
     * Actualiza el estado de una solicitud de asesoría.
     * @param id el ID de la solicitud
     * @param nuevoEstado el nuevo estado de la solicitud
     */
    void actualizarEstadoSolicitudAsesoria(int id, int nuevoEstado);
    /**
     * Elimina una solicitud de asesoría del sistema.
     * @param id el ID de la solicitud a eliminar
     */
    void eliminarSolicitudAsesoria(int id);
    /**
     * Agrega una nueva solicitud de asesoría al sistema.
     * @param solicitud la solicitud a agregar
     */
    void agregarSolicitudAsesoria(SolicitudAsesoria solicitud);
}
