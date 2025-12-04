package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase que representa una solicitud de taller por parte de un agricultor.
 * Incluye fechas, ubicación y estado de pago.
 */
public class SolicitudTaller {
    /** Identificador único de la solicitud de taller. */
    private int idSolicitudTaller;

    /** Identificador del agricultor que solicita el taller. */
    private int idAgricultor;

    /** Nombre del agricultor asociado a la solicitud. */
    private String nombreAgricultor;

    /** Identificador del taller solicitado. */
    private int idTaller;

    /** Nombre del taller solicitado. */
    private String nombreTaller;

    /**
     * Nombre del agrónomo que impartirá el taller.
     */
    private String nombreAgronomo;

    /**
     * Información sobre quién impartió el taller.
     */
    private String impartio;

    /** Fecha y hora en que se realizó la solicitud. */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaSolicitud;

    /** Fecha programada para aplicar el taller. */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAplicarTaller;

    /** Dirección física donde se llevará a cabo el taller. */
    private String direccion;

    /** Comentarios adicionales relacionados con la solicitud. */
    private String comentario;

    /** Estado actual de la solicitud (ej. pendiente, aprobado, rechazado). */
    private int idEstado;

    /** Imagen del comprobante de pago en formato base64. */
    private String estadoPagoImagen;

    /** Fecha de finalización del taller. */
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

    /**
     * Establece el nombre del taller solicitado.
     * @param nombreTaller el nombre del taller
     */
    public void setNombreTaller(String nombreTaller) {
        this.nombreTaller = nombreTaller;
    }

    /**
     * Obtiene el ID de la solicitud de taller.
     * @return el ID de la solicitud
     */
    public int getIdSolicitudTaller() { return idSolicitudTaller; }

    /**
     * Establece el ID de la solicitud de taller.
     * @param idSolicitudTaller identificador único de la solicitud
     */
    public void setIdSolicitudTaller(int idSolicitudTaller) { this.idSolicitudTaller = idSolicitudTaller; }

    /**
     * Obtiene el ID del agricultor.
     * @return el ID del agricultor
     */
    public int getIdAgricultor() { return idAgricultor; }

    /**
     * Establece el ID del agricultor.
     * @param idAgricultor identificador del agricultor
     */
    public void setIdAgricultor(int idAgricultor) { this.idAgricultor = idAgricultor; }

    /**
     * Obtiene el nombre del agricultor.
     * @return el nombre del agricultor
     */
    public String getNombreAgricultor() { return nombreAgricultor; }

    /**
     * Establece el nombre del agricultor.
     * @param nombreAgricultor el nombre del agricultor
     */
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

    /**
     * Obtiene el ID del taller.
     * @return el ID del taller
     */
    public int getIdTaller() { return idTaller; }

    /**
     * Establece el ID del taller.
     * @param idTaller identificador del taller
     */
    public void setIdTaller(int idTaller) { this.idTaller = idTaller; }

    /**
     * Obtiene la fecha y hora en que se realizó la solicitud.
     * @return fecha y hora de la solicitud
     */
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }

    /**
     * Establece la fecha y hora en que se realizó la solicitud.
     * @param fechaSolicitud fecha y hora de la solicitud
     */
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    /**
     * Obtiene la fecha programada para el taller.
     * @return la fecha del taller
     */
    public LocalDate getFechaAplicarTaller() { return fechaAplicarTaller; }

    /**
     * Establece la fecha programada para el taller.
     * @param fechaAplicarTaller fecha programada
     */
    public void setFechaAplicarTaller(LocalDate fechaAplicarTaller) { this.fechaAplicarTaller = fechaAplicarTaller; }

    /**
     * Obtiene la dirección donde se realizará el taller.
     * @return la dirección del taller
     */
    public String getDireccion() { return direccion; }

    /**
     * Establece la dirección donde se realizará el taller.
     * @param direccion dirección del taller
     */
    public void setDireccion(String direccion) { this.direccion = direccion; }

    /**
     * Obtiene los comentarios adicionales de la solicitud.
     * @return comentarios de la solicitud
     */
    public String getComentario() { return comentario; }

    /**
     * Establece comentarios adicionales de la solicitud.
     * @param comentario comentarios de la solicitud
     */
    public void setComentario(String comentario) { this.comentario = comentario; }

    /**
     * Obtiene el estado actual de la solicitud.
     * @return el estado de la solicitud
     */
    public int getIdEstado() { return idEstado; }

    /**
     * Establece el estado actual de la solicitud.
     * @param idEstado estado de la solicitud
     */
    public void setIdEstado(int idEstado) { this.idEstado = idEstado; }

    /**
     * Obtiene la imagen del comprobante de pago.
     * @return la imagen del comprobante en base64
     */
    public String getEstadoPagoImagen() { return estadoPagoImagen; }

    /**
     * Establece la imagen del comprobante de pago.
     * @param estadoPagoImagen imagen en base64
     */
    public void setEstadoPagoImagen(String estadoPagoImagen) { this.estadoPagoImagen = estadoPagoImagen; }

    /**
     * Obtiene la fecha de finalización del taller.
     * @return la fecha de finalización
     */
    public LocalDate getFechaFin() { return fechaFin; }

    /**
     * Establece la fecha de finalización del taller.
     * @param fechaFin fecha de finalización
     */
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}