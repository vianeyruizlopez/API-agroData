# AgroData API REST

## Descripción
API REST para el sistema AgroData, una plataforma de gestión agrícola que permite la comunicación entre agrónomos y agricultores para el manejo de cultivos, asesorías, talleres y tareas agrícolas.

## Tecnologías
- **Framework**: Javalin 6.6.0
- **Base de datos**: MySQL 9.3.0
- **Autenticación**: JWT (JSON Web Tokens)
- **Encriptación**: BCrypt
- **Pool de conexiones**: HikariCP 5.1.0
- **Java**: 11+

## Configuración del Proyecto

### Requisitos Previos
- Java 11 o superior
- MySQL 8.0+
- Gradle 7.0+

### Instalación
```bash
# Clonar el repositorio
git clone <repository-url>
cd API-agroData-nueva-rama

# Compilar el proyecto
./gradlew build

# Ejecutar la aplicación
./gradlew run
```

### Variables de Entorno
Crear archivo `.env` en `src/main/resources/`:
```env
DB_URL=jdbc:mysql://localhost:3306/agrodata
DB_USER=your_username
DB_PASSWORD=your_password
JWT_SECRET=your_jwt_secret_key
```

## Servidor
- **URL Base**: `http://localhost:7000`
- **Puerto**: 7000
- **CORS**: Configurado para desarrollo local y producción

## Autenticación

### Sistema de Roles
- **Rol 1**: Agrónomo (Administrador)
- **Rol 2**: Agricultor (Cliente)

### Headers Requeridos
```http
Authorization: Bearer <jwt_token>
Content-Type: application/json
```

## Resumen de Endpoints

| Método | Endpoint | Descripción | Rol Requerido |
|--------|----------|-------------|---------------|
| POST | `/registro` | Registrar usuario | Público |
| POST | `/login` | Iniciar sesión | Público |
| GET | `/perfil/{id}` | Obtener perfil | Propio usuario |
| PUT | `/perfil/{id}` | Editar perfil | Propio usuario |
| POST | `/recuperar-password` | Recuperar contraseña | Público |
| POST | `/encriptar-password` | Encriptar contraseña | Público |
| GET | `/informacionGeneral` | Dashboard general | Agrónomo |
| POST | `/solicitudasesoria` | Crear solicitud asesoría | Agricultor |
| GET | `/solicitudasesoria` | Listar solicitudes asesoría | Agrónomo |
| GET | `/solicitudasesoria/{id}` | Obtener solicitud por ID | Agrónomo |
| PATCH | `/solicitudasesoria/{id}/{estado}` | Actualizar estado solicitud | Agrónomo |
| DELETE | `/solicitudasesoria/{id}` | Eliminar solicitud | Agrónomo |
| POST | `/tarea` | Crear tarea | Agrónomo |
| GET | `/tarea` | Listar tareas | Ambos |
| GET | `/tarea/{idTarea}` | Obtener tarea por ID | Ambos |
| PUT | `/tarea/{idTarea}` | Actualizar tarea completa | Agrónomo |
| PATCH | `/tarea/{id}/{estado}` | Actualizar estado tarea | Ambos |
| DELETE | `/tarea/{id}` | Eliminar tarea | Agrónomo |
| POST | `/reporteplaga` | Registrar reporte plaga | Agricultor |
| GET | `/tarea/conreporteplaga` | Tareas con reporte plaga | Ambos |
| GET | `/talleres/` | Listar talleres | Ambos |
| GET | `/talleres/{id}` | Obtener taller por ID | Ambos |
| POST | `/talleres/` | Crear taller | Agrónomo |
| PUT | `/talleres/{id}` | Actualizar taller | Agrónomo |
| PATCH | `/catalogotaller/{id}/{estado}` | Actualizar estado taller | Agrónomo |
| DELETE | `/talleres/{id}` | Eliminar taller | Agrónomo |
| POST | `/solicitudtaller` | Crear solicitud taller | Agricultor |
| GET | `/solicitudtaller` | Listar solicitudes taller | Agrónomo |
| GET | `/solicitudtaller/{id}` | Obtener solicitud taller | Agrónomo |
| GET | `/solicitudtaller/misolicitudes` | Mis solicitudes taller | Agricultor |
| PATCH | `/solicitudtaller/{id}/{estado}` | Actualizar estado | Ambos |
| PATCH | `/solicitudtaller/{id}/comprobante` | Subir comprobante | Agricultor |
| DELETE | `/solicitudtaller/{id}` | Eliminar solicitud taller | Agrónomo |
| GET | `/obtenerPlanCultivos` | Listar planes cultivo | Ambos |
| PATCH | `/planes/{idPlan}/estado/{idEstado}` | Actualizar estado plan | Agrónomo |
| PUT | `/planes/{idSolicitud}/{idPlan}` | Actualizar plan | Agrónomo |
| GET | `/notificacionesagronomo` | Notificaciones agrónomo | Agrónomo |
| GET | `/notificacionesagricultor` | Notificaciones agricultor | Agricultor |
| GET | `/catalogo/cultivos` | Catálogo cultivos | Público |
| GET | `/catalogo/estados` | Catálogo estados | Público |
| GET | `/catalogo/tipoterreno` | Catálogo tipos terreno | Público |
| GET | `/catalogo/tipoterreno/{id}` | Obtener tipo terreno por ID | Público |
| GET | `/catalogo/cultivos/{id}` | Obtener cultivo por ID | Público |
| GET | `/catalogo/estados/{id}` | Obtener estado por ID | Público |
| GET | `/getTallerForStatus/{idEstado}` | Talleres por estado | Ambos |
| GET | `/solicitudesTallerAsesoria` | Resumen solicitudes | Agrónomo |
| GET | `/registroactividades/` | Listar actividades | Ambos |
| POST | `/registroactividades/` | Registrar actividades | Agricultor |
| GET | `/obtenerReporteDesempeno/{idPlan}` | Obtener reporte desempeño | Agrónomo |
| POST | `/registrarReporteDesempeno` | Registrar reporte | Agrónomo |

## Endpoints de la API

###  Autenticación y Usuarios

#### Registro de Usuario
```http
POST /registro
Content-Type: application/json

{
  "nombre": "Juan",
  "apellidoPaterno": "Pérez",
  "apellidoMaterno": "García",
  "telefono": "1234567890",
  "correo": "juan.perez@email.com",
  "password": "password123",
  "rol": 2
}
```

**Respuesta:**
```http
HTTP/1.1 201 Created
Usuario registrado
```

#### Login
```http
POST /login
Content-Type: application/x-www-form-urlencoded

correo=juan.perez@email.com&password=password123
```

**Respuesta:**
```json
{
  "mensaje": "Login exitoso",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "rol": 2,
  "id": 1
}
```

#### Obtener Perfil
```http
GET /perfil/{id}
Authorization: Bearer <token>
```

**Respuesta:**
```json
{
  "idUsuario": 1,
  "nombre": "Juan",
  "apellidoPaterno": "Pérez",
  "apellidoMaterno": "García",
  "telefono": "1234567890",
  "correo": "juan.perez@email.com",
  "imagenPerfil": null,
  "rol": 2
}
```

#### Editar Perfil
```http
PUT /perfil/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "nombre": "Juan Carlos",
  "apellidoPaterno": "Pérez",
  "apellidoMaterno": "García",
  "telefono": "0987654321",
  "correo": "juan.carlos@email.com"
}
```

#### Recuperar Contraseña
```http
POST /recuperar-password
Content-Type: application/x-www-form-urlencoded

correo=juan.perez@email.com&nuevaPassword=newpassword123
```

#### Encriptar Contraseña
```http
POST /encriptar-password
Content-Type: application/x-www-form-urlencoded

password=mypassword123
```

**Respuesta:**
```http
HTTP/1.1 200 OK
Contraseña encriptada: $2a$10$JvqsjtaMf3kgQE2MwD29YujXp69PgrSwS1f6fIeGq6iMeZWzaoRZe
```

###  Solicitudes de Asesoría

#### Crear Solicitud (Solo Agricultores)
```http
POST /solicitudasesoria
Authorization: Bearer <token>
confirmado: true
Content-Type: application/json

{
  "idAgricultor": 1,
  "usoMaquinaria": true,
  "nombreMaquinaria": "Tractor John Deere",
  "tipoRiego": 1,
  "tienePlaga": false,
  "superficieTotal": 10.5,
  "direccionTerreno": "Parcela 123, Sector Norte",
  "motivoAsesoria": "Optimización de cultivo de maíz",
  "cultivos": [
    {
      "idCultivo": 1,
      "cantidad": 100
    }
  ]
}
```

#### Obtener Todas las Solicitudes (Solo Agrónomos)
```http
GET /solicitudasesoria
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "idSolicitud": 1,
    "idAgricultor": 1,
    "nombreAgricultor": "Juan Pérez García",
    "fechaSolicitud": "2024-01-15T10:30:00",
    "usoMaquinaria": true,
    "nombreMaquinaria": "Tractor John Deere",
    "tipoRiego": 1,
    "nombreRiego": "Goteo",
    "tienePlaga": false,
    "superficieTotal": 10.5,
    "direccionTerreno": "Parcela 123, Sector Norte",
    "motivoAsesoria": "Optimización de cultivo de chayote",
    "idEstado": 1,
    "cultivos": [
      {
        "idCultivo": 1,
        "nombreCultivo": "Chayote",
        "cantidad": 100
      }
    ]
  }
]
```

#### Obtener Solicitud por ID (Solo Agrónomos)
```http
GET /solicitudasesoria/{id}
Authorization: Bearer <token>
```

#### Actualizar Estado de Solicitud (Solo Agrónomos)
```http
PATCH /solicitudasesoria/{id}/{estado}
Authorization: Bearer <token>
```

#### Eliminar Solicitud (Solo Agrónomos)
```http
DELETE /solicitudasesoria/{id}
Authorization: Bearer <token>
```

###  Tareas

#### Crear Tarea (Solo Agrónomos)
```http
POST /tarea
Authorization: Bearer <token>
confirmado: true
Content-Type: application/json

{
  "idUsuario": 1,
  "titulo": "Preparación del terreno",
  "descripcion": "Arar y preparar el terreno para la siembra",
  "fechaInicio": "2024-01-20T08:00:00",
  "fechaFin": "2024-01-22T17:00:00",
  "prioridad": "Alta",
  "idEstado": 1
}
```

#### Obtener Todas las Tareas
```http
GET /tarea
Authorization: Bearer <token>
```

**Respuesta (Agrónomo ve todas, Agricultor solo las suyas):**
```json
[
  {
    "idTarea": 1,
    "idUsuario": 1,
    "titulo": "Preparación del terreno",
    "descripcion": "Arar y preparar el terreno para la siembra",
    "fechaInicio": "2024-01-20T08:00:00",
    "fechaFin": "2024-01-22T17:00:00",
    "prioridad": "Alta",
    "idEstado": 1,
    "nombreEstado": "Pendiente"
  }
]
```

#### Obtener Tarea por ID
```http
GET /tarea/{idTarea}
Authorization: Bearer <token>
```

#### Actualizar Tarea Completa (Solo Agrónomos)
```http
PUT /tarea/{idTarea}
Authorization: Bearer <token>
Content-Type: application/json

{
  "idTarea": 1,
  "idUsuario": 1,
  "titulo": "Preparación del terreno - Actualizada",
  "descripcion": "Arar, nivelar y preparar el terreno para la siembra",
  "fechaInicio": "2024-01-20T08:00:00",
  "fechaFin": "2024-01-23T17:00:00",
  "prioridad": "Media",
  "idEstado": 2
}
```

#### Actualizar Estado de Tarea
```http
PATCH /tarea/{id}/{estado}
Authorization: Bearer <token>
```

#### Eliminar Tarea (Solo Agrónomos)
```http
DELETE /tarea/{id}
Authorization: Bearer <token>
```

###  Reportes de Plaga

#### Registrar Reporte de Plaga (Solo Agricultores)
```http
POST /reporteplaga
Authorization: Bearer <token>
Content-Type: application/json

{
  "idUsuario": 1,
  "nombrePlaga": "Pulgón verde",
  "descripcion": "Presencia de pulgones en hojas de maíz",
  "nivelSeveridad": "Medio",
  "ubicacion": "Sector A, Parcela 5"
}
```

#### Obtener Tareas con Reporte de Plaga
```http
GET /tarea/conreporteplaga
Authorization: Bearer <token>
```

###  Talleres

#### Obtener Todos los Talleres
```http
GET /talleres/
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "idTaller": 4,
    "nombreTaller": "Control Orgánico de Plagas",
    "descripcion": "Métodos naturales y ecológicos para el control de plagas usando extractos vegetales, trampas biológicas y manejo integrado sin químicos dañinos para el medio ambiente.",
    "idEstado": 2,
    "costo": 900.00
  },
  {
    "idTaller": 5,
    "nombreTaller": "Fertilización Orgánica",
    "descripcion": "Técnicas de compostaje, lombricomposta y fertilización natural para mejorar la productividad del suelo usando residuos orgánicos y abonos verdes.",
    "idEstado": 4,
    "costo": 450.00
  },
  {
    "idTaller": 7,
    "nombreTaller": "Agricultura Sostenible",
    "descripcion": "Prácticas agrícolas que protegen el medio ambiente, conservan los recursos naturales y aumentan la rentabilidad a largo plazo de los cultivos.",
    "idEstado": 4,
    "costo": 1500.00
  }
]
```

#### Obtener Taller por ID
```http
GET /talleres/{id}
Authorization: Bearer <token>
```

#### Crear Taller (Solo Agrónomos)
```http
POST /talleres/
Authorization: Bearer <token>
Content-Type: application/json

{
  "nombreTaller": "Manejo Integrado de Plagas",
  "descripcion": "Estrategias para el control sostenible de plagas usando métodos naturales y ecológicos"
}
```

#### Actualizar Taller (Solo Agrónomos)
```http
PUT /talleres/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "nombreTaller": "Manejo Integrado de Plagas - Nivel Avanzado",
  "descripcion": "Estrategias avanzadas para el control sostenible de plagas usando métodos naturales y ecológicos"
}
```

#### Actualizar Estado de Taller (Solo Agrónomos)
```http
PATCH /catalogotaller/{id}/{estado}
Authorization: Bearer <token>
```

#### Eliminar Taller (Solo Agrónomos)
```http
DELETE /talleres/{id}
Authorization: Bearer <token>
```

###  Solicitudes de Taller

#### Crear Solicitud de Taller (Solo Agricultores)
```http
POST /solicitudtaller
Authorization: Bearer <token>
confirmado: true
Content-Type: application/json

{
  "idAgricultor": 5,
  "idTaller": 4,
  "fechaAplicarTaller": "2025-11-20",
  "fechaFin": "2025-11-27",
  "direccion": "San Juan Diquiyu",
  "comentario": "Necesito la capacitación para mis empleados"
}
```

#### Obtener Todas las Solicitudes de Taller (Solo Agrónomos)
```http
GET /solicitudtaller
Authorization: Bearer <token>
```

#### Obtener Solicitud de Taller por ID (Solo Agrónomos)
```http
GET /solicitudtaller/{id}
Authorization: Bearer <token>
```

#### Obtener Mis Solicitudes de Taller (Solo Agricultores)
```http
GET /solicitudtaller/misolicitudes
Authorization: Bearer <token>
```

#### Obtener Talleres por Estado
```http
GET /getTallerForStatus/{idEstado}
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "idTaller": 4,
    "nombreTaller": "Control Orgánico de Plagas",
    "descripcion": "Métodos naturales y ecológicos para el control de plagas",
    "idEstado": 2,
    "costo": 900.00
  }
]
```

#### Actualizar Estado de Solicitud de Taller
```http
PATCH /solicitudtaller/{id}/{estado}
Authorization: Bearer <token>
```

#### Subir Comprobante de Pago (Solo Agricultores)
```http
PATCH /solicitudtaller/{id}/comprobante
Authorization: Bearer <token>
Content-Type: application/json

{
  "imagen": "base64_encoded_image_data"
}
```

#### Eliminar Solicitud de Taller (Solo Agrónomos)
```http
DELETE /solicitudtaller/{id}
Authorization: Bearer <token>
```

#### Obtener Resumen de Solicitudes (Solo Agrónomos)
```http
GET /solicitudesTallerAsesoria
Authorization: Bearer <token>
```

###  Proyectos y Planes de Cultivo

#### Obtener Planes de Cultivo
```http
GET /obtenerPlanCultivos
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "idPlan": 1,
    "idSolicitud": 1,
    "objetivo": "Maximizar la producción de maíz en 10 hectáreas",
    "observaciones": "Terreno con buen drenaje, ideal para maíz",
    "fechaCreacion": "2024-01-15T10:30:00",
    "idEstado": 1,
    "nombreEstado": "En Planificación"
  }
]
```

#### Actualizar Estado del Plan (Solo Agrónomos)
```http
PATCH /planes/{idPlan}/estado/{idEstado}
Authorization: Bearer <token>
```

#### Actualizar Objetivo y Observaciones (Solo Agrónomos)
```http
PUT /planes/{idSolicitud}/{idPlan}
Authorization: Bearer <token>
Content-Type: application/json

{
  "objetivo": "Maximizar la producción de maíz orgánico en 10 hectáreas",
  "observaciones": "Implementar técnicas de agricultura orgánica certificada"
}
```

###  Notificaciones

#### Obtener Notificaciones del Agrónomo (Solo Agrónomos)
```http
GET /notificacionesagronomo
Authorization: Bearer <token>
```

#### Obtener Notificaciones del Agricultor (Solo Agricultores)
```http
GET /notificacionesagricultor
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "idNotificacion": 1,
    "idUsuario": 1,
    "titulo": "Nueva tarea asignada",
    "mensaje": "Se te ha asignado la tarea: Preparación del terreno",
    "fechaCreacion": "2024-01-20T08:00:00",
    "leida": false
  }
]
```

###  Catálogos

#### Obtener Catálogo de Cultivos
```http
GET /catalogo/cultivos
```

**Respuesta:**
```json
[
  {
    "idCultivo": 1,
    "nombreCultivo": "Chayote"
  },
  {
    "idCultivo": 2,
    "nombreCultivo": "Papa"
  },
  {
    "idCultivo": 3,
    "nombreCultivo": "Calabaza"
  }
]
```

#### Obtener Cultivo por ID
```http
GET /catalogo/cultivos/{id}
```

#### Obtener Catálogo de Estados
```http
GET /catalogo/estados
```

**Respuesta:**
```json
[
  {
    "idEstado": 1,
    "nombreEstado": "pendiente"
  },
  {
    "idEstado": 2,
    "nombreEstado": "aceptada"
  },
  {
    "idEstado": 3,
    "nombreEstado": "rechazada"
  },
  {
    "idEstado": 4,
    "nombreEstado": "revision"
  },
  {
    "idEstado": 5,
    "nombreEstado": "completado"
  },
  {
    "idEstado": 6,
    "nombreEstado": "atrasada"
  }
]
```

#### Obtener Estado por ID
```http
GET /catalogo/estados/{id}
```

#### Obtener Catálogo de Tipos de Terreno
```http
GET /catalogo/tipoterreno
```

**Respuesta:**
```json
[
  {
    "idTipoTerreno": 1,
    "nombre": "Arcilloso",
    "descripcion": "Terreno con alta concentración de arcilla"
  }
]
```

#### Obtener Tipo de Terreno por ID
```http
GET /catalogo/tipoterreno/{id}
```

###  Registro de Actividades

#### Obtener Registros de Actividad
```http
GET /registroactividades/
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "idRegistro": 1,
    "idUsuario": 1,
    "actividad": "Siembra de maíz",
    "fecha": "2024-01-20T08:00:00",
    "observaciones": "Siembra realizada en condiciones óptimas"
  }
]
```

#### Registrar Actividades (Solo Agricultores)
```http
POST /registroactividades/
Authorization: Bearer <token>
Content-Type: application/json

[
  {
    "idUsuario": 1,
    "actividad": "Riego del cultivo",
    "fecha": "2024-01-21T06:00:00",
    "observaciones": "Riego matutino completado"
  }
]
```

###  Reportes de Desempeño

#### Obtener Reporte de Desempeño (Solo Agrónomos)
```http
GET /obtenerReporteDesempeno/{idPlan}
Authorization: Bearer <token>
```

**Respuesta:**
```json
{
  "idReporte": 1,
  "idPlan": 1,
  "fechaGeneracion": "2024-01-25T10:00:00",
  "observaciones": "El plan se está ejecutando según lo esperado",
  "eficiencia": 85.5
}
```

#### Registrar Reporte de Desempeño (Solo Agrónomos)
```http
POST /registrarReporteDesempeno
Authorization: Bearer <token>
Content-Type: application/json

{
  "idPlan": 1,
  "fechaGeneracion": "2024-01-25T10:00:00",
  "observaciones": "Evaluación mensual del progreso del plan de cultivo"
}
```

###  Información General (Solo Agrónomos)

#### Obtener Dashboard de Información General
```http
GET /informacionGeneral
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "totalSolicitudes": 15,
    "solicitudesPendientes": 5,
    "solicitudesEnProceso": 7,
    "solicitudesCompletadas": 3,
    "totalTareas": 25,
    "tareasPendientes": 10,
    "tareasEnProceso": 8,
    "tareasCompletadas": 7
  }
] "Preparación del terreno - Actualizada",
  "descripcion": "Arar, nivelar y preparar el terreno para la siembra",
  "fechaInicio": "2024-01-20T08:00:00",
  "fechaFin": "2024-01-23T17:00:00",
  "prioridad": "Media",
  "idEstado": 2
}
```

#### Actualizar Estado de Tarea
```http
PATCH /tarea/{id}/{estado}
Authorization: Bearer <token>
```

#### Eliminar Tarea (Solo Agrónomos)
```http
DELETE /tarea/{id}
Authorization: Bearer <token>
```

###  Reportes de Plaga

#### Registrar Reporte de Plaga (Solo Agricultores)
```http
POST /reporteplaga
Authorization: Bearer <token>
Content-Type: application/json

{
  "idUsuario": 1,
  "nombrePlaga": "Pulgón verde",
  "descripcion": "Presencia de pulgones en hojas de maíz",
  "nivelSeveridad": "Medio",
  "ubicacion": "Sector A, Parcela 5"
}
```

#### Obtener Tareas con Reporte de Plaga
```http
GET /tarea/conreporteplaga
Authorization: Bearer <token>
```

###  Talleres

#### Obtener Todos los Talleres
```http
GET /talleres/
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "idTaller": 4,
    "nombreTaller": "Control Orgánico de Plagas",
    "descripcion": "Métodos naturales y ecológicos para el control de plagas usando extractos vegetales, trampas biológicas y manejo integrado sin químicos dañinos para el medio ambiente.",
    "idEstado": 2,
    "costo": 900.00
  },
  {
    "idTaller": 5,
    "nombreTaller": "Fertilización Orgánica",
    "descripcion": "Técnicas de compostaje, lombricomposta y fertilización natural para mejorar la productividad del suelo usando residuos orgánicos y abonos verdes.",
    "idEstado": 4,
    "costo": 450.00
  },
  {
    "idTaller": 7,
    "nombreTaller": "Agricultura Sostenible",
    "descripcion": "Prácticas agrícolas que protegen el medio ambiente, conservan los recursos naturales y aumentan la rentabilidad a largo plazo de los cultivos.",
    "idEstado": 4,
    "costo": 1500.00
  }
]
```

#### Obtener Taller por ID
```http
GET /talleres/{id}
Authorization: Bearer <token>
```

#### Crear Taller (Solo Agrónomos)
```http
POST /talleres/
Authorization: Bearer <token>
Content-Type: application/json

{
  "nombreTaller": "Manejo Integrado de Plagas",
  "descripcion": "Estrategias para el control sostenible de plagas usando métodos naturales y ecológicos"
}
```

#### Actualizar Taller (Solo Agrónomos)
```http
PUT /talleres/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "nombreTaller": "Manejo Integrado de Plagas - Nivel Avanzado",
  "descripcion": "Estrategias avanzadas para el control sostenible de plagas usando métodos naturales y ecológicos"
}
```

#### Actualizar Estado de Taller (Solo Agrónomos)
```http
PATCH /catalogotaller/{id}/{estado}
Authorization: Bearer <token>
```

#### Eliminar Taller (Solo Agrónomos)
```http
DELETE /talleres/{id}
Authorization: Bearer <token>
```

###  Proyectos y Planes de Cultivo

#### Obtener Planes de Cultivo
```http
GET /obtenerPlanCultivos
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "idPlan": 1,
    "idSolicitud": 1,
    "objetivo": "Maximizar la producción de maíz en 10 hectáreas",
    "observaciones": "Terreno con buen drenaje, ideal para maíz",
    "fechaCreacion": "2024-01-15T10:30:00",
    "idEstado": 1,
    "nombreEstado": "En Planificación"
  }
]
```

#### Actualizar Estado del Plan (Solo Agrónomos)
```http
PATCH /planes/{idPlan}/estado/{idEstado}
Authorization: Bearer <token>
```

#### Actualizar Objetivo y Observaciones (Solo Agrónomos)
```http
PUT /planes/{idSolicitud}/{idPlan}
Authorization: Bearer <token>
Content-Type: application/json

{
  "objetivo": "Maximizar la producción de maíz orgánico en 10 hectáreas",
  "observaciones": "Implementar técnicas de agricultura orgánica certificada"
}
```

###  Notificaciones

#### Obtener Notificaciones del Agrónomo (Solo Agrónomos)
```http
GET /notificacionesagronomo
Authorization: Bearer <token>
```

#### Obtener Notificaciones del Agricultor (Solo Agricultores)
```http
GET /notificacionesagricultor
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "idNotificacion": 1,
    "idUsuario": 1,
    "titulo": "Nueva tarea asignada",
    "mensaje": "Se te ha asignado la tarea: Preparación del terreno",
    "fechaCreacion": "2024-01-20T08:00:00",
    "leida": false
  }
]
```

###  Solicitudes de Taller

#### Crear Solicitud de Taller (Solo Agricultores)
```http
POST /solicitudtaller
Authorization: Bearer <token>
confirmado: true
Content-Type: application/json

{
  "idAgricultor": 5,
  "idTaller": 4,
  "fechaAplicarTaller": "2025-11-20",
  "fechaFin": "2025-11-27",
  "direccion": "San Juan Diquiyu",
  "comentario": "Necesito la capacitación para mis empleados"
}
```

#### Obtener Todas las Solicitudes de Taller (Solo Agrónomos)
```http
GET /solicitudtaller
Authorization: Bearer <token>
```

#### Obtener Solicitud de Taller por ID (Solo Agrónomos)
```http
GET /solicitudtaller/{id}
Authorization: Bearer <token>
```

#### Obtener Mis Solicitudes de Taller (Solo Agricultores)
```http
GET /solicitudtaller/misolicitudes
Authorization: Bearer <token>
```

#### Obtener Talleres por Estado
```http
GET /getTallerForStatus/{idEstado}
Authorization: Bearer <token>
```

#### Actualizar Estado de Solicitud de Taller
```http
PATCH /solicitudtaller/{id}/{estado}
Authorization: Bearer <token>
```

#### Subir Comprobante de Pago (Solo Agricultores)
```http
PATCH /solicitudtaller/{id}/comprobante
Authorization: Bearer <token>
Content-Type: application/json

{
  "imagen": "base64_encoded_image_data"
}
```

#### Eliminar Solicitud de Taller (Solo Agrónomos)
```http
DELETE /solicitudtaller/{id}
Authorization: Bearer <token>
```

#### Obtener Resumen de Solicitudes (Solo Agrónomos)
```http
GET /solicitudesTallerAsesoria
Authorization: Bearer <token>
```

#### Obtener Talleres por Estado
```http
GET /getTallerForStatus/{idEstado}
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "idTaller": 4,
    "nombreTaller": "Control Orgánico de Plagas",
    "descripcion": "Métodos naturales y ecológicos para el control de plagas",
    "idEstado": 2,
    "costo": 900.00
  }
]
```

###  Catálogos

#### Obtener Catálogo de Cultivos
```http
GET /catalogo/cultivos
```

**Respuesta:**
```json
[
  {
    "idCultivo": 1,
    "nombreCultivo": "Chayote"
  },
  {
    "idCultivo": 2,
    "nombreCultivo": "Papa"
  },
  {
    "idCultivo": 3,
    "nombreCultivo": "Calabaza"
  }
]
```

#### Obtener Cultivo por ID
```http
GET /catalogo/cultivos/{id}
```

#### Obtener Catálogo de Estados
```http
GET /catalogo/estados
```

**Respuesta:**
```json
[
  {
    "idEstado": 1,
    "nombreEstado": "pendiente"
  },
  {
    "idEstado": 2,
    "nombreEstado": "aceptada"
  },
  {
    "idEstado": 3,
    "nombreEstado": "rechazada"
  },
  {
    "idEstado": 4,
    "nombreEstado": "revision"
  },
  {
    "idEstado": 5,
    "nombreEstado": "completado"
  },
  {
    "idEstado": 6,
    "nombreEstado": "atrasada"
  }
]
```

#### Obtener Estado por ID
```http
GET /catalogo/estados/{id}
```

#### Obtener Catálogo de Tipos de Terreno
```http
GET /catalogo/tipoterreno
```

**Respuesta:**
```json
[
  {
    "idTipoTerreno": 1,
    "nombre": "Arcilloso",
    "descripcion": "Terreno con alta concentración de arcilla"
  }
]
```

#### Obtener Tipo de Terreno por ID
```http
GET /catalogo/tipoterreno/{id}
```

###  Registro de Actividades

#### Obtener Registros de Actividad
```http
GET /registroactividades/
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "idRegistro": 1,
    "idUsuario": 1,
    "actividad": "Siembra de maíz",
    "fecha": "2024-01-20T08:00:00",
    "observaciones": "Siembra realizada en condiciones óptimas"
  }
]
```

#### Registrar Actividades (Solo Agricultores)
```http
POST /registroactividades/
Authorization: Bearer <token>
Content-Type: application/json

[
  {
    "idUsuario": 1,
    "actividad": "Riego del cultivo",
    "fecha": "2024-01-21T06:00:00",
    "observaciones": "Riego matutino completado"
  }
]
```

###  Reportes de Desempeño

#### Obtener Reporte de Desempeño (Solo Agrónomos)
```http
GET /obtenerReporteDesempeno/{idPlan}
Authorization: Bearer <token>
```

**Respuesta:**
```json
{
  "idReporte": 1,
  "idPlan": 1,
  "fechaGeneracion": "2024-01-25T10:00:00",
  "observaciones": "El plan se está ejecutando según lo esperado",
  "eficiencia": 85.5
}
```

#### Registrar Reporte de Desempeño (Solo Agrónomos)
```http
POST /registrarReporteDesempeno
Authorization: Bearer <token>
Content-Type: application/json

{
  "idPlan": 1,
  "fechaGeneracion": "2024-01-25T10:00:00",
  "observaciones": "Evaluación mensual del progreso del plan de cultivo"
}
```

###  Información General (Solo Agrónomos)

#### Obtener Dashboard de Información General
```http
GET /informacionGeneral
Authorization: Bearer <token>
```

**Respuesta:**
```json
[
  {
    "totalSolicitudes": 15,
    "solicitudesPendientes": 5,
    "solicitudesEnProceso": 7,
    "solicitudesCompletadas": 3,
    "totalTareas": 25,
    "tareasPendientes": 10,
    "tareasEnProceso": 8,
    "tareasCompletadas": 7
  }
]
```

## Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 201 | Created - Recurso creado exitosamente |
| 204 | No Content - Operación exitosa sin contenido |
| 400 | Bad Request - Error en la solicitud |
| 401 | Unauthorized - Token inválido o faltante |
| 403 | Forbidden - Sin permisos para la operación |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error interno del servidor |

## Estados del Sistema

### Estados de Solicitudes/Tareas/Talleres
- **1**: Pendiente
- **2**: Aceptada
- **3**: Rechazada
- **4**: Revisión
- **5**: Completado
- **6**: Atrasada

### Tipos de Riego (Referencia BD)
- **1**: Goteo
- **2**: Aspersión
- **3**: Temporal

**Nota**: El endpoint actual es `/catalogo/tipoterreno` para tipos de terreno, no tipos de riego.

### Roles de Usuario
- **1**: Agrónomo
- **2**: Agricultor

### Catálogo de Cultivos
- **1**: Chayote
- **2**: Papa
- **3**: Calabaza

### Costos de Talleres (Ejemplos)
- **Taller 3**: $100.00
- **Control Orgánico de Plagas**: $900.00
- **Fertilización Orgánica**: $450.00
- **Manejo Post-Cosecha**: $1,050.00
- **Agricultura Sostenible**: $1,500.00

### Campos Importantes
- **confirmado**: Header requerido para crear solicitudes (true/false)
- **estadoPagoImagen**: Campo para almacenar comprobantes de pago en base64
- **fechaAplicarTaller**: Fecha programada para el taller
- **fechaCompletado**: Fecha de finalización de tareas
- **superficieTotal**: Área del terreno en hectáreas

## Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 201 | Created - Recurso creado exitosamente |
| 204 | No Content - Operación exitosa sin contenido |
| 400 | Bad Request - Error en la solicitud |
| 401 | Unauthorized - Token inválido o faltante |
| 403 | Forbidden - Sin permisos para la operación |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error interno del servidor |

## Estados del Sistema

### Estados de Solicitudes/Tareas/Talleres
- **1**: Pendiente
- **2**: Aceptada
- **3**: Rechazada
- **4**: Revisión
- **5**: Completado
- **6**: Atrasada

### Tipos de Riego (Referencia BD)
- **1**: Goteo
- **2**: Aspersión
- **3**: Temporal

**Nota**: El endpoint actual es `/catalogo/tipoterreno` para tipos de terreno, no tipos de riego.

### Roles de Usuario
- **1**: Agrónomo
- **2**: Agricultor

### Catálogo de Cultivos
- **1**: Chayote
- **2**: Papa
- **3**: Calabaza

### Costos de Talleres (Ejemplos)
- **Taller 3**: $100.00
- **Control Orgánico de Plagas**: $900.00
- **Fertilización Orgánica**: $450.00
- **Manejo Post-Cosecha**: $1,050.00
- **Agricultura Sostenible**: $1,500.00

### Campos Importantes
- **confirmado**: Header requerido para crear solicitudes (true/false)
- **estadoPagoImagen**: Campo para almacenar comprobantes de pago en base64
- **fechaAplicarTaller**: Fecha programada para el taller
- **fechaCompletado**: Fecha de finalización de tareas
- **superficieTotal**: Área del terreno en hectáreas

## Ejemplos de Uso con cURL

### Registro y Login
```bash
# Registrar usuario
curl -X POST http://localhost:7000/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "María",
    "apellidoPaterno": "González",
    "apellidoMaterno": "López",
    "telefono": "5551234567",
    "correo": "maria.gonzalez@email.com",
    "password": "password123",
    "rol": 2
  }'

# Login
curl -X POST http://localhost:7000/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "correo=maria.gonzalez@email.com&password=password123"
```

### Operaciones con Token
```bash
# Obtener perfil
curl -X GET http://localhost:7000/perfil/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Crear solicitud de taller
curl -X POST http://localhost:7000/solicitudtaller \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -H "confirmado: true" \
  -d '{
    "idAgricultor": 5,
    "idTaller": 4,
    "fechaAplicarTaller": "2025-11-20",
    "fechaFin": "2025-11-27",
    "direccion": "San Juan Diquiyu",
    "comentario": "Necesito la capacitación para mis empleados"
  }'

# Crear solicitud de asesoría
curl -X POST http://localhost:7000/solicitudasesoria \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -H "confirmado: true" \
  -d '{
    "idAgricultor": 1,
    "usoMaquinaria": true,
    "nombreMaquinaria": "Tractor Case",
    "tipoRiego": 1,
    "tienePlaga": false,
    "superficieTotal": 15.0,
    "direccionTerreno": "Parcela 456, Zona Sur",
    "motivoAsesoria": "Mejora de productividad",
    "cultivos": [{"idCultivo": 1, "cantidad": 150}]
  }'

# Crear solicitud de taller
curl -X POST http://localhost:7000/solicitudtaller \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -H "confirmado: true" \
  -d '{
    "idUsuario": 1,
    "idTaller": 1,
    "fechaSolicitud": "2024-01-20T10:00:00",
    "motivoSolicitud": "Interés en aprender nuevas técnicas"
  }'

# Obtener catálogo de cultivos (sin autenticación)
curl -X GET http://localhost:7000/catalogo/cultivos

# Obtener tipos de terreno
curl -X GET http://localhost:7000/catalogo/tipoterreno

# Encriptar contraseña
curl -X POST http://localhost:7000/encriptar-password \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "password=mypassword123"

# Obtener reporte de desempeño (solo agrónomos)
curl -X GET http://localhost:7000/obtenerReporteDesempeno/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Obtener talleres por estado
curl -X GET http://localhost:7000/getTallerForStatus/2 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Obtener resumen de solicitudes
curl -X GET http://localhost:7000/solicitudesTallerAsesoria \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Seguridad

### Autenticación JWT
- Los tokens JWT tienen una duración limitada
- Incluyen información del usuario (ID y rol)
- Deben enviarse en el header `Authorization: Bearer <token>`

### Validaciones
- Validación de roles por endpoint
- Validación de propiedad de recursos (usuarios solo pueden ver/editar sus propios datos)
- Validación de datos de entrada con validadores personalizados

### Encriptación
- Las contraseñas se encriptan usando BCrypt
- Configuración segura de CORS para múltiples entornos

## Estructura de la Base de Datos

### Tablas Principales
- **usuario**: Información de agrónomos y agricultores
- **solicitudasesoria**: Solicitudes de asesoría agrícola
- **plandecultivo**: Planes de cultivo generados
- **tarea**: Tareas asignadas a agricultores
- **catalogotaller**: Talleres disponibles
- **solicitudtaller**: Solicitudes de participación en talleres
- **reporteplaga**: Reportes de plagas en cultivos
- **reportedesempeño**: Reportes de desempeño de planes
- **registroactividad**: Registro de actividades agrícolas

### Catálogos del Sistema
- **catalogocultivo**: Tipos de cultivos disponibles
- **catalogoestado**: Estados del sistema
- **catalogoriego**: Tipos de riego
- **catalogorol**: Roles de usuario

## Desarrollo

### Estructura del Proyecto
```
src/main/java/org/example/
├── controller/     # Controladores REST
├── model/         # Modelos de datos
├── repository/    # Acceso a datos
├── service/       # Lógica de negocio
├── routers/       # Configuración de rutas
├── util/          # Utilidades (JWT, etc.)
├── Validator/     # Validadores de datos
└── Main.java      # Punto de entrada
```

### Comandos Útiles
```bash
# Compilar
./gradlew build

# Ejecutar tests
./gradlew test

# Generar JAR ejecutable
./gradlew shadowJar

# Ejecutar JAR
java -jar build/libs/API-agroData-nueva-rama-1.0-SNAPSHOT-all.jar
```

## Contribución

1. Fork el proyecto
2. Crear una rama para la feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit los cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para detalles.

## Contacto

Para soporte técnico o consultas sobre la API, contactar al equipo de desarrollo.

# Registrar actividad
curl -X POST http://localhost:7000/registroactividades/ \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '[{
    "idUsuario": 1,
    "actividad": "Riego del cultivo",
    "fecha": "2024-01-21T06:00:00",
    "observaciones": "Riego matutino completado"
  }]'

# Obtener reporte de desempeño (solo agrónomos)
curl -X GET http://localhost:7000/obtenerReporteDesempeno/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Seguridad

### Autenticación JWT
- Los tokens JWT tienen una duración limitada
- Incluyen información del usuario (ID y rol)
- Deben enviarse en el header `Authorization: Bearer <token>`

### Validaciones
- Validación de roles por endpoint
- Validación de propiedad de recursos (usuarios solo pueden ver/editar sus propios datos)
- Validación de datos de entrada con validadores personalizados

### Encriptación
- Las contraseñas se encriptan usando BCrypt
- Configuración segura de CORS para múltiples entornos

## Estructura de la Base de Datos

### Tablas Principales
- **usuario**: Información de agrónomos y agricultores
- **solicitudasesoria**: Solicitudes de asesoría agrícola
- **plandecultivo**: Planes de cultivo generados
- **tarea**: Tareas asignadas a agricultores
- **catalogotaller**: Talleres disponibles
- **solicitudtaller**: Solicitudes de participación en talleres
- **reporteplaga**: Reportes de plagas en cultivos
- **reportedesempeño**: Reportes de desempeño de planes
- **registroactividad**: Registro de actividades agrícolas

### Catálogos del Sistema
- **catalogocultivo**: Tipos de cultivos disponibles
- **catalogoestado**: Estados del sistema
- **catalogoriego**: Tipos de riego
- **catalogorol**: Roles de usuario

## Desarrollo

### Estructura del Proyecto
```
src/main/java/org/example/
├── controller/     # Controladores REST
├── model/         # Modelos de datos
├── repository/    # Acceso a datos
├── service/       # Lógica de negocio
├── routers/       # Configuración de rutas
├── util/          # Utilidades (JWT, etc.)
├── Validator/     # Validadores de datos
└── Main.java      # Punto de entrada
```

### Comandos Útiles
```bash
# Compilar
./gradlew build

# Ejecutar tests
./gradlew test

# Generar JAR ejecutable
./gradlew shadowJar

# Ejecutar JAR
java -jar build/libs/API-agroData-nueva-rama-1.0-SNAPSHOT-all.jar
```

## Contribución

1. Fork el proyecto
2. Crear una rama para la feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit los cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para detalles.

## Contacto

Para soporte técnico o consultas sobre la API, contactar al equipo de desarrollo.