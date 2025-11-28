package org.example.controller;

import io.javalin.http.Context;
import org.example.model.InformacionGeneral;
import org.example.model.Usuario;
import org.example.service.UsuarioService;
import org.example.util.JwtUtil; // Importación necesaria
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    public void registrar(Context ctx) {
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            service.registrar(usuario);
            ctx.status(201).result("Usuario registrado");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno: " + e.getMessage());
        }
    }

    public void login(Context ctx) {
        try {
            String correo = ctx.formParam("correo");
            String password = ctx.formParam("password");

            if (correo == null || password == null) {
                ctx.status(400).result("Faltan parámetros");
                return;
            }

            Optional<Usuario> usuarioOpt = service.obtenerPorCorreo(correo);
            if (usuarioOpt.isPresent()) {
                Usuario u = usuarioOpt.get();
                boolean valido = BCrypt.checkpw(password, u.getPassword());
                if (valido) {
                    String token = JwtUtil.generarToken(u);
                    ctx.json(Map.of(
                            "mensaje", "Login exitoso",
                            "token", token,
                            "rol", u.getRol(),
                            "id", u.getIdUsuario()
                    ));
                } else {
                    ctx.status(401).result("Contraseña incorrecta");
                }
            } else {
                ctx.status(404).result("Correo no registrado");
            }
        } catch (SQLException e) {
            ctx.status(500).result("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno: " + e.getMessage());
        }
    }

    public void encriptarPassword(Context ctx) {
        String password = ctx.formParam("password");
        if (password == null || password.isEmpty()) {
            ctx.status(400).result("Debes enviar el parámetro 'password'");
            return;
        }
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        ctx.status(200).result("Contraseña encriptada: " + hash);
    }

    public void editarPerfil(Context ctx) {
        try {
            int idRuta = Integer.parseInt(ctx.pathParam("id"));
            // 🚨 CORRECCIÓN 1: Usar el método seguro para extraer el ID del token
            int idToken = JwtUtil.extraerEnteroSeguro(ctx.attribute("usuarioId")); //

            if (idRuta != idToken) {
                ctx.status(403).result("No tienes permiso para editar este perfil");
                return;
            }

            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            usuario.setIdUsuario(idRuta);

            // 1. Manejar la nueva contraseña
            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                String hashed = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
                usuario.setPassword(hashed);
            } else {
                // 2. Si no hay nueva contraseña, obtener la contraseña existente de la BD
                Optional<Usuario> usuarioExistente = service.obtenerPorId(idRuta); // Llama al service para obtener el hash actual

                // Si el usuario existe, usar su contraseña existente
                if (usuarioExistente.isPresent()) {
                    // ASIGNAMOS el hash existente al objeto 'usuario' que será enviado al repositorio
                    usuario.setPassword(usuarioExistente.get().getPassword());
                } else {
                    // Si por alguna razón no se encuentra (aunque debería estar autenticado)
                    ctx.status(404).result("Usuario no encontrado para actualizar");
                    return;
                }
            }

            service.editarPerfil(usuario); //
            ctx.status(200).result("Perfil actualizado con ID " + idRuta);
        } catch (IllegalArgumentException e) {
            ctx.status(400).result("Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno: " + e.getMessage());
        }
    }

    public void recuperarPassword(Context ctx) {
        try {
            String correo = ctx.formParam("correo");
            String nuevaPassword = ctx.formParam("nuevaPassword");

            service.recuperarPassword(correo, nuevaPassword);
            ctx.status(200).result("Contraseña actualizada correctamente");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error de base de datos: " + e.getMessage());
        }
    }

    public void obtenerPerfil(Context ctx) {
        try {
            int idRuta = Integer.parseInt(ctx.pathParam("id"));

            // Extraer ID del token usando el atributo que puso el middleware
            Object idAttr = ctx.attribute("usuarioId");
            int idToken = JwtUtil.extraerEnteroSeguro(idAttr);

            System.out.println("→ Solicitando perfil ID: " + idRuta + " | Token ID: " + idToken);

            // Validación de seguridad: El usuario solo puede ver su propio perfil
            // (Opcional: Si el agrónomo (rol 1) necesita ver perfiles de otros, agrega '|| rol == 1')
            if (idRuta != idToken) {
                System.out.println("⛔ Acceso denegado: ID ruta (" + idRuta + ") != ID token (" + idToken + ")");
                ctx.status(403).result("No tienes permiso para ver este perfil");
                return;
            }

            Optional<Usuario> usuarioOpt = service.obtenerPorId(idRuta);
            if (usuarioOpt.isPresent()) {
                Usuario u = usuarioOpt.get();
                // Por seguridad, no devolvemos la contraseña
                u.setPassword(null);
                ctx.json(u);
            } else {
                System.out.println("⚠️ Usuario no encontrado en BD: " + idRuta);
                ctx.status(404).result("Usuario no encontrado con ID " + idRuta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500).result("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error interno: " + e.getMessage());
        }
    }

    public void obtenerInformacionGeneral(Context ctx) {
        try {
            // Extraer el atributo "rol" desde el contexto
            Object rawRol = ctx.attribute("rol");
            String tipoRol = rawRol != null ? rawRol.getClass().getSimpleName() : "null";
            System.out.println("ROL recibido en controlador (raw): " + rawRol + " (tipo: " + tipoRol + ")");

            // Conversión segura a entero
            int rol = JwtUtil.extraerEnteroSeguro(rawRol);
            System.out.println("ROL convertido en controlador: " + rol);

            // Validación de permisos
            if (rol != 1) {
                ctx.status(403).result("Acceso denegado: solo el agrónomo puede ver todas las solicitudes");
                return;
            }

            // Obtener y devolver la información general
            List<InformacionGeneral> informacionGeneralList = service.obtenerInformacionGeneral();
            ctx.json(informacionGeneralList);

        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
            ctx.status(500).result("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            ctx.status(500).result("Error interno: " + e.getMessage());
        }
    }


    public void obtenerClientes(Context ctx) {
        Object rolAttr = ctx.attribute("rol");
        int rol = JwtUtil.extraerEnteroSeguro(rolAttr);

        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: Solo administradores pueden ver la lista de clientes.");
            return;
        }

        List<Usuario> clientes = service.obtenerTodosLosClientes();
        ctx.json(clientes);
    }

    public void eliminarCliente(Context ctx) {
        Object rolAttr = ctx.attribute("rol");
        int rol = JwtUtil.extraerEnteroSeguro(rolAttr);

        if (rol != 1) {
            ctx.status(403).result("Acceso denegado.");
            return;
        }

        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            service.eliminarCliente(id);
            ctx.status(200).result("Cliente eliminado correctamente");
        } catch (SQLException e) {

            ctx.status(500).result("No se puede eliminar el cliente porque tiene registros asociados (solicitudes, proyectos, etc).");
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        }
    }

    public void actualizarClienteAdmin(Context ctx) {
        Object rolAttr = ctx.attribute("rol");
        int rol = JwtUtil.extraerEnteroSeguro(rolAttr);

        if (rol != 1) {
            ctx.status(403).result("Acceso denegado.");
            return;
        }

        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Usuario usuarioEntrante = ctx.bodyAsClass(Usuario.class);
            usuarioEntrante.setIdUsuario(id);

            Optional<Usuario> usuarioActualOpt = service.obtenerPorId(id);

            if (usuarioActualOpt.isPresent()) {
                Usuario actual = usuarioActualOpt.get();

                if (usuarioEntrante.getPassword() == null || usuarioEntrante.getPassword().isEmpty()) {
                    usuarioEntrante.setPassword(actual.getPassword());
                }

                if (usuarioEntrante.getImagenPerfil() == null || usuarioEntrante.getImagenPerfil().isEmpty()) {
                    usuarioEntrante.setImagenPerfil(actual.getImagenPerfil());
                }

                usuarioEntrante.setRol(actual.getRol());

                service.editarPerfil(usuarioEntrante);
                ctx.status(200).result("Cliente actualizado");
            } else {
                ctx.status(404).result("Cliente no encontrado");
            }

        } catch (Exception e) {
            ctx.status(500).result("Error al actualizar: " + e.getMessage());
        }
    }
}