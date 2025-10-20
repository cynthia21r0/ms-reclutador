package com.ms_reclutador.dto;

import com.ms_reclutador.model.Area;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para área")
public class AreaDTO {

    @Schema(description = "ID único del área", example = "1")
    private Long id;

    @Schema(description = "Nombre del área", example = "Tecnología")
    private String nombre;

    public AreaDTO(Area area) {
        this.id = area.getId();
        this.nombre = area.getNombre();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}