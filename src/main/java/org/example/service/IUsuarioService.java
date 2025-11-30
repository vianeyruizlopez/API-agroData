package org.example.service;

import org.example.model.InformacionGeneral;
import org.example.model.Usuario;
import org.example.model.administrarCliente;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    void registrar(Usuario usuario) throws SQLException;
    Optional<Usuario> obtenerPorCorreo(String correo) throws SQLException;
    Optional<Usuario> obtenerPorId(int id) throws SQLException;
    void editarPerfil(Usuario usuario) throws SQLException;
    void recuperarPassword(String correo, String nuevaPassword) throws SQLException;
    List<InformacionGeneral> obtenerInformacionGeneral() throws SQLException;
    List<administrarCliente> verTodosClientes() throws SQLException;
    void eliminarClientes(int id) throws SQLException;
    void editarClientes(int id, administrarCliente usuario) throws SQLException;
}
