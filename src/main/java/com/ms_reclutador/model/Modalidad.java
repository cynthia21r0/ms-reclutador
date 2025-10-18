package com.ms_reclutador.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "modalidades")
@Schema(description = "Entidad que representa una modalidad de trabajo")
public class Modalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la modalidad", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre de la modalidad", example = "Presencial", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    // Getters y Setters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}