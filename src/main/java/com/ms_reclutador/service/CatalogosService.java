package com.ms_reclutador.service;

import com.ms_reclutador.model.*;
import com.ms_reclutador.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CatalogosService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private HabilidadesRepository habilidadesRepository;

    @Autowired
    private IdiomasRepository idiomasRepository;

    @Autowired
    private ModalidadRepository modalidadRepository;

    // Áreas
    public List<Area> obtenerTodasLasAreas() {
        return areaRepository.findAll();
    }

    public Area obtenerAreaPorId(Long id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
    }

    public Area crearArea(Area area) {
        if (areaRepository.existsByNombre(area.getNombre())) {
            throw new RuntimeException("El área ya existe");
        }
        return areaRepository.save(area);
    }

    // Habilidades
    public List<Habilidades> obtenerTodasLasHabilidades() {
        return habilidadesRepository.findAll();
    }

    public List<Habilidades> obtenerHabilidadesPorArea(Long areaId) {
        return habilidadesRepository.findByAreaId(areaId);
    }

    public Habilidades crearHabilidad(Habilidades habilidad) {
        if (habilidadesRepository.existsByNombreAndAreaId(habilidad.getNombre(), habilidad.getArea().getId())) {
            throw new RuntimeException("La habilidad ya existe en esta área");
        }
        return habilidadesRepository.save(habilidad);
    }

    // Idiomas
    public List<Idiomas> obtenerTodosLosIdiomas() {
        return idiomasRepository.findAll();
    }

    public Idiomas crearIdioma(Idiomas idioma) {
        if (idiomasRepository.existsByIdioma(idioma.getIdioma())) {
            throw new RuntimeException("El idioma ya existe");
        }
        return idiomasRepository.save(idioma);
    }

    // Modalidades
    public List<Modalidad> obtenerTodasLasModalidades() {
        return modalidadRepository.findAll();
    }

    public Modalidad crearModalidad(Modalidad modalidad) {
        if (modalidadRepository.existsByNombre(modalidad.getNombre())) {
            throw new RuntimeException("La modalidad ya existe");
        }
        return modalidadRepository.save(modalidad);
    }
}