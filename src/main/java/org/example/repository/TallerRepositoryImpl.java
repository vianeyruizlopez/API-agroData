package org.example.repository;

import org.example.config.DataBase;
import org.example.model.Taller;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TallerRepositoryImpl implements TallerRepository {

    @Override
    public List<Taller> obtenerTaller() {
        List<Taller> talleres = new ArrayList<>();
        String sql = """
    SELECT c.idTaller, c.nombreTaller, c.descripcion, c.idEstado, t.costo
    FROM catalogotaller c
   inner join costotaller t ON c.idTaller = t.idTaller
""";

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Taller taller = new Taller();
                int idEstado = rs.getInt("idEstado");
                taller.setIdEstado(idEstado);

                String estadoVisual;
                switch (idEstado) {
                    case 1, 5 -> estadoVisual = "Próximo";
                    case 4 -> estadoVisual = "Completado";
                    default -> estadoVisual = "En curso";
                }

                taller.setIdTaller(rs.getInt("idTaller"));
                taller.setNombreTaller(rs.getString("nombreTaller"));
                taller.setDescripcion(rs.getString("descripcion"));
                taller.setCosto(rs.getFloat("costo"));

                talleres.add(taller);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return talleres;
    }
    public Taller obtenerTallerPorId(int id) {
        String sql = """
        SELECT c.idTaller, c.nombreTaller, c.descripcion, c.idEstado, t.costo
        FROM catalogotaller c
        INNER JOIN costotaller t ON c.idTaller = t.idTaller
        WHERE c.idTaller = ?
    """;

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Taller taller = new Taller();
                    int idEstado = rs.getInt("idEstado");

                    String estadoVisual;
                    switch (idEstado) {
                        case 1, 5 -> estadoVisual = "Próximo";
                        case 4 -> estadoVisual = "Completado";
                        default -> estadoVisual = "En curso";
                    }

                    taller.setIdTaller(rs.getInt("idTaller"));
                    taller.setNombreTaller(rs.getString("nombreTaller"));
                    taller.setDescripcion(rs.getString("descripcion"));
                    taller.setCosto(rs.getFloat("costo"));
                    taller.setIdEstado(idEstado);

                    return taller;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void agregarTaller(Taller taller) {
        String sqlCatalogo = "INSERT INTO catalogotaller (nombreTaller, descripcion, idEstado) VALUES (?, ?, ?)";
        String sqlCosto = "INSERT INTO costotaller (idTaller, costo) VALUES (?, ?)";

        try (Connection conn = DataBase.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtCatalogo = conn.prepareStatement(sqlCatalogo, Statement.RETURN_GENERATED_KEYS)) {
                stmtCatalogo.setString(1, taller.getNombreTaller());
                stmtCatalogo.setString(2, taller.getDescripcion());
                stmtCatalogo.setInt(3, taller.getIdEstado());
                stmtCatalogo.executeUpdate();

                try (ResultSet generatedKeys = stmtCatalogo.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGenerado = generatedKeys.getInt(1);
                        taller.setIdTaller(idGenerado);

                        try (PreparedStatement stmtCosto = conn.prepareStatement(sqlCosto)) {
                            stmtCosto.setInt(1, idGenerado);
                            stmtCosto.setFloat(2, taller.getCosto());
                            stmtCosto.executeUpdate();
                        }
                    }
                }
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void actualizarTaller(int id, Taller tallerActualizado) {
        String sqlCatalogo = "UPDATE catalogotaller SET nombreTaller = ?, descripcion = ?, idEstado = ? WHERE idTaller = ?";
        String sqlCosto = "UPDATE costotaller SET costo = ? WHERE idTaller = ?";
        try (Connection conn = DataBase.getDataSource().getConnection()) {
            conn.setAutoCommit(false); // Inicia transacción

            try (PreparedStatement stmtCatalogo = conn.prepareStatement(sqlCatalogo);
                 PreparedStatement stmtCosto = conn.prepareStatement(sqlCosto)) {

                stmtCatalogo.setString(1, tallerActualizado.getNombreTaller());
                stmtCatalogo.setString(2, tallerActualizado.getDescripcion());
                stmtCatalogo.setInt(3, tallerActualizado.getIdEstado());
                stmtCatalogo.setInt(4, id);
                int filasCatalogo = stmtCatalogo.executeUpdate();

                stmtCosto.setFloat(1, tallerActualizado.getCosto());
                stmtCosto.setInt(2, id);
                int filasCosto = stmtCosto.executeUpdate();

                if (filasCatalogo == 0 || filasCosto == 0) {
                    conn.rollback();
                    throw new IllegalArgumentException("No existe un taller con ID " + id);
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error al actualizar el taller", e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void eliminarTaller(int id) {
        String sqlDeleteCosto = "DELETE FROM costotaller WHERE idTaller = ?";
        String sqlDeleteCatalogo = "DELETE FROM catalogotaller WHERE idTaller = ?";

        try (Connection conn = DataBase.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtCosto = conn.prepareStatement(sqlDeleteCosto);
                 PreparedStatement stmtCatalogo = conn.prepareStatement(sqlDeleteCatalogo)) {

                stmtCosto.setInt(1, id);
                stmtCosto.executeUpdate();

                stmtCatalogo.setInt(1, id);
                stmtCatalogo.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error al eliminar el taller", e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void actualizarEstado(int id, int nuevoEstado) {
        String sql = "UPDATE catalogotaller SET idEstado = ? WHERE idTaller = ?";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nuevoEstado);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeTallerPorId(int id) {
        String sql = "SELECT 1 FROM catalogotaller WHERE idtaller = ?";

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}