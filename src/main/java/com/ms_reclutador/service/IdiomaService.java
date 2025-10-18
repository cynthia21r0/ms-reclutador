/*package com.ms_reclutador.service;

import com.ms_reclutador.model.Idiomas;
import com.ms_reclutador.repository.IdiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class IdiomaService {

    @Autowired
    private IdiomaRepository idiomasRepository;

    public List<Idiomas> obtenerTodos() {
        return idiomasRepository.findAll();
    }

    public Optional<Idiomas> obtenerPorId(Long id) {
        return idiomasRepository.findById(id);
    }

    public Optional<Idiomas> obtenerPorNombre(String idioma) {
        return idiomasRepository.findByIdioma(idioma);
    }

    @Transactional
    public Idiomas crear(Idiomas idioma) {
        if (idiomasRepository.existsByIdioma(idioma.getIdioma())) {
            throw new RuntimeException("Ya existe ese idioma");
        }
        return idiomasRepository.save(idioma);
    }

    @Transactional
    public Idiomas actualizar(Long id, Idiomas idiomaActualizado) {
        return idiomasRepository.findById(id)
                .map(idioma -> {
                    idioma.setIdioma(idiomaActualizado.getIdioma());
                    return idiomasRepository.save(idioma);
                })
                .orElseThrow(() -> new RuntimeException("Idioma no encontrado con id: " + id));
    }

    @Transactional
    public void eliminar(Long id) {
        idiomasRepository.deleteById(id);
    }
}*/