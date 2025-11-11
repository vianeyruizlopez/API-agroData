package org.example.service;
import org.example.model.Taller;
import java.util.List;

public interface TallerService {
    public List<Taller> obtenerTaller();
    Taller obtenerTallerPorId(int id);
    public void agregarTaller(Taller Taller);
    public void eliminarTaller(int id);
    public void actualizarTaller(int id, Taller tallerActualizar);
    public void actualizarEstadoTaller(int id, int nuevoEstado);

}
