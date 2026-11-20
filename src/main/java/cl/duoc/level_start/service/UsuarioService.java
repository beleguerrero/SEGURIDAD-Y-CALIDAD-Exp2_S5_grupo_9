package cl.duoc.level_start.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.duoc.level_start.dto.LoginRequestDTO;
import cl.duoc.level_start.dto.LoginResponseDTO;
import cl.duoc.level_start.dto.UsuarioRequestDTO;
import cl.duoc.level_start.entity.TipoUsuarioEntity;
import cl.duoc.level_start.entity.UsuarioEntity;
import cl.duoc.level_start.jwt.JwtUtil;
import cl.duoc.level_start.repository.TipoUsuarioRepository;
import cl.duoc.level_start.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service 
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(
        UsuarioRepository usuarioRepository,
        TipoUsuarioRepository tipoUsuarioRepository,
        AuthenticationManager authenticationManager, 
        JwtUtil jwtUtil,
        PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }


    public LoginResponseDTO login(LoginRequestDTO data) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getUsuario(), data.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UsuarioEntity usuario = usuarioRepository.findByUsuario(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        String token = jwtUtil.generateToken(usuario.getId(), userDetails.getUsername(), role);

        return new LoginResponseDTO(token, "Bearer");
    }


    @Transactional 
    public void doUsuario(UsuarioRequestDTO usuarioRequest) {

        List<String> errores = new ArrayList<>();

        String nUsuario = usuarioRequest.getUsuario();
        String password = usuarioRequest.getPassword();
        String nombre = usuarioRequest.getNombre();
        String correo = usuarioRequest.getCorreo();

        if (nUsuario == null || nUsuario.isBlank()) {
            errores.add("Nombre de usuario no puede estar vacío");
        } else if (usuarioRepository.existsByUsuario(nUsuario)) {
            errores.add("El usuario ya está registrado");
        }

        if (password == null || password.isBlank()) {
            errores.add("Contraseña no puede estar vacía");
        }

        if (nombre == null || nombre.isBlank()) {
            errores.add("Nombre no puede estar vacío");
        }

        if (correo == null || correo.isBlank()) {
            errores.add("Correo no puede estar vacío");
        } else if (usuarioRepository.existsByCorreo(correo)) {
            errores.add("El correo ya está registrado");
        }

        if (usuarioRequest.getConfirmPassword() == null ||
            !usuarioRequest.getConfirmPassword().equals(password)) {
            errores.add("Las contraseñas no coinciden");
        }

        if (!errores.isEmpty()) {
            throw new IllegalArgumentException(
                String.join(", ", errores)
            );
        }

        TipoUsuarioEntity tipoUsuario = tipoUsuarioRepository.findByNombre("USER")
            .orElseThrow(() -> new IllegalStateException(
                "Tipo de usuario USER no encontrado"
            ));

        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setUsuario(nUsuario);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setFecha(LocalDateTime.now());
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setActivo(1);

        usuarioRepository.save(usuario);
    }


}
