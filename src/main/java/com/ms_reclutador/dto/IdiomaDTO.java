package com.ms_reclutador.dto;

import com.ms_reclutador.model.Idiomas;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para idioma")
public class IdiomaDTO {

    @Schema(description = "ID único del idioma", example = "1")
    private Long id;

    @Schema(description = "Nombre del idioma", example = "Inglés")
    private String idioma;

    public IdiomaDTO(Idiomas idioma) {
        this.id = idioma.getId();
        this.idioma = idioma.getIdioma();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
}