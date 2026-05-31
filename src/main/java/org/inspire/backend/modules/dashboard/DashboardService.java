package org.inspire.backend.modules.dashboard;

import lombok.RequiredArgsConstructor;
import org.inspire.backend.common.enums.EstadoCita;
import org.inspire.backend.modules.atencion.AtencionRepository;
import org.inspire.backend.modules.cita.CitaRepository;
import org.inspire.backend.modules.paciente.PacienteRepository;
import org.inspire.backend.modules.dashboard.dto.DashboardStatsResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final PacienteRepository pacienteRepo;
    private final CitaRepository citaRepo;
    private final AtencionRepository atencionRepo;

    public DashboardStatsResponse getStats() {
        long pacientesTotal = pacienteRepo.countTotal();

        LocalDate hoy = LocalDate.now();
        OffsetDateTime inicioDia = hoy.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime finDia = hoy.plusDays(1).atStartOfDay().atOffset(ZoneOffset.UTC);

        long citasHoy = citaRepo.findByFechaHoraInicioBetween(inicioDia, finDia).size();

        long atencionesEnCurso = atencionRepo.findByFechaFinIsNull().size();

        OffsetDateTime ahora = OffsetDateTime.now();
        OffsetDateTime enUnaSemana = ahora.plusDays(7);
        long citasProximas = citaRepo.findByFechaHoraInicioBetween(ahora, enUnaSemana).stream()
                .filter(c -> c.getEstado() != EstadoCita.CANCELADA && c.getEstado() != EstadoCita.ATENDIDA)
                .count();

        return new DashboardStatsResponse(
                pacientesTotal,
                citasHoy,
                atencionesEnCurso,
                citasProximas
        );
    }
}