package org.example.model;

/**
 * Clase que contiene información general de usuarios para el dashboard.
 * Incluye datos personales y estadísticas de cultivos.
 */
public class InformacionGeneral {
    private int idUsuario;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String telefono;
    private String direcciones;
    private float superficieTotal ;
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

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDirecciones() {
        return direcciones;
    }

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
