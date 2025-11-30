package org.example.service;

import org.example.model.ReporteDesempeno;

import java.sql.SQLException;
import java.time.LocalDateTime;

public interface IReporteDesempenoService {
    ReporteDesempeno obtenerReporteDesempeñoPorIdPlan(int idPlan) throws SQLException;
    void registrarReporteDesempeño(int idPlan, LocalDateTime fechaGeneracion, String observaciones) throws SQLException;
}
