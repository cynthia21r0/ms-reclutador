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
/*package com.ms_reclutador.service;

import com.ms_reclutador.model.*;
import com.ms_reclutador.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VacanteService {

    @Autowired
    private VacanteRepository vacanteRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ModalidadRepository modalidadRepository;

    @Autowired
    private HabilidadesRepository habilidadesRepository;

    @Autowired
    private IdiomaRepository idiomasRepository;

    // ==================== CRUD BÁSICO ====================

    public List<Vacante> obtenerTodas() {
        return vacanteRepository.findAll();
    }

    public Optional<Vacante> obtenerPorId(Long id) {
        return vacanteRepository.findById(id);
    }

    @Transactional
    public Vacante crear(Vacante vacante) {
        // Establecer fecha de creación si no existe
        if (vacante.getFechaCreacion() == null) {
            vacante.setFechaCreacion(LocalDateTime.now());
        }

        // Validar que el área existe
        if (vacante.getArea() != null && vacante.getArea().getId() != null) {
            Area area = areaRepository.findById(vacante.getArea().getId())
                    .orElseThrow(() -> new RuntimeException("Área no encontrada"));
            vacante.setArea(area);
        }

        // Validar que la modalidad existe
        if (vacante.getModalidad() != null && vacante.getModalidad().getId() != null) {
            Modalidad modalidad = modalidadRepository.findById(vacante.getModalidad().getId())
                    .orElseThrow(() -> new RuntimeException("Modalidad no encontrada"));
            vacante.setModalidad(modalidad);
        }

        return vacanteRepository.save(vacante);
    }

    @Transactional
    public Vacante actualizar(Long id, Vacante vacanteActualizada) {
        return vacanteRepository.findById(id)
                .map(vacante -> {
                    vacante.setTitulo(vacanteActualizada.getTitulo());
                    vacante.setDescripcion(vacanteActualizada.getDescripcion());

                    // Actualizar área si cambió
                    if (vacanteActualizada.getArea() != null && vacanteActualizada.getArea().getId() != null) {
                        Area area = areaRepository.findById(vacanteActualizada.getArea().getId())
                                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
                        vacante.setArea(area);
                    }

                    vacante.setSalario(vacanteActualizada.getSalario());
                    vacante.setUbicacion(vacanteActualizada.getUbicacion());
                    vacante.setTipoContrato(vacanteActualizada.getTipoContrato());
                    vacante.setSolicitudesPermitidas(vacanteActualizada.getSolicitudesPermitidas());
                    vacante.setEstado(vacanteActualizada.getEstado());
                    vacante.setFechaExpiracion(vacanteActualizada.getFechaExpiracion());
                    vacante.setBeneficios(vacanteActualizada.getBeneficios());
                    vacante.setEmpresa(vacanteActualizada.getEmpresa());
                    vacante.setHoraInicio(vacanteActualizada.getHoraInicio());
                    vacante.setHoraFin(vacanteActualizada.getHoraFin());
                    vacante.setDiasLaborales(vacanteActualizada.getDiasLaborales());
                    vacante.setHorasPorSemana(vacanteActualizada.getHorasPorSemana());

                    // Actualizar modalidad si cambió
                    if (vacanteActualizada.getModalidad() != null && vacanteActualizada.getModalidad().getId() != null) {
                        Modalidad modalidad = modalidadRepository.findById(vacanteActualizada.getModalidad().getId())
                                .orElseThrow(() -> new RuntimeException("Modalidad no encontrada"));
                        vacante.setModalidad(modalidad);
                    }

                    vacante.setTurno(vacanteActualizada.getTurno());
                    vacante.setHorarioFlexible(vacanteActualizada.getHorarioFlexible());

                    return vacanteRepository.save(vacante);
                })
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada con id: " + id));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!vacanteRepository.existsById(id)) {
            throw new RuntimeException("Vacante no encontrada con id: " + id);
        }
        vacanteRepository.deleteById(id);
    }

    // ==================== CONSULTAS ESPECIALES ====================

    public List<Vacante> obtenerPorEstado(String estado) {
        return vacanteRepository.findByEstado(estado);
    }

    public List<Vacante> obtenerActivas() {
        return vacanteRepository.findVacantesActivas(LocalDateTime.now());
    }

    public List<Vacante> obtenerPorArea(Long areaId) {
        return vacanteRepository.findByAreaId(areaId);
    }

    public List<Vacante> obtenerPorEmpresa(String empresa) {
        return vacanteRepository.findByEmpresa(empresa);
    }

    public List<Vacante> obtenerPorRangoSalario(Double min, Double max) {
        return vacanteRepository.findBySalarioRange(min, max);
    }

    public List<Vacante> buscarPorPalabraClave(String keyword) {
        return vacanteRepository.buscarPorPalabraClave(keyword);
    }

    // ==================== GESTIÓN DE HABILIDADES ====================

    @Transactional
    public Vacante agregarHabilidad(Long vacanteId, Long habilidadId) {
        Vacante vacante = vacanteRepository.findById(vacanteId)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));
        Habilidades habilidad = habilidadesRepository.findById(habilidadId)
                .orElseThrow(() -> new RuntimeException("Habilidad no encontrada"));

        // Verificar que no esté ya agregada
        if (!vacante.getHabilidades().contains(habilidad)) {
            vacante.agregarHabilidad(habilidad);
            return vacanteRepository.save(vacante);
        }
        return vacante;
    }

    @Transactional
    public Vacante removerHabilidad(Long vacanteId, Long habilidadId) {
        Vacante vacante = vacanteRepository.findById(vacanteId)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));
        Habilidades habilidad = habilidadesRepository.findById(habilidadId)
                .orElseThrow(() -> new RuntimeException("Habilidad no encontrada"));

        vacante.removerHabilidad(habilidad);
        return vacanteRepository.save(vacante);
    }

    @Transactional
    public Vacante establecerHabilidades(Long vacanteId, List<Long> habilidadIds) {
        Vacante vacante = vacanteRepository.findById(vacanteId)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));

        List<Habilidades> habilidades = habilidadesRepository.findAllById(habilidadIds);
        vacante.setHabilidades(habilidades);

        return vacanteRepository.save(vacante);
    }

    public List<Vacante> obtenerPorHabilidad(Long habilidadId) {
        return vacanteRepository.findByHabilidadId(habilidadId);
    }

    // ==================== GESTIÓN DE IDIOMAS ====================

    @Transactional
    public Vacante agregarIdioma(Long vacanteId, Long idiomaId) {
        Vacante vacante = vacanteRepository.findById(vacanteId)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));
        Idiomas idioma = idiomasRepository.findById(idiomaId)
                .orElseThrow(() -> new RuntimeException("Idioma no encontrado"));

        // Verificar que no esté ya agregado
        if (!vacante.getIdiomas().contains(idioma)) {
            vacante.agregarIdioma(idioma);
            return vacanteRepository.save(vacante);
        }
        return vacante;
    }

    @Transactional
    public Vacante removerIdioma(Long vacanteId, Long idiomaId) {
        Vacante vacante = vacanteRepository.findById(vacanteId)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));
        Idiomas idioma = idiomasRepository.findById(idiomaId)
                .orElseThrow(() -> new RuntimeException("Idioma no encontrado"));

        vacante.removerIdioma(idioma);
        return vacanteRepository.save(vacante);
    }

    @Transactional
    public Vacante establecerIdiomas(Long vacanteId, List<Long> idiomaIds) {
        Vacante vacante = vacanteRepository.findById(vacanteId)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));

        List<Idiomas> idiomas = idiomasRepository.findAllById(idiomaIds);
        vacante.setIdiomas(idiomas);

        return vacanteRepository.save(vacante);
    }

    public List<Vacante> obtenerPorIdioma(Long idiomaId) {
        return vacanteRepository.findByIdiomaId(idiomaId);
    }

    // ==================== MÉTODOS DE NEGOCIO ====================

    @Transactional
    public Vacante cambiarEstado(Long id, String nuevoEstado) {
        Vacante vacante = vacanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));
        vacante.setEstado(nuevoEstado);
        return vacanteRepository.save(vacante);
    }

    @Transactional
    public Vacante cerrarVacante(Long id) {
        return cambiarEstado(id, "CERRADA");
    }

    @Transactional
    public Vacante activarVacante(Long id) {
        return cambiarEstado(id, "ACTIVA");
    }

    public boolean estaDisponible(Long id) {
        return vacanteRepository.findById(id)
                .map(Vacante::isDisponible)
                .orElse(false);
    }
}*/