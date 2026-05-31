package org.inspire.backend.modules.dashboard.dto;

public record DashboardStatsResponse(
    long pacientesTotal,
    long citasHoy,
    long atencionesEnCurso,
    long citasProximas
) {}