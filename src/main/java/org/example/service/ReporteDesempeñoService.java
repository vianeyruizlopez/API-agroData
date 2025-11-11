package org.example.service;

import org.example.model.ReporteDesempeño;
import org.example.repository.ReporteDesempeñoRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReporteDesempeñoService {

    private ReporteDesempeñoRepository repository = new ReporteDesempeñoRepository();

    public ReporteDesempeñoService() {
    }

    public ReporteDesempeño obtenerReporteDesempeñoPorIdPlan(int idPlan) throws SQLException {
        return repository.obtenerReporteDesempeñoPorIdPlan(idPlan);
    }

    public void registrarReporteDesempeño(int idPlan, LocalDateTime fechaGeneracion, String observaciones) throws SQLException {
        repository.registrarReporteDesempeño(idPlan, fechaGeneracion, observaciones);
    }
}
