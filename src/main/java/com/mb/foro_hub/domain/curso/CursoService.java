package com.mb.foro_hub.domain.curso;

import com.mb.foro_hub.domain.curso.dto.DatosActualizacionCurso;
import com.mb.foro_hub.domain.curso.dto.DatosDetalleCurso;
import com.mb.foro_hub.domain.curso.dto.DatosRegistroCurso;
import com.mb.foro_hub.exceptions.ValidacionException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CursoService {
    private final CursoRepository cursoRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public DatosDetalleCurso registrarCurso(@Valid DatosRegistroCurso datos) {
        if(cursoRepository.existsByNombre(datos.nombre())) throw new ValidacionException("Ya existe un curso con el nombre: " + datos.nombre());

        var curso = new Curso(datos);
        cursoRepository.save(curso);
        return new DatosDetalleCurso(curso);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void desactivar(Long id) {
        var curso = getCurso(id);
        curso.desactivar();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public DatosDetalleCurso actualizar(Long id, @Valid DatosActualizacionCurso datos) {
        var curso = getCurso(id);
        curso.actualizar(datos);
        return new DatosDetalleCurso(curso);
    }

    public DatosDetalleCurso obtenerCursoPorId(Long id) {
        var curso = getCurso(id);
        return new DatosDetalleCurso(curso);
    }

    public Page<DatosDetalleCurso> obtenerCursos(Pageable paginacion) {
        return cursoRepository.findAllByActivoTrue(paginacion).map(DatosDetalleCurso::new);
    }

    private Curso getCurso(Long id) {
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe curso con ese ID"));
        return curso;
    }
}
