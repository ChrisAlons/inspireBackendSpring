package org.inspire.backend.modules.odontograma;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OdontogramaHallazgoRepository extends JpaRepository<OdontogramaHallazgo, UUID> {
    List<OdontogramaHallazgo> findByOdontogramaId(UUID odontogramaId);
    List<OdontogramaHallazgo> findByOdontogramaIdAndPiezaId(UUID odontogramaId, Short piezaId);
}
