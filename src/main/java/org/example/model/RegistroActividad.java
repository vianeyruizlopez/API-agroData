package org.example.model;

public class RegistroActividad {
    int idRegistro;
    int idTarea;
    String imagen;
    String descripcion;

     public void setIdRegistro(int idRegistro) {
         this.idRegistro = idRegistro;
     }
     public int getIdRegistro() {
        return idRegistro;
    }
    public void setIdTarea(int idTarea) {
         this.idTarea = idTarea;
     }
    public int getIdTarea() {
        return idTarea;}

    public String getImagen() {
         return imagen;
    }
    public void setImagen(String imagen) {
         this.imagen = imagen;
    }

    public String getDescripcion() {
         return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void agregarActividad(RegistroActividad registroActividad) {

    }
}
