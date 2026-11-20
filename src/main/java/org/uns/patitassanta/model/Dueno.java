package org.uns.patitassanta.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "duenos", uniqueConstraints = @UniqueConstraint(name = "uk_dueno_dni", columnNames = "dni"))
@Getter @Setter @NoArgsConstructor
public class Dueno {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 8) private String dni;
    @Column(nullable = false, length = 80) private String nombres;
    @Column(nullable = false, length = 100) private String apellidos;
    @Column(nullable = false, length = 20) private String celular;
    @Column(length = 120) private String correo;
    @Column(nullable = false, length = 200) private String direccion;
    @OneToMany(mappedBy = "dueno") private List<Mascota> mascotas = new ArrayList<>();
}
