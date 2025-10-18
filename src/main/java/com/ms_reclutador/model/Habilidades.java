package com.ms_reclutador.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "habilidades")
@Schema(description = "Entidad que representa una habilidad técnica")
public class Habilidades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la habilidad", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre de la habilidad", example = "Java Spring Boot", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    @JsonIgnore
    @Schema(description = "Área a la que pertenece la habilidad")
    private Area area;

    // Getters y Setters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Area getArea() { return area; }
    public void setArea(Area area) { this.area = area; }
}