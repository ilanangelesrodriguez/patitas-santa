package org.uns.patitassanta.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.uns.patitassanta.model.Veterinario;
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    boolean existsByNumeroColegiatura(String numeroColegiatura);
    boolean existsByNumeroColegiaturaAndIdNot(String numeroColegiatura, Long id);
}
