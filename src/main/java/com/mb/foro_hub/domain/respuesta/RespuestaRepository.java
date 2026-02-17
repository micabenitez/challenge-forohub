package com.mb.foro_hub.domain.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Page<Respuesta> findAllByTopicoId(Long topicoId, Pageable paginacion);

    @Query("""
        SELECT COUNT(r) > 0
        FROM Respuesta r
        WHERE r.id = :respuestaId
        AND r.usuario.email = :email
    """)
    boolean esAutor(Long respuestaId, String email);
}
