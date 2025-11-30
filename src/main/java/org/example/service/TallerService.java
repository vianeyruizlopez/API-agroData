package org.example.service;
import org.example.model.Taller;
import org.example.repository.TallerRepositoryImpl;

import java.util.List;

public class TallerService implements ITallerService {
    private final TallerRepositoryImpl tallerRepository;

    public TallerService(TallerRepositoryImpl repo) {
        this.tallerRepository = repo;
    }

    @Override
    public List<Taller> obtenerTaller() {

        return tallerRepository.obtenerTaller();
    }
    @Override
    public Taller obtenerTallerPorId(int id) {
        Taller taller = tallerRepository.obtenerTallerPorId(id);
        if (taller == null) {
            throw new IllegalArgumentException("No se encontró un taller con ID " + id);
        }
        return taller;
    }
    @Override
    public void agregarTaller(Taller taller) {
        if (taller.getIdTaller() != 0) {
            throw new IllegalArgumentException("El ID debe ser generado automáticamente. No lo incluyas en la solicitud.");
        }
        if (taller.getCosto()<0){
            throw new IllegalArgumentException("El costo no puede ser negativo o 0.");
        }
        if (taller.getDescripcion()==null || taller.getDescripcion().isBlank()){
            throw new IllegalArgumentException("El nombre del taller es obligatorio.");
        }
        if (taller.getNombreTaller()==null || taller.getNombreTaller().isBlank()){
            throw new IllegalArgumentException("El nombre del taller es obligatorio.");
        }
        tallerRepository.agregarTaller(taller);
    }
    @Override
    public void eliminarTaller(int id) {
        boolean existe = tallerRepository.obtenerTaller().stream()
                .anyMatch(t -> t.getIdTaller() == id);
        if (!existe) {
            throw new IllegalArgumentException("No existe un taller con ID " + id);
        }
        tallerRepository.eliminarTaller(id);
    }
    @Override
    public void actualizarEstadoTaller(int id, int nuevoEstado) {
        tallerRepository.actualizarEstado(id, nuevoEstado);
    }
    @Override
    public void actualizarTaller(int id, Taller tallerActualizar) {
        List<Taller> existentes = tallerRepository.obtenerTaller();
        for (Taller t : existentes) {
            if (t.getIdTaller() == tallerActualizar.getIdTaller() && t.getIdTaller() != id) {
                throw new IllegalArgumentException("El ID " + tallerActualizar.getIdTaller() + " ya está en uso por otro taller.");
            }
        }
        tallerRepository.actualizarTaller(id,tallerActualizar);
    }

}