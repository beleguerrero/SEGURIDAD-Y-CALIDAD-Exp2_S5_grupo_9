package cl.duoc.level_start.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.level_start.entity.UsuarioJuegoEntity;


@Repository
public interface UsuarioJuegoRepository extends JpaRepository<UsuarioJuegoEntity, Long> {

    List<UsuarioJuegoEntity> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioIdAndJuegoId(Long usuarioId, Long juegoId);
}
