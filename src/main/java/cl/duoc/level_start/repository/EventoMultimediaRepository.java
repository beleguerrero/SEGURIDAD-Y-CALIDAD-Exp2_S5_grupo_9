package cl.duoc.level_start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.level_start.entity.EventoMultimediaEntity;

@Repository 
public interface EventoMultimediaRepository extends JpaRepository<EventoMultimediaEntity, Long> {

}
