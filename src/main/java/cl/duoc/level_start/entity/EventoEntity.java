package cl.duoc.level_start.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "evento")
@Data
@NoArgsConstructor
@AllArgsConstructor 
public class EventoEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "e_id")
    private Long id;
    
    @Column(nullable = false, length = 150, name = "e_nombre")
    private String nombre;

    @Lob
    @Column(nullable = false, name = "e_detalle")
    private String detalle;

    @Column(nullable = false, length = 150, name = "e_imagen")
    private String imagen;

    @Column(nullable = false, name = "e_fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(nullable = false, length = 150, name = "e_lugar")
    private String lugar;

    @Column(nullable = false, name = "e_precio")
    private Integer precio;

    @Column(nullable = false, name = "e_fecha_evento")
    private LocalDateTime fechaEvento;

    @Column(nullable = false, length = 1, name = "e_privado")
    private Integer privado;
    
    @ManyToOne 
    @JoinColumn(nullable = false, name = "j_id")
    private JuegoEntity juego;
    
    @Column(nullable = false, length = 1, name = "e_activo")
    private Integer activo;
}

