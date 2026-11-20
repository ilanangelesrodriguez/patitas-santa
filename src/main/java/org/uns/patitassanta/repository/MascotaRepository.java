package org.uns.patitassanta.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.uns.patitassanta.model.Mascota;
import java.util.List;
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByDuenoDniOrderByIdAsc(String dni);
    boolean existsByDuenoId(Long duenoId);
}
