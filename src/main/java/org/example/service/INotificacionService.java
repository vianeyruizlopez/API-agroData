package org.example.service;

import org.example.model.Notificacion;

import java.util.List;

public interface INotificacionService {
    List<Notificacion> getNotificaciones();
    List<Notificacion> getTodasNotificaciones(int usuarioId);
}
