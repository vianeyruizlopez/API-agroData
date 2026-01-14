/**
 * Repositorios del sistema AgroData para acceder a la base de datos.
 * <p>
 * Este paquete contiene las clases e interfaces que permiten guardar, buscar,
 * actualizar y eliminar información de las entidades como cultivos,
 * usuarios, tareas y notificaciones.
 * <p>
 * Los repositorios simplifican el acceso a la base de datos, evitando que cada
 * clase tenga que escribir consultas SQL manuales. De esta forma, el código es
 * más claro, ordenado y fácil de mantener.
 * <p>
 * Algunas implementaciones incluyen lógica personalizada para consultas
 * específicas que no se pueden resolver con métodos básicos.
 *
 * <p>Ejemplos incluidos:
 * <ul>
 *   <li>{@code CatalogoCultivoRepository}, {@code EstadoRepository}, {@code NotificacionRepository} – repositorios estándar.</li>
 *   <li>{@code EstadoRepositoryImpl} – implementación con lógica personalizada.</li>
 * </ul>
 *
 * <p>
 * Este paquete conecta el modelo de datos con la base de datos de manera
 * organizada, ayudando a mantener la trazabilidad y facilitar el mantenimiento
 * del sistema.
 */
package org.example.repository;