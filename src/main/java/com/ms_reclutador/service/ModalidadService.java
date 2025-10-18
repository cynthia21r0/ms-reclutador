/*package com.ms_reclutador.service;

import com.ms_reclutador.model.Modalidad;
import com.ms_reclutador.repository.ModalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ModalidadService {

    @Autowired
    private ModalidadRepository modalidadRepository;

    public List<Modalidad> obtenerTodas() {
        return modalidadRepository.findAll();
    }

    public Optional<Modalidad> obtenerPorId(Long id) {
        return modalidadRepository.findById(id);
    }

    public Optional<Modalidad> obtenerPorNombre(String nombre) {
        return modalidadRepository.findByNombre(nombre);
    }

    @Transactional
    public Modalidad crear(Modalidad modalidad) {
        if (modalidadRepository.existsByNombre(modalidad.getNombre())) {
            throw new RuntimeException("Ya existe una modalidad con ese nombre");
        }
        return modalidadRepository.save(modalidad);
    }

    @Transactional
    public Modalidad actualizar(Long id, Modalidad modalidadActualizada) {
        return modalidadRepository.findById(id)
                .map(modalidad -> {
                    modalidad.setNombre(modalidadActualizada.getNombre());
                    return modalidadRepository.save(modalidad);
                })
                .orElseThrow(() -> new RuntimeException("Modalidad no encontrada con id: " + id));
    }

    @Transactional
    public void eliminar(Long id) {
        modalidadRepository.deleteById(id);
    }
}*/