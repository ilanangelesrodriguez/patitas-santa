package org.uns.patitassanta.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas", indexes = {
    @Index(name = "idx_cita_fecha", columnList = "fecha_hora"),
    @Index(name = "idx_cita_veterinario", columnList = "veterinario_id")
})
@Getter @Setter @NoArgsConstructor
public class Cita {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mascota_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cita_mascota"))
    private Mascota mascota;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "veterinario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cita_veterinario"))
    private Veterinario veterinario;
    @Column(name = "fecha_hora", nullable = false) private LocalDateTime fechaHora;
    @Column(nullable = false, length = 250) private String motivo;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private EstadoCita estado;
    @Column(length = 500) private String diagnostico;
    @Column(precision = 10, scale = 2) private BigDecimal costoSoles;
}
