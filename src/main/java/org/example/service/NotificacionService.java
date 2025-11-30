package org.example.service;

import org.example.model.Notificacion;
import org.example.repository.NotificacionRepository;

import java.util.ArrayList;
import java.util.List;

public class NotificacionService implements INotificacionService{
    private final NotificacionRepository notificacionRepository =  new NotificacionRepository();
    public NotificacionService() {
    }
    @Override
    public List<Notificacion> getNotificaciones() {
        List<Notificacion> notificacionList = new ArrayList<>();
        String filtroNotificaciones = "where nombreEstado = 'Pendiente'";
        notificacionList.addAll(notificacionRepository.obtenerNotificacionesAsesorias(filtroNotificaciones));
        notificacionList.addAll(notificacionRepository.obtenerNotificacionesTalleres(filtroNotificaciones));
        notificacionList.addAll(notificacionRepository.obtenerNotificacionesTareas(filtroNotificaciones));
        return notificacionList;
    }
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