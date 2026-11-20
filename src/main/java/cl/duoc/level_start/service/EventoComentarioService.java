package cl.duoc.level_start.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.level_start.dto.EventoComentarioRequestDTO;
import cl.duoc.level_start.entity.EventoComentarioEntity;
import cl.duoc.level_start.entity.EventoEntity;
import cl.duoc.level_start.entity.UsuarioEntity;
import cl.duoc.level_start.repository.EventoComentarioRepository;
import cl.duoc.level_start.repository.EventoRepository;
import cl.duoc.level_start.repository.UsuarioRepository;

@Service 
public class EventoComentarioService {

    private final EventoComentarioRepository eventoComentarioRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public EventoComentarioService(
        EventoComentarioRepository eventoComentarioRepository,
        EventoRepository eventoRepository,
        UsuarioRepository usuarioRepository
    ) {
        this.eventoComentarioRepository = eventoComentarioRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void doComentario(Long eventoId, String nombreUsuario, EventoComentarioRequestDTO request) {

        EventoEntity evento = eventoRepository.findById(eventoId).orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        UsuarioEntity usuario = usuarioRepository.findByUsuario(nombreUsuario).orElseThrow(() -> new RuntimeException("Uusario no encontrado"));

        if(eventoComentarioRepository.existsByEventoIdAndUsuarioId(eventoId, usuario.getId())) {
            throw new IllegalStateException("Ya has publicado una reseña en este evento");
        }

        EventoComentarioEntity comentario = new EventoComentarioEntity();

        comentario.setEvento(evento);
        comentario.setUsuario(usuario);
        comentario.setComentario(request.getComentario());
        comentario.setValoracion(request.getValoracion());

        eventoComentarioRepository.save(comentario);

    }

    public List<EventoComentarioEntity> getComentariosEvento(Long eventoId) {
        return eventoComentarioRepository.findByEventoIdOrderByFechaDesc(eventoId);
    }

}
