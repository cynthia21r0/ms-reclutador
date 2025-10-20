package com.ms_reclutador.dto;

import com.ms_reclutador.model.Modalidad;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para modalidad")
public class ModalidadDTO {

    @Schema(description = "ID único de la modalidad", example = "1")
    private Long id;

    @Schema(description = "Nombre de la modalidad", example = "Presencial")
    private String nombre;

    public ModalidadDTO(Modalidad modalidad) {
        this.id = modalidad.getId();
        this.nombre = modalidad.getNombre();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}