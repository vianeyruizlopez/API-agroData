/**
 * Controladores REST del sistema AgroData.
 * <p>
 * Este paquete agrupa todas las clases que exponen endpoints HTTP para la interacción
 * con clientes externos (front-end, aplicaciones móviles, integraciones).
 * <p>
 * Cada controlador está especializado en una funcionalidad del sistema agropecuario,
 * como gestión de cultivos, usuarios, tareas, talleres, asesorías y reportes.
 * <p>
 * Los controladores reciben las solicitudes, validan los datos de entrada y delegan
 * la lógica a la capa de servicio correspondiente, manteniendo una arquitectura limpia
 * y trazable.
 *
 * <p>Controladores incluidos:
 * <ul>
 *   <li>{@code CatalogoCultivoController}</li>
 *   <li>{@code EstadoController}</li>
 *   <li>{@code NotificacionController}</li>
 *   <li>{@code ProyectosController}</li>
 *   <li>{@code RegistroActividadController}</li>
 *   <li>{@code ReporteDesempenoController}</li>
 *   <li>{@code SolicitudAsesoriaController}</li>
 *   <li>{@code SolicitudTallerController}</li>
 *   <li>{@code TallerController}</li>
 *   <li>{@code TareaController}</li>
 *   <li>{@code TipoTerrenoController}</li>
 *   <li>{@code UsuarioController}</li>
 * </ul>
 */
package org.example.controller;