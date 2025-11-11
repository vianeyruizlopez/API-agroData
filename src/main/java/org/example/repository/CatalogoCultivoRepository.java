package org.example.repository;

import org.example.config.DataBase;
import org.example.model.catalogoCultivo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogoCultivoRepository {
    public List<catalogoCultivo> obtener() {
        List<catalogoCultivo> cultivos = new ArrayList<>();

        String sql = "SELECT * FROM catalogoCultivo;";

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                catalogoCultivo cultivo = new catalogoCultivo();
                cultivo.setIdCultivo(rs.getInt("idCultivo"));
                cultivo.setNombreCultivo(rs.getString("nombreCultivo"));
                cultivos.add(cultivo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cultivos;
    }
    public catalogoCultivo obtenerPorId(int id) {
        String sql = "SELECT * FROM catalogoCultivo WHERE idCultivo = ?;";
        catalogoCultivo cultivo = null;

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cultivo = new catalogoCultivo();
                    cultivo.setIdCultivo(rs.getInt("idCultivo"));
                    cultivo.setNombreCultivo(rs.getString("nombreCultivo"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cultivo;
    }
}
