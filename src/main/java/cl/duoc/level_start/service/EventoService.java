package cl.duoc.level_start.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.level_start.entity.EventoEntity;
import cl.duoc.level_start.repository.EventoRepository;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<EventoEntity> getEventosPublicos() {
        return eventoRepository.findByActivoAndPrivado(1, 0);
    }

    public List<EventoEntity> getEventosPrivados() {
        return eventoRepository.findByActivoAndPrivado(1, 1);
    }

    public EventoEntity getEventoById(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }
}
