package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase que representa un plan de cultivo generado por un agrónomo.
 * Contiene toda la información del plan incluyendo fechas, cultivos y progreso.
 */
public class PlanCultivo {
    /**
     * Identificador único del plan de cultivo.
     */
    private int idPlan;
    /**
     * Identificador de la solicitud de asesoría agronómica a la que corresponde este plan.
     */
    private int idSolicitud;
    /**
     * Identificador del usuario (productor) al que pertenece el plan.
     */
    private int idUsuario;
    /**
     * Identificador del estado actual del plan (e.g., Pendiente, En Progreso, Completado).
     */
    private int idEstado;
    /**
     * Nombre del productor asociado a la solicitud.
     */
    private String nombre;
    /**
     * Apellido paterno del productor asociado a la solicitud.
     */
    private String apellidoPaterno;
    /**
     * Apellido materno del productor asociado a la solicitud.
     */
    private String apellidoMaterno;
    /**
     * Dirección o ubicación del terreno donde se implementará el plan de cultivo.
     */
    private String direccionTerreno;
    /**
     * Descripción del motivo por el cual el productor solicitó la asesoría.
     */
    private String motivoAsesoria;

    /**
     * Fecha de inicio programada para la ejecución del plan de cultivo.
     * El formato de serialización/deserialización es "yyyy-MM-dd".
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    /**
     * Superficie total del terreno destinada al plan de cultivo, generalmente en hectáreas.
     */
    private float superficieTotal;

    /**
     * Fecha de finalización estimada para el plan de cultivo.
     * El formato de serialización/deserialización es "yyyy-MM-dd".
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    /**
     * Notas o comentarios adicionales proporcionados por el agrónomo o el sistema.
     */
    private String observaciones;

    /**
     * Número total de tareas o actividades definidas en este plan de cultivo.
     */
    private int totalTareas;
    /**
     * Número de tareas que han sido marcadas como completadas por el productor.
     */
    private int totalTareasCompletas;
    /**
     * Porcentaje de avance del plan de cultivo, calculado en base a las tareas completadas / total de tareas.
     */
    private float porcentajeAvance;

    /**
     * Lista de los cultivos específicos que forman parte de este plan (e.g., maíz, frijol).
     */
    private List<CultivoPorSolicitud> cultivoPorSolicitud;

    /**
     * Lista de los reportes de plagas o enfermedades registrados durante la ejecución del plan.
     */
    private List<ReportePlaga> reportePlagas;

    /**
     * Constructor vacío para crear un plan de cultivo sin datos.
     */
    public PlanCultivo() {}

    /**
     * Obtiene el ID del plan de cultivo.
     * @return el ID del plan
     */
    public int getIdPlan() {
        return idPlan;
    }

    /**
     * Establece el ID del plan de cultivo.
     * @param idPlan el nuevo ID del plan
     */
    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }
    /**
     * Obtiene el ID de la solicitud de asesoría asociada.
     * @return el ID de la solicitud
     */
    public int getIdSolicitud() {
        return idSolicitud;
    }
    /**
     * Establece el ID de la solicitud de asesoría.
     * @param idSolicitud el nuevo ID de la solicitud
     */
    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    /**
     * Obtiene el ID del usuario (productor) asociado al plan.
     * @return el ID del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }
    /**
     * Establece el ID del usuario (productor).
     * @param idUsuario el nuevo ID del usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    /**
     * Obtiene el ID del estado actual del plan.
     * @return el ID del estado
     */
    public int getIdEstado() {
        return idEstado;
    }
    /**
     * Establece el ID del estado del plan.
     * @param idEstado el nuevo ID del estado
     */
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
    /**
     * Obtiene el nombre del productor.
     * @return el nombre del productor
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Establece el nombre del productor.
     * @param nombre el nuevo nombre del productor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Obtiene el apellido paterno del productor.
     * @return el apellido paterno del productor
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }
    /**
     * Establece el apellido paterno del productor.
     * @param apellidoPaterno el nuevo apellido paterno
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    /**
     * Obtiene el apellido materno del productor.
     * @return el apellido materno del productor
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
    /**
     * Establece el apellido materno del productor.
     * @param apellidoMaterno el nuevo apellido materno
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    /**
     * Obtiene la dirección o ubicación del terreno.
     * @return la dirección del terreno
     */
    public String getDireccionTerreno() {
        return direccionTerreno;
    }
    /**
     * Establece la dirección o ubicación del terreno.
     * @param direccionTerreno la nueva dirección del terreno
     */
    public void setDireccionTerreno(String direccionTerreno) {
        this.direccionTerreno = direccionTerreno;
    }
    /**
     * Obtiene el motivo de la asesoría solicitada.
     * @return el motivo de la asesoría
     */
    public String getMotivoAsesoria() {
        return motivoAsesoria;
    }
    /**
     * Establece el motivo de la asesoría.
     * @param motivoAsesoria el nuevo motivo de la asesoría
     */
    public void setMotivoAsesoria(String motivoAsesoria) {
        this.motivoAsesoria = motivoAsesoria;
    }
    /**
     * Obtiene la fecha de inicio programada del plan.
     * @return la fecha de inicio
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    /**
     * Establece la fecha de inicio del plan.
     * @param fechaInicio la nueva fecha de inicio
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la superficie total del terreno en hectáreas.
     * @return la superficie total
     */
    public float getSuperficieTotal() {
        return superficieTotal;
    }
    /**
     * Establece la superficie total del terreno en hectáreas.
     * @param superficieTotal la nueva superficie total
     */
    public void setSuperficieTotal(float superficieTotal) {
        this.superficieTotal = superficieTotal;
    }
    /**
     * Obtiene la fecha de finalización estimada del plan.
     * @return la fecha de finalización
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    /**
     * Establece la fecha de finalización del plan.
     * @param fechaFin la nueva fecha de finalización
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    /**
     * Obtiene las observaciones o notas del plan.
     * @return las observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }
    /**
     * Establece las observaciones o notas del plan.
     * @param observaciones las nuevas observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    /**
     * Obtiene el número total de tareas definidas en el plan.
     * @return el total de tareas
     */
    public int getTotalTareas() {
        return totalTareas;
    }
    /**
     * Establece el número total de tareas.
     * @param totalTareas el nuevo total de tareas
     */
    public void setTotalTareas(int totalTareas) {
        this.totalTareas = totalTareas;
    }
    /**
     * Obtiene el número de tareas que han sido marcadas como completas.
     * @return el total de tareas completas
     */
    public int getTotalTareasCompletas() {
        return totalTareasCompletas;
    }

    /**
     * Establece el número de tareas completas.
     * @param totalTareasCompletas el nuevo total de tareas completas
     */
    public void setTotalTareasCompletas(int totalTareasCompletas) {
        this.totalTareasCompletas = totalTareasCompletas;
    }

    /**
     * Obtiene el porcentaje de avance del plan.
     * @return el porcentaje de avance (0-100)
     */
    public float getPorcentajeAvance() {
        return porcentajeAvance;
    }

    /**
     * Establece el porcentaje de avance del plan.
     * @param porcentajeAvance el nuevo porcentaje de avance
     */
    public void setPorcentajeAvance(float porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    /**
     * Obtiene la lista de cultivos del plan.
     * @return lista de cultivos asociados al plan
     */
    public List<CultivoPorSolicitud> getCultivoPorSolicitud() {
        return cultivoPorSolicitud;
    }
    /**
     * Establece la lista de cultivos asociados al plan.
     * @param cultivoPorSolicitud la nueva lista de cultivos
     */
    public void setCultivoPorSolicitud(List<CultivoPorSolicitud> cultivoPorSolicitud) {
        this.cultivoPorSolicitud = cultivoPorSolicitud;
    }

    /**
     * Obtiene la lista de reportes de plaga del plan.
     * @return lista de reportes de plaga
     */
    public List<ReportePlaga> getReportePlagas() {
        return reportePlagas;
    }

    /**
     * Establece la lista de reportes de plaga.
     * @param reportePlagas la nueva lista de reportes de plaga
     */
    public void setReportePlagas(List<ReportePlaga> reportePlagas) {
        this.reportePlagas = reportePlagas;
    }

    /**
     * Convierte el plan de cultivo a una representación de texto.
     * @return cadena con toda la información del plan
     */
    @Override
    public String toString() {
        return "PlanCultivo{" +
                "idPlan=" + idPlan +
                ", idSolicitud=" + idSolicitud +
                ", idUsuario=" + idUsuario +
                ", idEstado=" + idEstado +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", direccionTerreno='" + direccionTerreno + '\'' +
                ", motivoAsesoria='" + motivoAsesoria + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", superficieTotal=" + superficieTotal +
                ", fechaFin=" + fechaFin +
                ", observaciones='" + observaciones + '\'' +
                ", totalTareas=" + totalTareas +
                ", totalTareasCompletas=" + totalTareasCompletas +
                ", porcentajeAvance=" + porcentajeAvance +
                ", cultivoPorSolicitud=" + cultivoPorSolicitud +
                ", reportePlagas=" + reportePlagas +
                '}';
    }
}

