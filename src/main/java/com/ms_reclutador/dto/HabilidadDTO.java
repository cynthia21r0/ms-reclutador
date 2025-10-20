package com.ms_reclutador.dto;

import com.ms_reclutador.model.Habilidades;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para habilidad")
public class HabilidadDTO {

    @Schema(description = "ID único de la habilidad", example = "1")
    private Long id;

    @Schema(description = "Nombre de la habilidad", example = "Java Spring Boot")
    private String nombre;

    @Schema(description = "Área de la habilidad")
    private AreaDTO area;

    public HabilidadDTO(Habilidades habilidad) {
        this.id = habilidad.getId();
        this.nombre = habilidad.getNombre();
        if (habilidad.getArea() != null) {
            this.area = new AreaDTO(habilidad.getArea());
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public AreaDTO getArea() { return area; }
    public void setArea(AreaDTO area) { this.area = area; }
}