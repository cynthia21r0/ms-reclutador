/*package com.ms_reclutador.service;

import com.ms_reclutador.model.Area;
import com.ms_reclutador.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    public List<Area> obtenerTodas() {
        return areaRepository.findAll();
    }

    public Optional<Area> obtenerPorId(Long id) {
        return areaRepository.findById(id);
    }

    public Optional<Area> obtenerPorNombre(String nombre) {
        return areaRepository.findByNombre(nombre);
    }

    @Transactional
    public Area crear(Area area) {
        if (areaRepository.existsByNombre(area.getNombre())) {
            throw new RuntimeException("Ya existe un área con ese nombre");
        }
        return areaRepository.save(area);
    }

    @Transactional
    public Area actualizar(Long id, Area areaActualizada) {
        return areaRepository.findById(id)
                .map(area -> {
                    area.setNombre(areaActualizada.getNombre());
                    return areaRepository.save(area);
                })
                .orElseThrow(() -> new RuntimeException("Área no encontrada con id: " + id));
    }

    @Transactional
    public void eliminar(Long id) {
        areaRepository.deleteById(id);
    }
}*/