package org.example.repository;
import org.example.config.DataBase;
import org.example.model.Rol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RolRepository {
    public Rol obtenerPorId(int idRol) {
        Rol rol = null;
        String sql = "SELECT * FROM catalogorol WHERE idRol = ?";

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRol);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rol = new Rol();
                rol.setIdRol(rs.getInt("idRol"));
                rol.setNombreRol(rs.getString("nombreRol"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rol;
    }

}
