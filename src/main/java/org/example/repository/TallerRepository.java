package org.example.repository;

import org.example.model.Taller;

import java.util.List;

public interface TallerRepository {
    public List<Taller> obtenerTaller();
    Taller obtenerTallerPorId(int id);
    public void agregarTaller(Taller Taller);
    public void eliminarTaller(int id);
    public void actualizarTaller(int id, Taller tallerActualizado);

}
