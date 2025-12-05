package org.example.repository;

import org.example.config.DataBase;
import org.example.model.SolicitudAsesoria;
import org.example.model.CultivoPorSolicitud;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar las solicitudes de asesoría agrícola.
 * Maneja las operaciones CRUD relacionadas con solicitudes y cultivos asociados.
 */
public class SolicitudAsesoriaRepository {

    /**
     * Constructor por defecto del repositorio de solicitudes de asesoría.
     */
    public SolicitudAsesoriaRepository() {}

    /**
     * Obtiene una solicitud de asesoría por su ID.
     * @param id ID de la solicitud
     * @return Solicitud de asesoría encontrada o null si no existe
     *@throws SQLException si ocurre un error al consultar la base de datos
     */
    public SolicitudAsesoria obtenerPorId(int id) {
        String sql = """
            SELECT s.*, r.nombreRiego, CONCAT(u.nombre, ' ', u.apellidoPaterno, ' ', u.apellidoMaterno) AS nombreCompleto
            FROM solicitudasesoria s
            JOIN catalogoriego r ON s.tipoRiego = r.idRiego
            JOIN usuario u ON s.idAgricultor = u.idUsuario
            WHERE s.idSolicitud = ?
        """;

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                } else {
                    System.out.println("No se encontró la solicitud con ID: " + id);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener la solicitud por ID: " + id);
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtiene todas las solicitudes de asesoría pendientes.
     * @return Lista de solicitudes de asesoría con cultivos asociados
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public List<SolicitudAsesoria> obtenerTodas() {
        List<SolicitudAsesoria> lista = new ArrayList<>();


        String sql = """
            SELECT s.*, r.nombreRiego, CONCAT(u.nombre, ' ', u.apellidoPaterno, ' ', u.apellidoMaterno) AS nombreCompleto
            FROM solicitudasesoria s
            JOIN catalogoriego r ON s.tipoRiego = r.idRiego 
            JOIN catalogoestado e ON s.idEstado = e.idEstado
            JOIN usuario u ON s.idAgricultor = u.idUsuario
            WHERE e.idEstado = 1
        """;
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SolicitudAsesoria solicitud = mapear(rs);


                List<CultivoPorSolicitud> cultivos = obtenerCultivosPorSolicitud(solicitud.getIdSolicitud());
                solicitud.setCultivos(cultivos);

                lista.add(solicitud);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todas las solicitudes de asesoría:");
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Agrega una nueva solicitud de asesoría a la base de datos.
     * @param solicitud Solicitud de asesoría a insertar
     *@throws SQLException si ocurre un error al consultar la base de datos
     */
    public void agregar(SolicitudAsesoria solicitud) {
        String sql = """
            INSERT INTO solicitudasesoria (
                idAgricultor,
                fechaSolicitud,
                usoMaquinaria,
                nombreMaquinaria,
                tipoRiego,
                tienePlaga,
                descripcionPlaga,
                superficieTotal,
                direccionTerreno,
                motivoAsesoria,
                idEstado
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, solicitud.getIdAgricultor());

            LocalDateTime fecha = solicitud.getFechaSolicitud() != null ? solicitud.getFechaSolicitud() : LocalDateTime.now();
            stmt.setObject(2, fecha);

            stmt.setBoolean(3, solicitud.isUsoMaquinaria());
            stmt.setString(4, solicitud.getNombreMaquinaria());
            stmt.setInt(5, solicitud.getTipoRiego());
            stmt.setBoolean(6, solicitud.isTienePlaga());
            stmt.setString(7, solicitud.getDescripcionPlaga());
            stmt.setFloat(8, solicitud.getSuperficieTotal());
            stmt.setString(9, solicitud.getDireccionTerreno());
            stmt.setString(10, solicitud.getMotivoAsesoria());
            stmt.setInt(11, solicitud.getIdEstado());

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                solicitud.setIdSolicitud(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Agrega los cultivos asociados a una solicitud de asesoría.
     * @param idSolicitud ID de la solicitud
     * @param cultivos Lista de cultivos a asociar
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public void agregarCultivosPorSolicitud(int idSolicitud, List<CultivoPorSolicitud> cultivos) {
        String sql = "INSERT INTO cultivoporsolicitud (idSolicitud, idCultivo) VALUES (?, ?)";
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (CultivoPorSolicitud cultivo : cultivos) {
                stmt.setInt(1, idSolicitud);
                stmt.setInt(2, cultivo.getIdCultivo());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el estado de una solicitud de asesoría.
     * @param id ID de la solicitud
     * @param nuevoEstado Nuevo estado de la solicitud
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public void actualizarEstado(int id, int nuevoEstado) {
        String sql = "UPDATE solicitudasesoria SET idEstado = ? WHERE idSolicitud = ?";
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
     * Elimina una solicitud de asesoría de la base de datos.
     * @param id ID de la solicitud a eliminar
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public void eliminar(int id) {
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM solicitudasesoria WHERE idSolicitud = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene los cultivos asociados a una solicitud específica.
     * @param idSolicitud ID de la solicitud
     * @return Lista de cultivos por solicitud
     * @throws SQLException si ocurre un error al consultar la base de datos
     */
    public List<CultivoPorSolicitud> obtenerCultivosPorSolicitud(int idSolicitud) {
        List<CultivoPorSolicitud> lista = new ArrayList<>();
        String sql = """
            SELECT cps.idSolicitud, cps.idCultivo, c.nombreCultivo
            FROM cultivoporsolicitud cps
            JOIN catalogocultivo c ON cps.idCultivo = c.idCultivo
            WHERE cps.idSolicitud = ?
        """;
        try (Connection conn = DataBase.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSolicitud);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CultivoPorSolicitud cps = new CultivoPorSolicitud();
                cps.setIdSolicitud(rs.getInt("idSolicitud"));
                cps.setIdCultivo(rs.getInt("idCultivo"));
                cps.setNombreCultivo(rs.getString("nombreCultivo"));
                lista.add(cps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Mapea un ResultSet a un objeto SolicitudAsesoria.
     * @param rs ResultSet con los datos de la solicitud
     * @return Objeto SolicitudAsesoria mapeado
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private SolicitudAsesoria mapear(ResultSet rs) throws SQLException {
        SolicitudAsesoria s = new SolicitudAsesoria();
        s.setIdSolicitud(rs.getInt("idSolicitud"));
        s.setIdAgricultor(rs.getInt("idAgricultor"));

        try {
            s.setNombreAgricultor(rs.getString("nombreCompleto"));
        } catch (SQLException e) {

            s.setNombreAgricultor("Desconocido");
        }

        Timestamp ts = rs.getTimestamp("fechaSolicitud");
        if (ts != null) {
            s.setFechaSolicitud(ts.toLocalDateTime());
        }

        s.setUsoMaquinaria(rs.getBoolean("usoMaquinaria"));
        s.setNombreMaquinaria(rs.getString("nombreMaquinaria"));
        s.setTipoRiego(rs.getInt("tipoRiego"));
        s.setNombreRiego(rs.getString("nombreRiego"));
        s.setTienePlaga(rs.getBoolean("tienePlaga"));
        s.setDescripcionPlaga(rs.getString("descripcionPlaga"));
        s.setSuperficieTotal(rs.getFloat("superficieTotal"));
        s.setDireccionTerreno(rs.getString("direccionTerreno"));
        s.setMotivoAsesoria(rs.getString("motivoAsesoria"));
        s.setIdEstado(rs.getInt("idEstado"));
        return s;
    }
}