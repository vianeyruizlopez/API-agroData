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

/**
 * Implementación del repositorio de estados.
 * Maneja operaciones de base de datos para el catálogo de estados.
 */
public class EstadoRepositoryImpl implements  EstadoRepository {
    /**
     * Obtiene todos los estados del catálogo.
     * @return lista de todos los estados disponibles
     */
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
    /**
     * Obtiene un estado por su ID.
     * @param id el ID del estado
     * @return el estado encontrado o null si no existe
     */
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
