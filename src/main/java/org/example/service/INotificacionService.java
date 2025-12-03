package org.example.service;

import org.example.model.Notificacion;

import java.util.List;

/**
 * Interfaz que define servicios para manejar notificaciones.
 * Permite consultar notificaciones para agrónomos y agricultores.
 */
public interface INotificacionService {
    /**
     * Obtiene notificaciones para agrónomos.
     * @return lista de notificaciones pendientes
     */
    List<Notificacion> getNotificaciones();
    /**
     * Obtiene todas las notificaciones de un usuario específico.
     * @param usuarioId el ID del usuario
     * @return lista de notificaciones del usuario
     */
    List<Notificacion> getTodasNotificaciones(int usuarioId);
}
