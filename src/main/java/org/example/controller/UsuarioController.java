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

/**
 * Controlador que maneja las peticiones HTTP relacionadas con usuarios.
 * Incluye registro, login, perfil y administración de clientes.
 */
public class UsuarioController {

    private final IUsuarioService service;

    /**
     * Constructor que recibe el servicio de usuarios.
     * @param service el servicio para manejar la lógica de usuarios
     */
    public UsuarioController(IUsuarioService service) {
        this.service = service;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * <p><strong>Endpoint:</strong> POST /registro</p>
     * <p><strong>Acceso:</strong> Público</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * POST /registro
     * Content-Type: application/json
     * 
     * {
     *   "nombre": "Juan",
     *   "apellidoPaterno": "Pérez",
     *   "apellidoMaterno": "García",
     *   "telefono": "1234567890",
     *   "correo": "juan.perez@email.com",
     *   "password": "password123",
     *   "rol": 2
     * }
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>201 Created: "Usuario registrado"</li>
     *   <li>400 Bad Request: Error de validación</li>
     *   <li>500 Internal Server Error: Error de base de datos</li>
     * </ul>
     * 
     * @param ctx el contexto de la petición HTTP
     */
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

    /**
     * Autentica un usuario con correo y contraseña.
     * 
     * <p><strong>Endpoint:</strong> POST /login</p>
     * <p><strong>Acceso:</strong> Público</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * POST /login
     * Content-Type: application/x-www-form-urlencoded
     * 
     * correo=juan.perez@email.com&password=password123
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta exitosa:</strong></p>
     * <pre>
     * HTTP/1.1 200 OK
     * {
     *   "mensaje": "Login exitoso",
     *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     *   "rol": 2,
     *   "id": 1
     * }
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>200 OK: Login exitoso con token JWT</li>
     *   <li>400 Bad Request: Faltan parámetros</li>
     *   <li>401 Unauthorized: Contraseña incorrecta</li>
     *   <li>404 Not Found: Correo no registrado</li>
     * </ul>
     * 
     * @param ctx el contexto de la petición HTTP
     */
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

    /**
     * Encripta una contraseña usando BCrypt.
     * Útil para generar hashes de contraseñas.
     * @param ctx el contexto de la petición HTTP
     */
    public void encriptarPassword(Context ctx) {
        String password = ctx.formParam("password");
        if (password == null || password.isEmpty()) {
            ctx.status(400).result("Debes enviar el parámetro 'password'");
            return;
        }
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        ctx.status(200).result("Contraseña encriptada: " + hash);
    }

    /**
     * Edita el perfil de un usuario.
     * Solo permite editar el propio perfil del usuario autenticado.
     * @param ctx el contexto de la petición HTTP
     */
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

    /**
     * Cambia la contraseña de un usuario.
     * Recibe el correo y la nueva contraseña.
     * @param ctx el contexto de la petición HTTP
     */
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

    /**
     * Obtiene los datos del perfil de un usuario.
     * 
     * <p><strong>Endpoint:</strong> GET /perfil/{id}</p>
     * <p><strong>Acceso:</strong> Requiere autenticación (solo propio perfil)</p>
     * 
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>
     * GET /perfil/1
     * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     * </pre>
     * 
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>
     * HTTP/1.1 200 OK
     * {
     *   "idUsuario": 1,
     *   "nombre": "Juan",
     *   "apellidoPaterno": "Pérez",
     *   "apellidoMaterno": "García",
     *   "telefono": "1234567890",
     *   "correo": "juan.perez@email.com",
     *   "imagenPerfil": null,
     *   "rol": 2
     * }
     * </pre>
     * 
     * <p><strong>Respuestas:</strong></p>
     * <ul>
     *   <li>200 OK: Datos del perfil</li>
     *   <li>403 Forbidden: Sin permisos para ver este perfil</li>
     *   <li>404 Not Found: Usuario no encontrado</li>
     * </ul>
     * 
     * @param ctx el contexto de la petición HTTP
     */
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

    /**
     * Obtiene información general del sistema para el dashboard.
     * Solo accesible para agrónomos (rol 1).
     * @param ctx el contexto de la petición HTTP
     */
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
    /**
     * Obtiene la lista de todos los clientes registrados.
     * Solo accesible para agrónomos (rol 1).
     * @param ctx el contexto de la petición HTTP
     */
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
    /**
     * Elimina un cliente del sistema.
     * Solo accesible para agrónomos (rol 1).
     * @param ctx el contexto de la petición HTTP
     */
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
    /**
     * Extrae el ID del usuario del token JWT.
     * @param ctx el contexto de la petición HTTP
     * @return el ID del usuario
     */
    private int extraerUsuarioId(Context ctx) {
        Object rawId = ctx.attribute("usuarioId");
        return JwtUtil.extraerEnteroSeguro(rawId);
    }

    /**
     * Extrae el rol del usuario del token JWT.
     * @param ctx el contexto de la petición HTTP
     * @return el rol del usuario
     */
    private int extraerRol(Context ctx) {
        Object rawRol = ctx.attribute("rol");
        return JwtUtil.extraerEnteroSeguro(rawRol);
    }
    /**
     * Edita los datos de un cliente.
     * Solo accesible para agrónomos (rol 1).
     * @param ctx el contexto de la petición HTTP
     */
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