package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase que representa una solicitud de taller por parte de un agricultor.
 * Incluye fechas, ubicación y estado de pago.
 */
public class SolicitudTaller {
    private int idSolicitudTaller;
    private int idAgricultor;
    private String nombreAgricultor;
    private int idTaller;
    private String nombreTaller;

    /**
     * Nombre del agrónomo que impartirá el taller.
     */
    private String nombreAgronomo;
    
    /**
     * Información sobre quién impartió el taller.
     */
    private String impartio;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaSolicitud;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAplicarTaller;

    private String direccion;
    private String comentario;
    private int idEstado;
    private String estadoPagoImagen;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    /**
     * Constructor vacío para crear una solicitud de taller sin datos.
     */
    public SolicitudTaller() {}

    // --- Getters y Setters ---

    /**
     * Obtiene el nombre del taller solicitado.
     * @return el nombre del taller
     */
    public String getNombreTaller() {
        return nombreTaller;
    }

    public void setNombreTaller(String nombreTaller) {
        this.nombreTaller = nombreTaller;
    }

    /**
     * Obtiene el ID de la solicitud de taller.
     * @return el ID de la solicitud
     */
    public int getIdSolicitudTaller() { return idSolicitudTaller; }
    public void setIdSolicitudTaller(int idSolicitudTaller) { this.idSolicitudTaller = idSolicitudTaller; }

    public int getIdAgricultor() { return idAgricultor; }
    public void setIdAgricultor(int idAgricultor) { this.idAgricultor = idAgricultor; }

    public String getNombreAgricultor() { return nombreAgricultor; }
    public void setNombreAgricultor(String nombreAgricultor) { this.nombreAgricultor = nombreAgricultor; }

    /**
     * Obtiene el nombre del agrónomo asignado.
     * @return el nombre del agrónomo
     */
    public String getNombreAgronomo() { return nombreAgronomo; }
    /**
     * Establece el nombre del agrónomo asignado.
     * @param nombreAgronomo el nombre del agrónomo
     */
    public void setNombreAgronomo(String nombreAgronomo) { this.nombreAgronomo = nombreAgronomo; }

    /**
     * Obtiene información sobre quién impartió el taller.
     * @return información del instructor
     */
    public String getImpartio() { return impartio; }
    /**
     * Establece información sobre quién impartió el taller.
     * @param impartio información del instructor
     */
    public void setImpartio(String impartio) { this.impartio = impartio; }

    public int getIdTaller() { return idTaller; }
    public void setIdTaller(int idTaller) { this.idTaller = idTaller; }

    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    /**
     * Obtiene la fecha programada para el taller.
     * @return la fecha del taller
     */
    public LocalDate getFechaAplicarTaller() { return fechaAplicarTaller; }
    public void setFechaAplicarTaller(LocalDate fechaAplicarTaller) { this.fechaAplicarTaller = fechaAplicarTaller; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public int getIdEstado() { return idEstado; }
    public void setIdEstado(int idEstado) { this.idEstado = idEstado; }

    /**
     * Obtiene la imagen del comprobante de pago.
     * @return la imagen del comprobante en base64
     */
    public String getEstadoPagoImagen() { return estadoPagoImagen; }
    public void setEstadoPagoImagen(String estadoPagoImagen) { this.estadoPagoImagen = estadoPagoImagen; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}