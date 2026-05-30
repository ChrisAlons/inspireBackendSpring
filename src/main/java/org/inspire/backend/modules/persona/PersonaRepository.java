package org.inspire.backend.modules.persona;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PersonaRepository extends JpaRepository<Persona, UUID> {

    boolean existsByTipoDocumentoIdAndNumeroDocumento(Short tipoDocumentoId, String numeroDocumento);

    Optional<Persona> findByTipoDocumentoIdAndNumeroDocumento(Short tipoDocumentoId, String numeroDocumento);

    @Query("SELECT p FROM Persona p WHERE LOWER(p.apellidoPaterno) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(p.apellidoMaterno) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(p.nombres) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<Persona> buscar(String q, Pageable pageable);
}
