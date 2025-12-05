package org.example.Validator;

import org.example.model.SolicitudAsesoria;

/**
 * Validador para solicitudes de asesoría agrícola.
 * Verifica que todos los campos obligatorios estén presentes y sean válidos.
 */
public class SolicitudAsesoriaValidator {
    /**
     * Constructor vacío para crear un validador de solicitudes de asesoría sin inicializar datos.
     */
    public SolicitudAsesoriaValidator() {
    }
    /**
     * Valida una solicitud de asesoría verificando todos los campos obligatorios.
     * @param solicitud la solicitud a validar
     * @return null si es válida, o un mensaje de error si hay problemas
     */
    public static String validar(SolicitudAsesoria solicitud) {
        if (solicitud == null) {
            return "La solicitud está vacía";
        }

        if (solicitud.getIdAgricultor() <= 0) {
            return "El campo 'idAgricultor' es obligatorio";
        }

        if (solicitud.getSuperficieTotal() <= 0) {
            return "La superficie total debe ser mayor a cero";
        }

        if (solicitud.getDireccionTerreno() == null || solicitud.getDireccionTerreno().isBlank()) {
            return "La ubicación del terreno es obligatoria";
        }

        if (solicitud.getMotivoAsesoria() == null || solicitud.getMotivoAsesoria().isBlank()) {
            return "Debe indicar el motivo de la asesoría";
        }

        if (solicitud.getIdEstado() <= 0) {
            return "Debe seleccionar un estado válido";
        }
        if (solicitud.isTienePlaga()) {
            if (solicitud.getDescripcionPlaga() == null || solicitud.getDescripcionPlaga().isBlank()) {
                return "Debe describir la plaga si indicó que tiene plaga";
            }
        }

        if (solicitud.isUsoMaquinaria()) {
            if (solicitud.getNombreMaquinaria() == null || solicitud.getNombreMaquinaria().isBlank()) {
                return "Debe indicar el nombre de la maquinaria si se usa maquinaria";
            }
        }

        return null;
    }
}
