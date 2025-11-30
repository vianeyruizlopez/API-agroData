package org.example.service;

import org.example.model.RegistroActividad;

import java.util.List;

public interface IRegistroActividadService {
    List<RegistroActividad> obtenerRegistroActividad();
    void agregarActividad(RegistroActividad registroActividad);
}
