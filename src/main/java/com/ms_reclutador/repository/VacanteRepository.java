package com.ms_reclutador.repository;

import com.ms_reclutador.model.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacanteRepository extends JpaRepository<Vacante, Long> {

    List<Vacante> findAllByOrderByFechaCreacionDesc();

    List<Vacante> findByEstadoOrderByFechaCreacionDesc(String estado);

    @Query("SELECT v FROM Vacante v WHERE v.estado = 'ACTIVA' AND v.solicitudesRecibidas < v.solicitudesPermitidas AND v.fechaExpiracion > CURRENT_TIMESTAMP")
    List<Vacante> findVacantesDisponibles();

    @Query("SELECT COUNT(v) FROM Vacante v WHERE v.estado = 'ACTIVA'")
    Long countVacantesActivas();
}