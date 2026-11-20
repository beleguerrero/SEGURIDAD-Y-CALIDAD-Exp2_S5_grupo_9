package cl.duoc.level_start;

import cl.duoc.level_start.service.EventoComentarioService;
import cl.duoc.level_start.service.EventoService;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.duoc.level_start.dto.EventoComentarioRequestDTO;
import cl.duoc.level_start.dto.UsuarioRequestDTO;
import cl.duoc.level_start.entity.EventoEntity;
import cl.duoc.level_start.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller 
public class HomeController {

    private final EventoService eventoService;
    private final UsuarioService usuarioService;
    private final EventoComentarioService eventoComentarioService;

    public HomeController(
        UsuarioService usuarioService, 
        EventoService eventoService,
        EventoComentarioService eventoComentarioService
    ) {
        this.usuarioService = usuarioService;
        this.eventoService = eventoService;
        this.eventoComentarioService = eventoComentarioService;
    }
    
    @GetMapping("/")
    public String root(@RequestParam(name="name", required=false, defaultValue="Level Start") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("eventos", eventoService.getEventosPublicos());
        model.addAttribute("eventosPrivados", eventoService.getEventosPrivados());
        return "home";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name="name", required=false, defaultValue="Level Start") String name, Model model) {
        model.addAttribute("name", name);
        return "login";
    }

    @GetMapping("/registro")
    public String registro(@RequestParam(name="name", required=false, defaultValue="Level Start") String name, Model model) {
        model.addAttribute("name", name);
        return "registro";
    }

    // post
    @PostMapping("/registro")
    public String doUsuario(@Valid @ModelAttribute("usuario") UsuarioRequestDTO request, BindingResult result, Model model) {
        System.out.println("doUsuario POST /registro recibido");
        
        if (result.hasErrors()) {
            System.out.println("doUsuario Errores de validación:");

            result.getAllErrors().forEach(error ->
                System.out.println("doUsuario " + error.toString())
            );

            return "registro";
        }

        try {
            usuarioService.doUsuario(request);
            System.out.println("doUsuario Usuario registrado correctamente");
            return "redirect:/login?registroExitoso";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }


    @GetMapping("/eventos-privados")
    public String eventos_privados(@RequestParam(name="name", required=false, defaultValue="Level Start") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("eventosPrivados", eventoService.getEventosPrivados());
        return "eventos_privados";
    }

    @GetMapping("/403")
    public String forbidden(@RequestParam(name="name", required=false, defaultValue="Level Start") String name, Model model) {
        model.addAttribute("name", name);
        return "403";
    }

    @GetMapping("/eventos/{id}")
    public String detalleEvento(@PathVariable Long id, Model model) {
        EventoEntity evento = eventoService.getEventoById(id);

        if(evento == null) {
            return "redirect:/home";
        }

        model.addAttribute("evento", evento);
        model.addAttribute("comentarios", eventoComentarioService.getComentariosEvento(id));
        model.addAttribute("comentarioDTO", new EventoComentarioRequestDTO());

        return "single_evento";
    }

    @PostMapping("/eventos/{id}/comentarios")
    public String doComentario(
        @PathVariable Long id,
        @Valid @ModelAttribute("comentarioDTO") EventoComentarioRequestDTO request,
        BindingResult result,
        Principal principal,
        RedirectAttributes redirectAttributes
    ) {

        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", result.getFieldError().getDefaultMessage());
            return "redirect:/eventos/" + id;
        }

        try {
            eventoComentarioService.doComentario(id, principal.getName(), request);
            redirectAttributes.addFlashAttribute("mensaje", "Reseña agregada correctamente!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error","No fue posible publicar tu reseña");
        }

        return "redirect:/eventos/" + id;
    }
}
