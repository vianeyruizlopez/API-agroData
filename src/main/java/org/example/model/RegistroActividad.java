package org.example.model;

/**
 * Clase que representa el registro de una actividad agrícola.
 * Los agricultores registran sus actividades con imágenes y descripciones.
 */
public class RegistroActividad {
    int idRegistro;
    int idTarea;
    String imagen;
    String descripcion;

     /**
      * Establece el ID del registro de actividad.
      * @param idRegistro el ID del registro
      */
     public void setIdRegistro(int idRegistro) {
         this.idRegistro = idRegistro;
     }
     /**
      * Obtiene el ID del registro de actividad.
      * @return el ID del registro
      */
     public int getIdRegistro() {
        return idRegistro;
    }
    /**
     * Establece el ID de la tarea asociada.
     * @param idTarea el ID de la tarea
     */
    public void setIdTarea(int idTarea) {
         this.idTarea = idTarea;
     }
    /**
     * Obtiene el ID de la tarea asociada.
     * @return el ID de la tarea
     */
    public int getIdTarea() {
        return idTarea;}

    /**
     * Obtiene la imagen de la actividad.
     * @return la imagen en formato base64 o URL
     */
    public String getImagen() {
         return imagen;
    }
    /**
     * Establece la imagen de la actividad.
     * @param imagen la imagen en formato base64 o URL
     */
    public void setImagen(String imagen) {
         this.imagen = imagen;
    }

    /**
     * Obtiene la descripción de la actividad.
     * @return la descripción de la actividad
     */
    public String getDescripcion() {
         return descripcion;
    }

    /**
     * Establece la descripción de la actividad.
     * @param descripcion la descripción de la actividad
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Método para agregar una actividad al registro.
     * @param registroActividad la actividad a agregar
     */
    public void agregarActividad(RegistroActividad registroActividad) {

    }
}
