package org.example.repository;

import org.example.config.DataBase;
import org.example.model.catalogoCultivo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para manejar operaciones de base de datos del catálogo de cultivos.
 * Permite consultar cultivos por diferentes criterios.
 */
public class CatalogoCultivoRepository {
    /**
     * Obtiene todos los cultivos del catálogo.
     * @return lista de todos los cultivos disponibles
     */
    public List<catalogoCultivo> obtener() {
        List<catalogoCultivo> cultivos = new ArrayList<>();

        String sql = "SELECT * FROM catalogocultivo;";

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
    /**
     * Obtiene un cultivo por su ID.
     * @param id el ID del cultivo
     * @return el cultivo encontrado o null si no existe
     */
    public catalogoCultivo obtenerPorId(int id) {
        String sql = "SELECT * FROM catalogocultivo WHERE idCultivo = ?;";
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

    /**
     * Obtiene un cultivo por su nombre exacto.
     * @param nombre el nombre del cultivo
     * @return el cultivo encontrado o null si no existe
     */
    public catalogoCultivo obtenerPorNombre(String nombre) {
        String sql = "SELECT * FROM catalogocultivo WHERE LOWER(nombreCultivo) = LOWER(?);";
        catalogoCultivo cultivo = null;

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
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


    /**
     * Busca cultivos que contengan el término de búsqueda.
     * @param parcial término de búsqueda parcial
     * @return lista de cultivos que coinciden con la búsqueda
     */
    public List<catalogoCultivo> obtenerPorCoincidencia(String parcial) {
        String sql = "SELECT * FROM catalogocultivo WHERE LOWER(nombreCultivo) LIKE LOWER(?);";
        List<catalogoCultivo> cultivos = new ArrayList<>();

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + parcial + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    catalogoCultivo cultivo = new catalogoCultivo();
                    cultivo.setIdCultivo(rs.getInt("idCultivo"));
                    cultivo.setNombreCultivo(rs.getString("nombreCultivo"));
                    cultivos.add(cultivo);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cultivos;
    }
}
