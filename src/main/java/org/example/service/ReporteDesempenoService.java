package org.example.service;

import org.example.model.ReporteDesempeno;
import org.example.repository.ReporteDesempenoRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReporteDesempenoService implements IReporteDesempenoService {

    private final ReporteDesempenoRepository repository = new ReporteDesempenoRepository();

    public ReporteDesempenoService() {
    }

    @Override
    public ReporteDesempeno obtenerReporteDesempeñoPorIdPlan(int idPlan) throws SQLException {
        return repository.obtenerReporteDesempeñoPorIdPlan(idPlan);
    }

    @Override
    public void registrarReporteDesempeño(int idPlan, LocalDateTime fechaGeneracion, String observaciones) throws SQLException {
        repository.registrarReporteDesempeño(idPlan, fechaGeneracion, observaciones);
    }
}
