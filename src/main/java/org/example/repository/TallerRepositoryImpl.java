package org.example.repository;

import org.example.config.DataBase;
import org.example.model.Taller;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del repositorio para gestionar talleres agrícolas.
 * Maneja las operaciones CRUD en la base de datos para talleres y sus costos.
 */
public class TallerRepositoryImpl implements TallerRepository {
    /**
     * Constructor vacío para crear un repositorio de talleres sin inicializar datos.
     */
    public TallerRepositoryImpl() {
    }
    /**
     * Obtiene todos los talleres activos con su información de costo.
     * @return Lista de talleres con estado visual calculado
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    @Override
    public List<Taller> obtenerTaller() {
        List<Taller> talleres = new ArrayList<>();
        String sql = """
    SELECT c.idTaller, c.nombreTaller, c.descripcion, c.idEstado, t.costo
    FROM catalogotaller c
   inner join costotaller t ON c.idTaller = t.idTaller WHERE c.activo=1
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
    /**
     * Obtiene un taller específico por su ID.
     * @param id ID del taller
     * @return Taller encontrado o null si no existe
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
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

    /**
     * Agrega un nuevo taller con su costo asociado.
     * @param taller Taller a agregar
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
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

    /**
     * Actualiza un taller existente y su costo.
     * @param id ID del taller a actualizar
     * @param tallerActualizado Datos actualizados del taller
     * @throws IllegalArgumentException Si el taller no existe
     * @throws RuntimeException Si hay error en la transacción
     */
    @Override
    public void actualizarTaller(int id, Taller tallerActualizado) {
        String sqlCatalogo = "UPDATE catalogotaller SET nombreTaller = ?, descripcion = ?, idEstado = ? WHERE idTaller = ?";
        String sqlCosto = "UPDATE costotaller SET costo = ? WHERE idTaller = ?";
        try (Connection conn = DataBase.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

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

    /**
     * Marca un taller como inactivo (eliminación lógica).
     * @param id ID del taller a eliminar
     * @throws RuntimeException Si hay error en la transacción
     */
    @Override
    public void eliminarTaller(int id) {
        String sqlDeleteCosto = "UPDATE catalogotaller set activo=0 WHERE idTaller = ?";



        try (Connection conn = DataBase.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtCosto = conn.prepareStatement(sqlDeleteCosto)) {

                stmtCosto.setInt(1, id);
                stmtCosto.executeUpdate();



                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error al eliminar el taller", e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     * Actualiza el estado de un taller.
     * @param id ID del taller
     * @param nuevoEstado Nuevo estado del taller
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
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

    /**
     * Verifica si existe un taller con el ID especificado.
     * @param id ID del taller a verificar
     * @return true si existe, false en caso contrario
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
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