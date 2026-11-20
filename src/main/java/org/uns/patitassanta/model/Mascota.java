package org.uns.patitassanta.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mascotas")
@Getter @Setter @NoArgsConstructor
public class Mascota {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 80) private String nombre;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Especie especie;
    @Column(nullable = false, length = 80) private String raza;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private SexoMascota sexo;
    private LocalDate fechaNacimiento;
    @Column(nullable = false, precision = 7, scale = 2) private BigDecimal pesoActualKg;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dueno_id", nullable = false, foreignKey = @ForeignKey(name = "fk_mascota_dueno"))
    private Dueno dueno;
    @OneToMany(mappedBy = "mascota") private List<Cita> citas = new ArrayList<>();
}
