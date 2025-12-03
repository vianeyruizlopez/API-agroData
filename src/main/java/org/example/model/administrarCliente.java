package org.example.model;

/**
 * Clase que representa los datos básicos de un cliente.
 * Contiene información personal como nombre, teléfono y correo.
 */
public class administrarCliente {
    private int idUsuario;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correo;
    private String imagenPerfil;

    /**
     * Constructor vacío para crear un cliente sin datos.
     */
    public administrarCliente() {

    }

    /**
     * Obtiene el ID del usuario.
     * @return el ID del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el ID del usuario.
     * @param idUsuario el ID del usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre del cliente.
     * @return el nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     * @param nombre el nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido paterno del cliente.
     * @return el apellido paterno
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Establece el apellido paterno del cliente.
     * @param apellidoPaterno el apellido paterno
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del cliente.
     * @return el apellido materno
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Establece el apellido materno del cliente.
     * @param apellidoMaterno el apellido materno
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Obtiene el teléfono del cliente.
     * @return el número de teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del cliente.
     * @param telefono el número de teléfono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     * @return el correo electrónico
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del cliente.
     * @param correo el correo electrónico
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la imagen de perfil del cliente.
     * @return la URL o ruta de la imagen de perfil
     */
    public String getImagenPerfil() {
        return imagenPerfil;
    }

    /**
     * Establece la imagen de perfil del cliente.
     * @param imagenPerfil la URL o ruta de la imagen de perfil
     */
    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }
}
