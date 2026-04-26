package org.inspire.backend.modules.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {

    Optional<Paciente> findByCodigoHistoria(String codigoHistoria);

    boolean existsByPersonaId(UUID personaId);

    @Query("SELECT p FROM Paciente p JOIN p.persona per WHERE LOWER(per.apellidoPaterno) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(per.nombres) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(p.codigoHistoria) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<Paciente> buscar(String q, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM clinica.paciente", nativeQuery = true)
    long countTotal();
}
