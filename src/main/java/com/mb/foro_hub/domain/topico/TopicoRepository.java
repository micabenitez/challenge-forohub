package com.mb.foro_hub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findAllByActivoTrue(Pageable paginacion);

    @Query("""
        SELECT COUNT(t) > 0
        FROM Topico t
        WHERE t.id = :id
        AND t.usuario.email = :email
    """)
    boolean esAutor(Long id, String email);
}
