package cl.duoc.level_start.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "juego")
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class JuegoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "j_id")
    private Long id;
    
    @Column(nullable = false, length = 150, name = "j_nombre")
    private String nombre;

    @ManyToOne 
    @JoinColumn(nullable = false, name = "c_id")
    private CategoriaEntity categoria;
    
    @Column(nullable = false, length = 1, name = "j_activo")
    private Integer activo;
}

