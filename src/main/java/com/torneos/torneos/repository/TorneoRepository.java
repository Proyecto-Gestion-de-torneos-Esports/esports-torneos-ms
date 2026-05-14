package com.torneos.torneos.repository;

import com.torneos.torneos.model.EstadoTorneo;
import com.torneos.torneos.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TorneoRepository extends JpaRepository<Torneo, Long> {

    List<Torneo> findByIdJuego(Long idJuego);

    List<Torneo> findByEstado(EstadoTorneo estado);

    List<Torneo> findByNombreContainingIgnoreCase(String nombre);

}
