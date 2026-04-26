package org.inspire.backend.modules.historiaclinica;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, UUID> {
    Optional<HistoriaClinica> findByPacienteId(UUID pacienteId);
}
