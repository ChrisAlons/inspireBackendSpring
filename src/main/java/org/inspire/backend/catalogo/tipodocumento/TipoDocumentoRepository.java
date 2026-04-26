package org.inspire.backend.catalogo.tipodocumento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Short> {
    Optional<TipoDocumento> findByCodigo(String codigo);
}
