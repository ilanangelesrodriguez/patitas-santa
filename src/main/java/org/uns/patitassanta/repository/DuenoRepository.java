package org.uns.patitassanta.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.uns.patitassanta.model.Dueno;
import java.util.Optional;
public interface DuenoRepository extends JpaRepository<Dueno, Long> {
    Optional<Dueno> findByDni(String dni);
    boolean existsByDni(String dni);
    boolean existsByDniAndIdNot(String dni, Long id);
}
