package com.ms_reclutador.service;

import com.ms_reclutador.dto.VacanteResponseDTO;
import com.ms_reclutador.model.*;
import com.ms_reclutador.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
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
    // Obtener vacante completa por ID
    @Transactional(readOnly = true)
    public VacanteResponseDTO obtenerVacanteCompletaPorId(Long id) {
        List<Object[]> results = vacanteRepository.findVacanteCompletaById(id);

        if (results.isEmpty()) {
            throw new RuntimeException("Vacante no encontrada con ID: " + id);
        }

        return mapearResultadoAVacanteCompleta(results);
    }

    // Obtener todas las vacantes completas
    @Transactional(readOnly = true)
    public List<VacanteResponseDTO> obtenerTodasLasVacantesCompletas() {
        List<Object[]> results = vacanteRepository.findAllVacantesCompletas();
        return mapearResultadosAVacantesCompletas(results);
    }

    // Obtener vacantes completas por empresa
    @Transactional(readOnly = true)
    public List<VacanteResponseDTO> obtenerVacantesCompletasPorEmpresa(String empresa) {
        List<Object[]> results = vacanteRepository.findVacantesCompletasByEmpresa(empresa);
        return mapearResultadosAVacantesCompletas(results);
    }

    // Método auxiliar para mapear resultados
    private VacanteResponseDTO mapearResultadoAVacanteCompleta(List<Object[]> results) {
        if (results.isEmpty()) return null;

        Object[] firstRow = results.get(0);
        VacanteResponseDTO dto = new VacanteResponseDTO();

        // Mapear datos básicos de la vacante (primer registro)
        dto.setId(((Number) firstRow[0]).longValue());
        dto.setTitulo((String) firstRow[1]);
        dto.setDescripcion((String) firstRow[2]);
        dto.setSalario(((Number) firstRow[3]).doubleValue());
        dto.setUbicacion((String) firstRow[4]);
        dto.setTipoContrato((String) firstRow[5]);
        dto.setSolicitudesPermitidas(((Number) firstRow[6]).intValue());
        dto.setEstado((String) firstRow[7]);

        // Convertir Timestamp a LocalDateTime
        dto.setFechaCreacion(convertTimestampToLocalDateTime(firstRow[8]));
        dto.setFechaExpiracion(convertTimestampToLocalDateTime(firstRow[9]));

        dto.setBeneficios((String) firstRow[10]);
        dto.setEmpresa((String) firstRow[11]);

        // Convertir Time a LocalTime
        dto.setHoraInicio(convertTimeToLocalTime(firstRow[12]));
        dto.setHoraFin(convertTimeToLocalTime(firstRow[13]));

        dto.setDiasLaborales((String) firstRow[14]);
        dto.setHorasPorSemana(firstRow[15] != null ? ((Number) firstRow[15]).intValue() : null);
        dto.setTurno((String) firstRow[16]);
        dto.setHorarioFlexible((Boolean) firstRow[17]);

        // Mapear área
        if (firstRow[18] != null) {
            dto.setArea(new VacanteResponseDTO.AreaDTO(
                    ((Number) firstRow[18]).longValue(),
                    (String) firstRow[19]
            ));
        }

        // Mapear modalidad
        if (firstRow[20] != null) {
            dto.setModalidad(new VacanteResponseDTO.ModalidadDTO(
                    ((Number) firstRow[20]).longValue(),
                    (String) firstRow[21]
            ));
        }

        // Mapear habilidades e idiomas (pueden venir en múltiples registros)
        List<VacanteResponseDTO.HabilidadDTO> habilidades = new ArrayList<>();
        List<VacanteResponseDTO.IdiomaDTO> idiomas = new ArrayList<>();

        for (Object[] row : results) {
            // Habilidades
            if (row[22] != null) {
                VacanteResponseDTO.HabilidadDTO habilidad = new VacanteResponseDTO.HabilidadDTO(
                        ((Number) row[22]).longValue(),
                        (String) row[23],
                        row[24] != null ? ((Number) row[24]).longValue() : null
                );
                // Evitar duplicados
                if (habilidades.stream().noneMatch(h -> h.getId().equals(habilidad.getId()))) {
                    habilidades.add(habilidad);
                }
            }

            // Idiomas
            if (row[25] != null) {
                VacanteResponseDTO.IdiomaDTO idioma = new VacanteResponseDTO.IdiomaDTO(
                        ((Number) row[25]).longValue(),
                        (String) row[26]
                );
                // Evitar duplicados
                if (idiomas.stream().noneMatch(i -> i.getId().equals(idioma.getId()))) {
                    idiomas.add(idioma);
                }
            }
        }

        dto.setHabilidades(habilidades);
        dto.setIdiomas(idiomas);

        return dto;
    }

    private List<VacanteResponseDTO> mapearResultadosAVacantesCompletas(List<Object[]> results) {
        Map<Long, VacanteResponseDTO> vacantesMap = new HashMap<>();

        for (Object[] row : results) {
            Long vacanteId = ((Number) row[0]).longValue();

            if (!vacantesMap.containsKey(vacanteId)) {
                VacanteResponseDTO dto = new VacanteResponseDTO();

                // Mapear datos básicos
                dto.setId(vacanteId);
                dto.setTitulo((String) row[1]);
                dto.setDescripcion((String) row[2]);
                dto.setSalario(((Number) row[3]).doubleValue());
                dto.setUbicacion((String) row[4]);
                dto.setTipoContrato((String) row[5]);
                dto.setSolicitudesPermitidas(((Number) row[6]).intValue());
                dto.setEstado((String) row[7]);

                // Convertir Timestamp a LocalDateTime
                dto.setFechaCreacion(convertTimestampToLocalDateTime(row[8]));
                dto.setFechaExpiracion(convertTimestampToLocalDateTime(row[9]));

                dto.setBeneficios((String) row[10]);
                dto.setEmpresa((String) row[11]);

                // Convertir Time a LocalTime
                dto.setHoraInicio(convertTimeToLocalTime(row[12]));
                dto.setHoraFin(convertTimeToLocalTime(row[13]));

                dto.setDiasLaborales((String) row[14]);
                dto.setHorasPorSemana(row[15] != null ? ((Number) row[15]).intValue() : null);
                dto.setTurno((String) row[16]);
                dto.setHorarioFlexible((Boolean) row[17]);

                // Mapear área
                if (row[18] != null) {
                    dto.setArea(new VacanteResponseDTO.AreaDTO(
                            ((Number) row[18]).longValue(),
                            (String) row[19]
                    ));
                }

                // Mapear modalidad
                if (row[20] != null) {
                    dto.setModalidad(new VacanteResponseDTO.ModalidadDTO(
                            ((Number) row[20]).longValue(),
                            (String) row[21]
                    ));
                }

                dto.setHabilidades(new ArrayList<>());
                dto.setIdiomas(new ArrayList<>());
                vacantesMap.put(vacanteId, dto);
            }
        }

        return new ArrayList<>(vacantesMap.values());
    }

    // 🔹 Métodos auxiliares para conversión de tipos
    private LocalDateTime convertTimestampToLocalDateTime(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDateTime) {
            return (LocalDateTime) value;
        }
        if (value instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) value).toLocalDateTime();
        }
        return null;
    }

    private LocalTime convertTimeToLocalTime(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalTime) {
            return (LocalTime) value;
        }
        if (value instanceof java.sql.Time) {
            return ((java.sql.Time) value).toLocalTime();
        }
        return null;
    }}