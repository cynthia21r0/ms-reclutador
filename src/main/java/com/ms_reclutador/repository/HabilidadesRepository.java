package com.ms_reclutador.repository;

import com.ms_reclutador.model.Habilidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabilidadesRepository extends JpaRepository<Habilidades, Long> {
    List<Habilidades> findByAreaId(Long areaId);

    Optional<Habilidades> findByNombreAndAreaId(String nombre, Long areaId);

    @Query("SELECT h FROM Habilidades h WHERE h.area.id = :areaId ORDER BY h.nombre")
    List<Habilidades> findHabilidadesByArea(@Param("areaId") Long areaId);

    boolean existsByNombreAndAreaId(String nombre, Long areaId);
}
/*package com.ms_reclutador.repository;

import com.ms_reclutador.model.Habilidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabilidadesRepository extends JpaRepository<Habilidades, Long> {

    List<Habilidades> findByAreaId(Long areaId);

    Optional<Habilidades> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}*/