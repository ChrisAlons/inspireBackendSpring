package org.inspire.backend.modules.habito;

import org.inspire.backend.common.enums.TipoHabito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HabitoRepository extends JpaRepository<Habito, UUID> {
    List<Habito> findByHistoriaClinicaId(UUID historiaClinicaId);
    Optional<Habito> findByHistoriaClinicaIdAndTipo(UUID historiaClinicaId, TipoHabito tipo);
}
