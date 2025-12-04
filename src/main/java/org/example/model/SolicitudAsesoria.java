package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase que representa una solicitud de asesoría agrícola.
 * Los agricultores envían estas solicitudes para recibir ayuda de agrónomos.
 */
public class SolicitudAsesoria {

    /**
     * Identificador único de la solicitud de asesoría.
     */
    private int idSolicitud;

    /**
     * Identificador del agricultor que realiza la solicitud.
     */
    private int idAgricultor;

    /**
     * Nombre completo del agricultor que solicita la asesoría.
     */
    private String nombreAgricultor;

    /**
     * Fecha y hora en que se generó la solicitud.
     * Se serializa en formato ISO con patrón yyyy-MM-dd'T'HH:mm:ss.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaSolicitud;

    /**
     * Indica si el agricultor requiere uso de maquinaria.
     */
    private boolean usoMaquinaria;

    /**
     * Nombre de la maquinaria solicitada.
     */
    private String nombreMaquinaria;

    /**
     * Tipo de riego solicitado (identificador).
     */
    private int tipoRiego;

    /**
     * Nombre del sistema de riego solicitado.
     */
    private String nombreRiego;

    /**
     * Indica si el terreno presenta plagas.
     */
    private boolean tienePlaga;

    /**
     * Descripción de la plaga encontrada en el terreno.
     */
    private String descripcionPlaga;

    /**
     * Superficie total del terreno en hectáreas.
     */
    private float superficieTotal;

    /**
     * Dirección del terreno donde se solicita la asesoría.
     */
    private String direccionTerreno;

    /**
     * Motivo principal por el cual se solicita la asesoría.
     */
    private String motivoAsesoria;

    /**
     * Identificador del estado actual de la solicitud.
     */
    private int idEstado;

    /**
     * Lista de cultivos asociados a la solicitud.
     */
    private List<CultivoPorSolicitud> cultivos;

    /**
     * Obtiene el nombre completo del agricultor.
     * @return el nombre del agricultor
     */
    public String getNombreAgricultor() { return nombreAgricultor; }

    /**
     * Establece el nombre del agricultor.
     * @param nombreAgricultor el nombre del agricultor
     */
    public void setNombreAgricultor(String nombreAgricultor) { this.nombreAgricultor = nombreAgricultor; }

    /**
     * Constructor vacío para crear una solicitud sin datos.
     */
    public SolicitudAsesoria() {}

    /**
     * Obtiene el ID de la solicitud.
     * @return el ID de la solicitud
     */
    public int getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Establece el ID de la solicitud.
     * @param idSolicitud el ID de la solicitud
     */
    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Obtiene el ID del agricultor que hizo la solicitud.
     * @return el ID del agricultor
     */
    public int getIdAgricultor() {
        return idAgricultor;
    }

    /**
     * Establece el ID del agricultor.
     * @param idAgricultor el ID del agricultor
     */
    public void setIdAgricultor(int idAgricultor) {
        this.idAgricultor = idAgricultor;
    }

    /**
     * Obtiene la fecha de la solicitud.
     * @return fecha y hora de la solicitud
     */
    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * Establece la fecha de la solicitud.
     * @param fechaSolicitud fecha y hora de la solicitud
     */
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * Verifica si el agricultor usa maquinaria.
     * @return true si usa maquinaria, false si no
     */
    public boolean isUsoMaquinaria() {
        return usoMaquinaria;
    }

    /**
     * Establece si el agricultor usa maquinaria.
     * @param usoMaquinaria indicador de uso de maquinaria
     */
    public void setUsoMaquinaria(boolean usoMaquinaria) {
        this.usoMaquinaria = usoMaquinaria;
    }

    /**
     * Obtiene el nombre de la maquinaria solicitada.
     * @return nombre de la maquinaria
     */
    public String getNombreMaquinaria() {
        return nombreMaquinaria;
    }

    /**
     * Establece el nombre de la maquinaria solicitada.
     * @param nombreMaquinaria nombre de la maquinaria
     */
    public void setNombreMaquinaria(String nombreMaquinaria) {
        this.nombreMaquinaria = nombreMaquinaria;
    }

    /**
     * Obtiene el tipo de riego solicitado.
     * @return identificador del tipo de riego
     */
    public int getTipoRiego() {
        return tipoRiego;
    }

    /**
     * Establece el tipo de riego solicitado.
     * @param tipoRiego identificador del tipo de riego
     */
    public void setTipoRiego(int tipoRiego) {
        this.tipoRiego = tipoRiego;
    }

    /**
     * Obtiene el nombre del sistema de riego.
     * @return nombre del riego
     */
    public String getNombreRiego() {
        return nombreRiego;
    }

    /**
     * Establece el nombre del sistema de riego.
     * @param nombreRiego nombre del riego
     */
    public void setNombreRiego(String nombreRiego) {
        this.nombreRiego = nombreRiego;
    }

    /**
     * Verifica si el terreno tiene plaga.
     * @return true si tiene plaga, false si no
     */
    public boolean isTienePlaga() {
        return tienePlaga;
    }

    /**
     * Establece si el terreno tiene plaga.
     * @param tienePlaga indicador de presencia de plaga
     */
    public void setTienePlaga(boolean tienePlaga) {
        this.tienePlaga = tienePlaga;
    }

    /**
     * Obtiene la descripción de la plaga.
     * @return descripción de la plaga
     */
    public String getDescripcionPlaga() {
        return descripcionPlaga;
    }

    /**
     * Establece la descripción de la plaga.
     * @param descripcionPlaga descripción de la plaga
     */
    public void setDescripcionPlaga(String descripcionPlaga) {
        this.descripcionPlaga = descripcionPlaga;
    }

    /**
     * Obtiene la superficie total del terreno en hectáreas.
     * @return la superficie en hectáreas
     */
    public float getSuperficieTotal() {
        return superficieTotal;
    }

    /**
     * Establece la superficie total del terreno.
     * @param superficieTotal superficie en hectáreas
     */
    public void setSuperficieTotal(float superficieTotal) {
        this.superficieTotal = superficieTotal;
    }

    /**
     * Obtiene la dirección del terreno.
     * @return dirección del terreno
     */
    public String getDireccionTerreno() {
        return direccionTerreno;
    }

    /**
     * Establece la dirección del terreno.
     * @param direccionTerreno dirección del terreno
     */
    public void setDireccionTerreno(String direccionTerreno) {
        this.direccionTerreno = direccionTerreno;
    }

    /**
     * Obtiene el motivo de la asesoría.
     * @return motivo de la asesoría
     */
    public String getMotivoAsesoria() {
        return motivoAsesoria;
    }

    /**
     * Establece el motivo de la asesoría.
     * @param motivoAsesoria motivo de la asesoría
     */
    public void setMotivoAsesoria(String motivoAsesoria) {
        this.motivoAsesoria = motivoAsesoria;
    }

    /**
     * Obtiene el ID del estado de la solicitud.
     * @return identificador del estado
     */
    public int getIdEstado() {
        return idEstado;
    }

    /**
     * Establece el ID del estado de la solicitud.
     * @param idEstado identificador del estado
     */
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * Obtiene la lista de cultivos incluidos en la solicitud.
     * @return lista de cultivos con sus cantidades
     */
    public List<CultivoPorSolicitud> getCultivos() {
        return cultivos;
    }

    /**
     * Establece la lista de cultivos incluidos en la solicitud.
     * @param cultivos lista de cultivos con sus cantidades
     */
    public void setCultivos(List<CultivoPorSolicitud> cultivos) {
        this.cultivos = cultivos;
    }
}