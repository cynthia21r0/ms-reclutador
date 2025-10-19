package com.ms_reclutador.service;

import com.ms_reclutador.model.*;
import com.ms_reclutador.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class VacanteService {

    @Autowired
    private VacanteRepository vacanteRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private HabilidadesRepository habilidadesRepository;

    @Autowired
    private IdiomasRepository idiomasRepository;

    @Autowired
    private ModalidadRepository modalidadRepository;

    // Crear vacante
    public Vacante crearVacante(Vacante vacante, Long areaId, Long modalidadId,
                                Set<Long> habilidadesIds, Set<Long> idiomasIds) {

        // Validar y establecer área
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        vacante.setArea(area);

        // Validar y establecer modalidad
        Modalidad modalidad = modalidadRepository.findById(modalidadId)
                .orElseThrow(() -> new RuntimeException("Modalidad no encontrada"));
        vacante.setModalidad(modalidad);

        // Establecer habilidades
        if (habilidadesIds != null && !habilidadesIds.isEmpty()) {
            Set<Habilidades> habilidades = habilidadesIds.stream()
                    .map(id -> habilidadesRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Habilidad no encontrada: " + id)))
                    .collect(Collectors.toSet());
            vacante.setHabilidades(habilidades);
        }

        // Establecer idiomas
        if (idiomasIds != null && !idiomasIds.isEmpty()) {
            Set<Idiomas> idiomas = idiomasIds.stream()
                    .map(id -> idiomasRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Idioma no encontrado: " + id)))
                    .collect(Collectors.toSet());
            vacante.setIdiomas(idiomas);
        }

        // Establecer fechas
        vacante.setFechaCreacion(LocalDateTime.now());
        if (vacante.getFechaExpiracion() == null) {
            vacante.setFechaExpiracion(LocalDateTime.now().plusDays(30)); // 30 días por defecto
        }

        // Validar estado
        if (vacante.getEstado() == null) {
            vacante.setEstado("ACTIVA");
        }

        return vacanteRepository.save(vacante);
    }

    // Obtener todas las vacantes
    @Transactional(readOnly = true)
    public List<Vacante> obtenerTodasLasVacantes() {
        return vacanteRepository.findAll();
    }

    // Obtener vacante por ID
    @Transactional(readOnly = true)
    public Vacante obtenerVacantePorId(Long id) {
        return vacanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada con ID: " + id));
    }

    // Obtener vacantes por empresa
    @Transactional(readOnly = true)
    public List<Vacante> obtenerVacantesPorEmpresa(String empresa) {
        return vacanteRepository.findByEmpresa(empresa);
    }

    // Obtener vacante por ID y empresa (para validación de propiedad)
    @Transactional(readOnly = true)
    public Vacante obtenerVacantePorIdYEmpresa(Long id, String empresa) {
        return vacanteRepository.findByIdAndEmpresa(id, empresa)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada o no pertenece a la empresa"));
    }

    // Actualizar vacante
    public Vacante actualizarVacante(Long id, Vacante vacanteActualizada,
                                     Long areaId, Long modalidadId,
                                     Set<Long> habilidadesIds, Set<Long> idiomasIds) {

        Vacante vacanteExistente = obtenerVacantePorId(id);

        // Actualizar campos básicos
        vacanteExistente.setTitulo(vacanteActualizada.getTitulo());
        vacanteExistente.setDescripcion(vacanteActualizada.getDescripcion());
        vacanteExistente.setSalario(vacanteActualizada.getSalario());
        vacanteExistente.setUbicacion(vacanteActualizada.getUbicacion());
        vacanteExistente.setTipoContrato(vacanteActualizada.getTipoContrato());
        vacanteExistente.setSolicitudesPermitidas(vacanteActualizada.getSolicitudesPermitidas());
        vacanteExistente.setBeneficios(vacanteActualizada.getBeneficios());
        vacanteExistente.setHoraInicio(vacanteActualizada.getHoraInicio());
        vacanteExistente.setHoraFin(vacanteActualizada.getHoraFin());
        vacanteExistente.setDiasLaborales(vacanteActualizada.getDiasLaborales());
        vacanteExistente.setHorasPorSemana(vacanteActualizada.getHorasPorSemana());
        vacanteExistente.setTurno(vacanteActualizada.getTurno());
        vacanteExistente.setHorarioFlexible(vacanteActualizada.getHorarioFlexible());

        // Actualizar área si se proporciona
        if (areaId != null) {
            Area area = areaRepository.findById(areaId)
                    .orElseThrow(() -> new RuntimeException("Área no encontrada"));
            vacanteExistente.setArea(area);
        }

        // Actualizar modalidad si se proporciona
        if (modalidadId != null) {
            Modalidad modalidad = modalidadRepository.findById(modalidadId)
                    .orElseThrow(() -> new RuntimeException("Modalidad no encontrada"));
            vacanteExistente.setModalidad(modalidad);
        }

        // Actualizar habilidades si se proporcionan
        if (habilidadesIds != null) {
            Set<Habilidades> habilidades = habilidadesIds.stream()
                    .map(habilidadId -> habilidadesRepository.findById(habilidadId)
                            .orElseThrow(() -> new RuntimeException("Habilidad no encontrada: " + habilidadId)))
                    .collect(Collectors.toSet());
            vacanteExistente.setHabilidades(habilidades);
        }

        // Actualizar idiomas si se proporcionan
        if (idiomasIds != null) {
            Set<Idiomas> idiomas = idiomasIds.stream()
                    .map(idiomaId -> idiomasRepository.findById(idiomaId)
                            .orElseThrow(() -> new RuntimeException("Idioma no encontrado: " + idiomaId)))
                    .collect(Collectors.toSet());
            vacanteExistente.setIdiomas(idiomas);
        }

        return vacanteRepository.save(vacanteExistente);
    }

    // Cambiar estado de vacante
    public Vacante cambiarEstadoVacante(Long id, String estado) {
        Vacante vacante = obtenerVacantePorId(id);
        vacante.setEstado(estado);
        return vacanteRepository.save(vacante);
    }

    // Eliminar vacante
    public void eliminarVacante(Long id) {
        Vacante vacante = obtenerVacantePorId(id);
        vacanteRepository.delete(vacante);
    }

    // Obtener habilidades por área
    @Transactional(readOnly = true)
    public List<Habilidades> obtenerHabilidadesPorArea(Long areaId) {
        return habilidadesRepository.findByAreaId(areaId);
    }
}