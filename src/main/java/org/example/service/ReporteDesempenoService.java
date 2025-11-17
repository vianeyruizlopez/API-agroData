package org.example.service;

import org.example.model.ReporteDesempeno;
import org.example.repository.ReporteDesempenoRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReporteDesempenoService {

    private ReporteDesempenoRepository repository = new ReporteDesempenoRepository();

    public ReporteDesempenoService() {
    }

    public ReporteDesempeno obtenerReporteDesempeñoPorIdPlan(int idPlan) throws SQLException {
        return repository.obtenerReporteDesempeñoPorIdPlan(idPlan);
    }

    public void registrarReporteDesempeño(int idPlan, LocalDateTime fechaGeneracion, String observaciones) throws SQLException {
        repository.registrarReporteDesempeño(idPlan, fechaGeneracion, observaciones);
    }
}
