package org.uns.patitassanta.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "veterinarios", uniqueConstraints = @UniqueConstraint(name = "uk_veterinario_colegiatura", columnNames = "numero_colegiatura"))
@Getter @Setter @NoArgsConstructor
public class Veterinario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "numero_colegiatura", nullable = false, length = 30) private String numeroColegiatura;
    @Column(nullable = false, length = 120) private String nombresCompletos;
    @Column(nullable = false, length = 100) private String especialidad;
    @Column(nullable = false) private boolean activo;
    @OneToMany(mappedBy = "veterinario") private List<Cita> citas = new ArrayList<>();
}
