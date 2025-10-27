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

    List<Vacante> findByEmpresaId(Long empresaId);
    List<Vacante> findByEstado(String estado);
    List<Vacante> findByAreaId(Long areaId);
    List<Vacante> findByModalidadId(Long modalidadId);

    @Query("SELECT v FROM Vacante v WHERE v.empresaId = :empresaId AND v.estado = :estado")
    List<Vacante> findByEmpresaIdAndEstado(@Param("empresaId") Long empresaId,
                                           @Param("estado") String estado);

    @Query("SELECT v FROM Vacante v JOIN v.habilidades h WHERE h.id = :habilidadId")
    List<Vacante> findByHabilidadId(@Param("habilidadId") Long habilidadId);

    @Query("SELECT v FROM Vacante v JOIN v.idiomas i WHERE i.id = :idiomaId")
    List<Vacante> findByIdiomaId(@Param("idiomaId") Long idiomaId);

    Optional<Vacante> findByIdAndEmpresaId(Long id, Long empresaId);

    // 🔹 Query nativa para obtener vacante con todas las relaciones
    @Query(value = """
    SELECT v.id, v.titulo, v.descripcion, v.salario, v.tipo_contrato,
           v.solicitudes_permitidas, v.estado, v.fecha_creacion, v.fecha_expiracion,
           v.beneficios, v.empresa_id, v.hora_inicio, v.hora_fin, v.dias_laborales,
           v.horas_por_semana, v.turno, v.horario_flexible,
           a.id as area_id, a.nombre as area_nombre,
           m.id as modalidad_id, m.nombre as modalidad_nombre,
           h.id as habilidad_id, h.nombre as habilidad_nombre, h.id_area as habilidad_area_id,
           i.id as idioma_id, i.idioma as idioma_nombre,
           e.nombre as empresa_nombre, e.colonia as empresa_colonia, e.cp as empresa_cp,
           e.edo as empresa_estado, e.municipio as empresa_municipio
    FROM reclutador.vacantes v
    LEFT JOIN reclutador.areas a ON v.area_id = a.id
    LEFT JOIN reclutador.modalidades m ON v.modalidad_id = m.id
    LEFT JOIN reclutador.vacantes_habilidades vh ON v.id = vh.vacante_id
    LEFT JOIN reclutador.habilidades h ON vh.habilidad_id = h.id
    LEFT JOIN reclutador.vacantes_idiomas vi ON v.id = vi.vacante_id
    LEFT JOIN reclutador.idiomas i ON vi.idioma_id = i.id
    LEFT JOIN maching.empresas e ON v.empresa_id = e.id
    WHERE v.id = :id
    """, nativeQuery = true)
    List<Object[]> findVacanteCompletaById(@Param("id") Long id);


    // 🔹 Query nativa para obtener todas las vacantes con relaciones
    @Query(value = """
        SELECT DISTINCT v.id, v.titulo, v.descripcion, v.salario, v.tipo_contrato,
               v.solicitudes_permitidas, v.estado, v.fecha_creacion, v.fecha_expiracion,
               v.beneficios, v.empresa_id, v.hora_inicio, v.hora_fin, v.dias_laborales,
               v.horas_por_semana, v.turno, v.horario_flexible,
               a.id as area_id, a.nombre as area_nombre,
               m.id as modalidad_id, m.nombre as modalidad_nombre
        FROM reclutador.vacantes v
        LEFT JOIN reclutador.areas a ON v.area_id = a.id
        LEFT JOIN reclutador.modalidades m ON v.modalidad_id = m.id
        ORDER BY v.fecha_creacion DESC
        """, nativeQuery = true)
    List<Object[]> findAllVacantesCompletas();

    // 🔹 Query nativa para vacantes por empresa con relaciones
    @Query(value = """
        SELECT DISTINCT v.id, v.titulo, v.descripcion, v.salario, v.tipo_contrato,
               v.solicitudes_permitidas, v.estado, v.fecha_creacion, v.fecha_expiracion,
               v.beneficios, v.empresa_id, v.hora_inicio, v.hora_fin, v.dias_laborales,
               v.horas_por_semana, v.turno, v.horario_flexible,
               a.id as area_id, a.nombre as area_nombre,
               m.id as modalidad_id, m.nombre as modalidad_nombre
        FROM reclutador.vacantes v
        LEFT JOIN reclutador.areas a ON v.area_id = a.id
        LEFT JOIN reclutador.modalidades m ON v.modalidad_id = m.id
        WHERE v.empresa_id = :empresaId
        ORDER BY v.fecha_creacion DESC
        """, nativeQuery = true)
    List<Object[]> findVacantesCompletasByEmpresaId(@Param("empresaId") Long empresaId);
}