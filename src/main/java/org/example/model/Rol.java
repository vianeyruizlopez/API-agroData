package org.example.model;

/**
 * Clase que representa un rol de usuario en el sistema.
 * Define los permisos y accesos de agrónomos y agricultores.
 */
public class Rol {
    private int idRol;
    private String nombreRol;

    /**
     * Constructor vacío para crear un rol sin datos.
     */
    public Rol() {
    }
    /**
     * Constructor que crea un rol con ID y nombre.
     * @param idRol el ID del rol
     * @param nombreRol el nombre del rol
     */
    public Rol(int idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }


    /**
     * Obtiene el ID del rol.
     * @return el ID del rol
     */
    public int getIdRol() {
        return idRol;
    }

    /**
     * Establece el ID del rol.
     * @param idRol el ID del rol
     */
    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    /**
     * Obtiene el nombre del rol.
     * @return el nombre del rol (Agrónomo o Agricultor)
     */
    public String getNombreRol() {
        return nombreRol;
    }

    /**
     * Establece el nombre del rol.
     * @param nombreRol el nombre del rol
     */
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
