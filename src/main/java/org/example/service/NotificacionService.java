package org.example.service;

import org.example.model.Notificacion;
import org.example.repository.NotificacionRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que implementa la lógica de negocio para notificaciones.
 * Consolida notificaciones de asesorías, talleres y tareas.
 */
public class NotificacionService implements INotificacionService{
    private final NotificacionRepository notificacionRepository =  new NotificacionRepository();
    /**
     * Constructor del servicio de notificaciones.
     */
    public NotificacionService() {
    }
    /**
     * Obtiene notificaciones pendientes para agrónomos.
     * @return lista consolidada de notificaciones pendientes
     */
    @Override
    public List<Notificacion> getNotificaciones() {
        List<Notificacion> notificacionList = new ArrayList<>();
        String filtroNotificaciones = "where nombreEstado = 'Pendiente'";
        notificacionList.addAll(notificacionRepository.obtenerNotificacionesAsesorias(filtroNotificaciones));
        notificacionList.addAll(notificacionRepository.obtenerNotificacionesTalleres(filtroNotificaciones));
        notificacionList.addAll(notificacionRepository.obtenerNotificacionesTareas(filtroNotificaciones));
        return notificacionList;
    }
    /**
     * Obtiene todas las notificaciones de un usuario específico.
     * @param usuarioId el ID del usuario
     * @return lista consolidada de notificaciones del usuario
     */
    @Override
    public List<Notificacion> getTodasNotificaciones(int usuarioId) {
        String filtro = "WHERE idUsuario = " + usuarioId;

        List<Notificacion> asesoria = notificacionRepository.obtenerNotificacionesAsesorias(filtro);
        List<Notificacion> taller = notificacionRepository.obtenerNotificacionesTalleres(filtro);
        List<Notificacion> tarea = notificacionRepository.obtenerNotificacionesTareas(filtro);

        List<Notificacion> todas = new ArrayList<>();
        todas.addAll(asesoria);
        todas.addAll(taller);
        todas.addAll(tarea);
        return todas;
    }
} 