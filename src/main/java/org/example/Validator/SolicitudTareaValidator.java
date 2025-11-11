package org.example.Validator;

import org.example.model.Tarea;

public class SolicitudTareaValidator {

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
