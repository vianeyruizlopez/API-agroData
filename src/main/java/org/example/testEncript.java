package org.example;

import org.mindrot.jbcrypt.BCrypt;

public class testEncript {
    public static void main(String[] args) {
        // La lógica va DIRECTAMENTE dentro del main
        String password = "123456";

        // Genera el salt y crea el hash
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());

        // Imprime el resultado en la consola
        System.out.println("Contraseña encriptada: " + hash);
    }
}