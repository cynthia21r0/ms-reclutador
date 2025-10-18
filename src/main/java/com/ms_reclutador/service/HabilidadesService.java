/*package com.ms_reclutador.service;

import com.ms_reclutador.model.Habilidades;
import com.ms_reclutador.repository.HabilidadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HabilidadesService {

    @Autowired
    private HabilidadesRepository habilidadesRepository;

    public List<Habilidades> obtenerTodas() {
        return habilidadesRepository.findAll();
    }

    public Optional<Habilidades> obtenerPorId(Long id) {
        return habilidadesRepository.findById(id);
    }

    public List<Habilidades> obtenerPorArea(Long areaId) {
        return habilidadesRepository.findByAreaId(areaId);
    }

    public Optional<Habilidades> obtenerPorNombre(String nombre) {
        return habilidadesRepository.findByNombre(nombre);
    }

    @Transactional
    public Habilidades crear(Habilidades habilidad) {
        if (habilidadesRepository.existsByNombre(habilidad.getNombre())) {
            throw new RuntimeException("Ya existe una habilidad con ese nombre");
        }
        return habilidadesRepository.save(habilidad);
    }

    @Transactional
    public Habilidades actualizar(Long id, Habilidades habilidadActualizada) {
        return habilidadesRepository.findById(id)
                .map(habilidad -> {
                    habilidad.setNombre(habilidadActualizada.getNombre());
                    habilidad.setArea(habilidadActualizada.getArea());
                    return habilidadesRepository.save(habilidad);
                })
                .orElseThrow(() -> new RuntimeException("Habilidad no encontrada con id: " + id));
    }

    @Transactional
    public void eliminar(Long id) {
        habilidadesRepository.deleteById(id);
    }
}*/