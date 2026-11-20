package cl.duoc.level_start.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.level_start.entity.EventoComentarioEntity;

@Repository 
public interface EventoComentarioRepository extends JpaRepository<EventoComentarioEntity, Long> {

    List<EventoComentarioEntity> findByEventoIdOrderByFechaDesc(Long eventoId);

    boolean existsByEventoIdAndUsuarioId(Long eventoId, Long usuarioId);

}
