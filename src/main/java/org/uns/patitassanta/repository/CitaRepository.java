package org.uns.patitassanta.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.uns.patitassanta.model.Cita;
import org.uns.patitassanta.model.EstadoCita;
import java.time.LocalDateTime;
import java.util.List;
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByVeterinarioIdOrderByFechaHoraAsc(Long veterinarioId);
    List<Cita> findByMascotaIdOrderByFechaHoraAsc(Long mascotaId);
    boolean existsByVeterinarioId(Long veterinarioId);
    boolean existsByMascotaId(Long mascotaId);
    boolean existsByVeterinarioIdAndFechaHoraAndEstadoAndIdNot(
            Long veterinarioId, LocalDateTime fechaHora, EstadoCita estado, Long id);
    boolean existsByMascotaIdAndFechaHoraAndEstadoAndIdNot(
            Long mascotaId, LocalDateTime fechaHora, EstadoCita estado, Long id);
}
