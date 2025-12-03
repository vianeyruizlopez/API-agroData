package org.example.repository;
import org.example.model.RegistroActividad;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar el registro de actividades agrícolas.
 * Maneja las operaciones de consulta e inserción de registros de tareas.
 */
public class RegistroActividadRepository {
    private final DataSource dataSource;
    
    /**
     * Constructor del repositorio de registro de actividades.
     * @param dataSource Fuente de datos para las conexiones a la base de datos
     */
    public RegistroActividadRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Obtiene todos los registros de actividades de la base de datos.
     * @return Lista de registros de actividad
     */
    public List<RegistroActividad> obtenerRegistroActividad() {
        List<RegistroActividad> actividades = new ArrayList<>();
        String sql = "SELECT idRegistro, idTarea, imagen, comentario FROM registrotarea";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RegistroActividad actividad = new RegistroActividad();
                actividad.setIdRegistro(rs.getInt("idRegistro"));
                actividad.setIdTarea(rs.getInt("idTarea"));
                actividad.setImagen(rs.getString("imagen"));
                actividad.setDescripcion(rs.getString("comentario"));
                actividades.add(actividad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actividades;
    }

    /**
     * Agrega un nuevo registro de actividad a la base de datos.
     * @param registroActividad Registro de actividad a insertar
     */
    public void agregarActividad(RegistroActividad registroActividad) {
        String sql = "INSERT INTO registrotarea (idTarea, imagen, comentario) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, registroActividad.getIdTarea());
            stmt.setString(2, registroActividad.getImagen());
            stmt.setString(3, registroActividad.getDescripcion());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
