package cl.duoc.level_start.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "evento_multimedia")
@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class EventoMultimediaEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "em_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "e_id", nullable = false)
    private EventoEntity evento;

    @Column(name = "em_ruta", nullable = false, length = 500)
    private String ruta;

    @Column(name = "em_tipo", nullable = false, length = 20)
    private String tipo;
}
