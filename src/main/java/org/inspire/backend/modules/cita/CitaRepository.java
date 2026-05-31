package org.inspire.backend.modules.cita;

import org.inspire.backend.common.enums.EstadoCita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface CitaRepository extends JpaRepository<Cita, UUID> {
    Page<Cita> findByPacienteId(UUID pacienteId, Pageable pageable);
    Page<Cita> findByOdontologoId(UUID odontologoId, Pageable pageable);
    Page<Cita> findByPacienteIdAndOdontologoId(UUID pacienteId, UUID odontologoId, Pageable pageable);
    List<Cita> findByEstado(EstadoCita estado);
    List<Cita> findByFechaHoraInicioBetween(OffsetDateTime desde, OffsetDateTime hasta);
}
