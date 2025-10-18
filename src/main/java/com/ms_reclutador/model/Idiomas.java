package com.ms_reclutador.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "idiomas")
@Schema(description = "Entidad que representa un idioma")
public class Idiomas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del idioma", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del idioma", example = "Inglés", requiredMode = Schema.RequiredMode.REQUIRED)
    private String idioma;

    // Getters y Setters
    public Long getId() { return id; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
}