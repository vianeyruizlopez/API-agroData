package org.example.Validator;
import org.example.model.SolicitudTaller;

/**
 * Validador para solicitudes de taller.
 * Verifica que todos los campos obligatorios estén presentes y sean válidos.
 */
public class SolicitudTallerValidator {
    /**
     * Constructor vacío para crear un validador de solicitudes de taller sin inicializar datos.
     */
    public SolicitudTallerValidator() {
    }

    /**
     * Valida una solicitud de taller verificando todos los campos obligatorios.
     * @param solicitud la solicitud de taller a validar
     * @return null si es válida, o un mensaje de error si hay problemas
     */
    public static String validar(SolicitudTaller solicitud) {
        if (solicitud == null) {
            return "La solicitud está vacía";
        }

        if (solicitud.getIdAgricultor() <= 0) {
            return "El campo 'idAgricultor' es obligatorio";
        }

        if (solicitud.getIdTaller() <= 0) {
            return "Debe seleccionar un taller válido";
        }


        if (solicitud.getFechaAplicarTaller() == null) {
            return "Debe indicar la fecha en que desea aplicar el taller";
        }


        if (solicitud.getDireccion() == null || solicitud.getDireccion().isBlank()) {
            return "La dirección es obligatoria";
        }

        if (solicitud.getComentario() == null || solicitud.getComentario().isBlank()) {
            return "El comentario es obligatorio";
        }

        if (solicitud.getIdEstado() <= 0) {
            return "Debe seleccionar un estado válido";
        }


        return null;
    }

}
