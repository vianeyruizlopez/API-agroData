package org.example.model;

/**
 * Clase que contiene información general de usuarios para el dashboard.
 * Incluye datos personales y estadísticas de cultivos.
 */
public class InformacionGeneral {

    /**
     * Identificador único del usuario dentro del sistema.
     */
    private int idUsuario;

    /**
     * Nombre de pila del usuario.
     */
    private String nombre;

    /**
     * Apellido paterno del usuario.
     */
    private String apellidoPaterno;

    /**
     * Apellido materno del usuario.
     */
    private String apellidoMaterno;

    /**
     * Dirección de correo electrónico del usuario.
     */
    private String correo;

    /**
     * Número de teléfono de contacto del usuario.
     */
    private String telefono;

    /**
     * Direcciones asociadas al usuario (ejemplo: domicilio, parcelas).
     */
    private String direcciones;

    /**
     * Superficie total de cultivos en hectáreas.
     */
    private float superficieTotal;

    /**
     * Lista de cultivos asociados al usuario como cadena.
     */
    private String cultivos;

    /**
     * Constructor vacío para crear información general sin datos.
     */
    public InformacionGeneral() {
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
     * @param idUsuario el identificador único del usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return el nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre el nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido paterno del usuario.
     * @return el apellido paterno
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Establece el apellido paterno del usuario.
     * @param apellidoPaterno el apellido paterno
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del usuario.
     * @return el apellido materno
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Establece el apellido materno del usuario.
     * @param apellidoMaterno el apellido materno
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return el correo electrónico
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     * @param correo la dirección de correo electrónico
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el número de teléfono del usuario.
     * @return el número de teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del usuario.
     * @param telefono el número de contacto
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene las direcciones asociadas al usuario.
     * @return las direcciones del usuario
     */
    public String getDirecciones() {
        return direcciones;
    }

    /**
     * Establece las direcciones asociadas al usuario.
     * @param direcciones las direcciones del usuario
     */
    public void setDirecciones(String direcciones) {
        this.direcciones = direcciones;
    }

    /**
     * Obtiene la superficie total de cultivos en hectáreas.
     * @return la superficie total
     */
    public float getSuperficieTotal() {
        return superficieTotal;
    }

    /**
     * Establece la superficie total de cultivos.
     * @param superficieTotal la superficie en hectáreas
     */
    public void setSuperficieTotal(float superficieTotal) {
        this.superficieTotal = superficieTotal;
    }

    /**
     * Obtiene la lista de cultivos como cadena.
     * @return los cultivos del usuario
     */
    public String getCultivos() {
        return cultivos;
    }

    /**
     * Establece la lista de cultivos del usuario.
     * @param cultivos los cultivos asociados
     */
    public void setCultivos(String cultivos) {
        this.cultivos = cultivos;
    }

    /**
     * Convierte la información general a texto.
     * @return cadena con toda la información del usuario
     */
    @Override
    public String toString() {
        return "InformacionGeneral{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono=" + telefono +
                ", direcciones='" + direcciones + '\'' +
                ", superficieTotal=" + superficieTotal +
                ", cultivos='" + cultivos + '\'' +
                '}';
    }
}