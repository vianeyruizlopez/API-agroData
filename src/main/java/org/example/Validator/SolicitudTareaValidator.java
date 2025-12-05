package org.example.Validator;

import org.example.model.Tarea;

/**
 * Validador para tareas agrícolas.
 * Verifica que las tareas tengan todos los campos obligatorios y fechas válidas.
 */
public class SolicitudTareaValidator {

    /**
     * Constructor vacío para crear un validador de tareas sin inicializar datos.
     */
    public SolicitudTareaValidator() {
    }

    /**
     * Valida una tarea verificando todos los campos obligatorios.
     * @param tarea la tarea a validar
     * @return null si es válida, o un mensaje de error si hay problemas
     */
    public static String validar(Tarea tarea) {
        if (tarea == null) {
            return "La tarea está vacía";
        }
        if (tarea.getIdPlan() <= 0) {
            return "El campo 'idPlan' es obligatorio";
        }
        if (tarea.getNombreTarea() == null || tarea.getNombreTarea().isBlank()) {
            return "El nombre de la tarea es obligatorio";
        }

        if (tarea.getFechaVencimiento() == null) {
            return "La fecha de vencimiento es obligatoria";
        }
        if (tarea.getIdEstado() <= 0) {
            return "Debe seleccionar un estado válido para la tarea";
        }
        if (tarea.getIdEstado() == 4 && tarea.getFechaCompletado() == null) {
            return "Debe indicar la fecha de completado si la tarea está marcada como completada";
        }

        return null;

    }
}
