package com.ms_reclutador.repository;

import com.ms_reclutador.model.Idiomas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdiomasRepository extends JpaRepository<Idiomas, Long> {
    Optional<Idiomas> findByIdioma(String idioma);
    boolean existsByIdioma(String idioma);
}
/*package com.ms_reclutador.repository;

import com.ms_reclutador.model.Idiomas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdiomaRepository extends JpaRepository<Idiomas, Long> {

    Optional<Idiomas> findByIdioma(String idioma);

    boolean existsByIdioma(String idioma);
}*/