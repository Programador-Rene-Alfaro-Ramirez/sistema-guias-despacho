package cl.duoc.sistema_guias_despacho.repository;

import cl.duoc.sistema_guias_despacho.model.GuiaDespacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface GuiaDespachoRepository extends JpaRepository<GuiaDespacho, Long> {
    
    // Método personalizado para buscar por transportista y fecha
    List<GuiaDespacho> findByTransportistaAndFecha(String transportista, LocalDate fecha);
} 