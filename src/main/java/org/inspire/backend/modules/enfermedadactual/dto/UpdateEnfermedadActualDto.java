package org.inspire.backend.modules.enfermedadactual.dto;

public record UpdateEnfermedadActualDto(
        String motivoConsulta,
        String tiempoEnfermedad,
        String signosSintomas,
        String funcionesBiologicas,
        String expectativasTratamiento
) {}
