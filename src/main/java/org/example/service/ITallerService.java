package org.example.service;
import org.example.model.Taller;
import java.util.List;

public interface ITallerService {
    List<Taller> obtenerTaller();
    Taller obtenerTallerPorId(int id);
    void agregarTaller(Taller Taller);
    void eliminarTaller(int id);
    void actualizarEstadoTaller(int id, int nuevoEstado);
    void actualizarTaller(int id, Taller tallerActualizar);

}
