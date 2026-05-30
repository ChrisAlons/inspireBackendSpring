package org.inspire.backend.modules.antecedente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AntecedenteFamiliarRepository extends JpaRepository<AntecedenteFamiliar, UUID> {
    List<AntecedenteFamiliar> findByHistoriaClinicaId(UUID historiaClinicaId);
}
