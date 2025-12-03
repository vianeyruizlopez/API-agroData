package org.example.model;

/**
 * Clase que representa un usuario del sistema AgroData.
 * Extiende de administrarCliente y agrega password y rol.
 * Puede ser agrónomo (rol 1) o agricultor (rol 2).
 */
public class Usuario extends administrarCliente {
    private String password;
    private int rol;

    /**
     * Constructor vacío para crear un usuario sin datos.
     */
    public Usuario() {
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return la contraseña encriptada
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     * @param password la contraseña a establecer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol del usuario.
     * @return 1 para agrónomo, 2 para agricultor
     */
    public int getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * @param rol 1 para agrónomo, 2 para agricultor
     */
    public void setRol(int rol) {
        this.rol = rol;
    }
}
