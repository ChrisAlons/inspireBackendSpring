package org.inspire.backend.modules.antecedente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AntecedentePersonalRepository extends JpaRepository<AntecedentePersonal, UUID> {
    List<AntecedentePersonal> findByHistoriaClinicaIdOrderByFechaRegistroDesc(UUID historiaClinicaId);
}
