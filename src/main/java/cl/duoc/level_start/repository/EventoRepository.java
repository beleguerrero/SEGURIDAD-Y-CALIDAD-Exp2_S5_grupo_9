package cl.duoc.level_start.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.level_start.entity.EventoEntity;

@Repository 
public interface  EventoRepository extends JpaRepository<EventoEntity, Long> {

    List<EventoEntity> findByActivoAndPrivado(Integer activo, Integer privado);

}
