package org.example.repository;

import org.example.config.DataBase;
import org.example.model.TipoTerreno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoTerrenoRepository {
    public List<TipoTerreno> obtener() {
        List<TipoTerreno> tipoTerreno = new ArrayList<>();

        String sql = "SELECT * FROM catalogoriego;";

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TipoTerreno terreno = new TipoTerreno();
                terreno.setIdRiego(rs.getInt("idRiego"));
                terreno.setNombreRiego(rs.getString("nombreRiego"));
                tipoTerreno.add(terreno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoTerreno;
    }
    public TipoTerreno obtenerPorId(int id) {
        String sql = "SELECT * FROM catalogoriego WHERE idRiego = ?;";
        TipoTerreno terreno = null;

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    terreno = new TipoTerreno();
                    terreno.setIdRiego(rs.getInt("idRiego"));
                    terreno.setNombreRiego(rs.getString("nombreRiego"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return terreno;
    }
}
