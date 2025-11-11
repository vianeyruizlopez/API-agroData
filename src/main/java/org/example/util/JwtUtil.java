package org.example.util;

import io.jsonwebtoken.*;
import org.example.model.Usuario;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "clave_super_secreta_muy_segura_de_32_bytes";

    // Genera el token JWT con claims seguros
    public static String generarToken(Usuario usuario) {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        int idSeguro = usuario.getIdUsuario();
        int rolSeguro = usuario.getRol();

        System.out.println("Generando token → ID: " + idSeguro + " (tipo: " + tipo(idSeguro) + "), ROL: " + rolSeguro + " (tipo: " + tipo(rolSeguro) + ")");

        String token = Jwts.builder()
                .setSubject(usuario.getCorreo())
                .claim("id", idSeguro)
                .claim("rol", rolSeguro)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 259200000)) // 3 dias
                .signWith(key)
                .compact();

        System.out.println("Token generado: " + token);
        return token;
    }

    public static Claims validarToken(String token) throws JwtException {
        System.out.println("→ Iniciando validación del token...");
        System.out.println("Token recibido: " + token);

        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build();

        Claims claims = parser.parseClaimsJws(token).getBody();
        System.out.println("Claims completos desde token: " + claims);

        Object rawId = claims.get("id");
        Object rawRol = claims.get("rol");

        System.out.println("Raw ID desde token: " + rawId + " (tipo: " + tipo(rawId) + ")");
        System.out.println("Raw ROL desde token: " + rawRol + " (tipo: " + tipo(rawRol) + ")");

        int id = extraerEnteroSeguro(rawId);
        int rol = extraerEnteroSeguro(rawRol);

        claims.put("id", id);
        claims.put("rol", rol);

        System.out.println("Token validado correctamente");
        System.out.println("Usuario ID extraído (int): " + id);
        System.out.println("Rol extraído (int): " + rol);

        return claims;
    }
    // Convierte cualquier tipo numérico o cadena a entero seguro
    public static int extraerEnteroSeguro(Object valor) {
        System.out.println("Intentando extraer entero de: " + valor + " (tipo: " + tipo(valor) + ")");
        if (valor instanceof Integer) {
            return (Integer) valor;
        } else if (valor instanceof Number) {
            return ((Number) valor).intValue();
        } else if (valor instanceof String) {
            try {
                return Integer.parseInt(((String) valor).trim());
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir a entero: " + valor);
            }
        } else {
            System.out.println("Tipo inesperado para conversión a entero: " + tipo(valor));
        }
        return -1;
    }

    // Devuelve el tipo de dato como texto para trazabilidad
    private static String tipo(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }
}