/**
 * Configuración central del sistema AgroData.
 * <p>
 * Este paquete contiene las clases responsables de inicializar y configurar
 * los componentes clave del sistema, incluyendo:
 * <ul>
 *   <li>Conexión a la base de datos (DataSource, EntityManager, etc.).</li>
 *   <li>Lectura y adaptación de variables de entorno.</li>
 *   <li>Definición de beans personalizados para el contexto de Spring.</li>
 * </ul>
 * <p>
 * Su propósito es garantizar que el entorno de ejecución esté correctamente
 * parametrizado, facilitando la trazabilidad, reversibilidad y estabilidad
 * de la configuración en todo el proyecto.
 */
package org.example.config;