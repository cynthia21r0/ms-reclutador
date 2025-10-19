package com.ms_reclutador.repository;

import com.ms_reclutador.model.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacanteRepository extends JpaRepository<Vacante, Long> {

    List<Vacante> findByEmpresa(String empresa);

    List<Vacante> findByEstado(String estado);

    List<Vacante> findByAreaId(Long areaId);

    List<Vacante> findByModalidadId(Long modalidadId);

    @Query("SELECT v FROM Vacante v WHERE v.empresa = :empresa AND v.estado = :estado")
    List<Vacante> findByEmpresaAndEstado(@Param("empresa") String empresa,
                                         @Param("estado") String estado);

    @Query("SELECT v FROM Vacante v JOIN v.habilidades h WHERE h.id = :habilidadId")
    List<Vacante> findByHabilidadId(@Param("habilidadId") Long habilidadId);

    @Query("SELECT v FROM Vacante v JOIN v.idiomas i WHERE i.id = :idiomaId")
    List<Vacante> findByIdiomaId(@Param("idiomaId") Long idiomaId);

    Optional<Vacante> findByIdAndEmpresa(Long id, String empresa);
}