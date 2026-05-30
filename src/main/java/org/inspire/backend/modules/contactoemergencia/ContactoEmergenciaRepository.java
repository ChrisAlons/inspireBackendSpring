package org.inspire.backend.modules.contactoemergencia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactoEmergenciaRepository extends JpaRepository<ContactoEmergencia, UUID> {
    List<ContactoEmergencia> findByPacienteIdOrderByPrioridad(UUID pacienteId);
}
