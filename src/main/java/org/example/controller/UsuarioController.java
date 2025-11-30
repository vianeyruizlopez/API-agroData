package org.example.controller;

import io.javalin.http.Context;
import org.example.model.InformacionGeneral;
import org.example.model.Usuario;
import org.example.model.administrarCliente;
import org.example.service.IUsuarioService;
import org.example.service.UsuarioService;
import org.example.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsuarioController {

    private final IUsuarioService service;

    public UsuarioController(IUsuarioService service) {
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
            int idToken = JwtUtil.extraerEnteroSeguro(ctx.attribute("usuarioId")); //

            if (idRuta != idToken) {
                ctx.status(403).result("No tienes permiso para editar este perfil");
                return;
            }

            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            usuario.setIdUsuario(idRuta);


            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                String hashed = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
                usuario.setPassword(hashed);
            } else {

                Optional<Usuario> usuarioExistente = service.obtenerPorId(idRuta);


                if (usuarioExistente.isPresent()) {

                    usuario.setPassword(usuarioExistente.get().getPassword());
                } else {

                    ctx.status(404).result("Usuario no encontrado para actualizar");
                    return;
                }
            }

            service.editarPerfil(usuario);
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
            int idToken = JwtUtil.extraerEnteroSeguro(ctx.attribute("usuarioId"));

            if (idRuta != idToken) {
                ctx.status(403).result("No tienes permiso para ver este perfil");
                return;
            }

            Optional<Usuario> usuarioOpt = service.obtenerPorId(idRuta);
            if (usuarioOpt.isPresent()) {
                ctx.json(usuarioOpt.get());
            } else {
                ctx.status(404).result("Usuario no encontrado con ID " + idRuta);
            }
        } catch (SQLException e) {
            ctx.status(500).result("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno: " + e.getMessage());
        }
    }

    public void obtenerInformacionGeneral(Context ctx) {
        try {

            Object rawRol = ctx.attribute("rol");
            String tipoRol = rawRol != null ? rawRol.getClass().getSimpleName() : "null";
            System.out.println("ROL recibido en controlador (raw): " + rawRol + " (tipo: " + tipoRol + ")");


            int rol = JwtUtil.extraerEnteroSeguro(rawRol);
            System.out.println("ROL convertido en controlador: " + rol);


            if (rol != 1) {
                ctx.status(403).result("Acceso denegado: solo el agrónomo puede ver todas las solicitudes");
                return;
            }


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
    public void verTodosClientes(Context ctx) {
        try {
            Object rawRol = ctx.attribute("rol");
            String tipoRol = rawRol != null ? rawRol.getClass().getSimpleName() : "null";
            System.out.println("ROL recibido en controlador (raw): " + rawRol + " (tipo: " + tipoRol + ")");

            int rol = JwtUtil.extraerEnteroSeguro(rawRol);
            System.out.println("ROL convertido en controlador: " + rol);

            if (rol != 1) {
                ctx.status(403).result("Acceso denegado: solo el agrónomo puede ver todas las solicitudes");
                return;
            }

            List<administrarCliente> verTodosClientesList = service.verTodosClientes();
            ctx.json(verTodosClientesList);

        } catch (SQLException e) {
            ctx.status(500).result("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno: " + e.getMessage());
        }
    }
    public void eliminarClientes(Context ctx) {
        int usuarioId = extraerUsuarioId(ctx);
        int rol = extraerRol(ctx);
        System.out.println("→ Entrando a eliminar | usuarioId: " + usuarioId + ", rol: " + rol);

        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo agrónomo puede eliminar clientes");
            return;
        }

        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            service.eliminarClientes(id);
            ctx.json(Map.of("mensaje", "Cliente eliminado", "id", id));
        } catch (IllegalArgumentException e) {
            ctx.status(404).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno: " + e.getMessage());
        }
    }
    private int extraerUsuarioId(Context ctx) {
        Object rawId = ctx.attribute("usuarioId");
        return JwtUtil.extraerEnteroSeguro(rawId);
    }

    private int extraerRol(Context ctx) {
        Object rawRol = ctx.attribute("rol");
        return JwtUtil.extraerEnteroSeguro(rawRol);
    }
    public void editarClientes(Context ctx) {
        int usuarioId = extraerUsuarioId(ctx);
        int rol = extraerRol(ctx);
        System.out.println("→ Entrando a actualizar | usuarioId: " + usuarioId + ", rol: " + rol);

        if (rol != 1) {
            ctx.status(403).result("Acceso denegado: solo agrónomo puede editar clientes");
            return;
        }

        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            administrarCliente editarClientes = ctx.bodyAsClass(administrarCliente.class);
            service.editarClientes(id, editarClientes);
            ctx.status(200).json(editarClientes);
        } catch (IllegalArgumentException e) {
            ctx.status(404).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno: " + e.getMessage());
        }
    }
}