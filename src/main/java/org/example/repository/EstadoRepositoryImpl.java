package org.example.repository;

import org.example.config.DataBase;
import org.example.model.Estado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EstadoRepositoryImpl implements  EstadoRepository {
    @Override
    public List<Estado> obtenerEstados() {
        List<Estado> estados = new ArrayList<>();

        String sql = "SELECT * FROM catalogoestado;";

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Estado estado = new Estado();
                estado.setIdEstado(rs.getInt("idEstado"));
                estado.setNombre(rs.getString("nombreEstado"));
                estados.add(estado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estados;

    }
    public Estado obtenerPorId(int id) {
        String sql = "SELECT * FROM catalogoestado WHERE idEstado = ?;";
        Estado estado = null;

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    estado = new Estado();
                    estado.setIdEstado(rs.getInt("idEstado"));
                    estado.setNombre(rs.getString("nombreEstado"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estado;
    }
}
