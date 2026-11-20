package cl.duoc.level_start.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class UsuarioRequestDTO {
    @NotNull(message = "Debe ingresar usuario")
    private String usuario;

    @NotNull(message = "Debe ingresar contraseña")
    private String password;
    
     @NotNull(message = "Debe ingresar repita contraseña")
    private String confirmPassword;

    @NotNull(message = "Debe ingresar nombre")
    private String nombre;

    private String correo;
    // private TipoUsuarioEntity tipoUsuario;
}
