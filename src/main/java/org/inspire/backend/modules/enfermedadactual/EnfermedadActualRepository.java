package org.inspire.backend.modules.enfermedadactual;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EnfermedadActualRepository extends JpaRepository<EnfermedadActual, UUID> {
    Optional<EnfermedadActual> findByAtencionId(UUID atencionId);
}
