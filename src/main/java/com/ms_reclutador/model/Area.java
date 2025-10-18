package com.ms_reclutador.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "areas")
@Schema(description = "Entidad que representa un área de especialización")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del área", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del área", example = "Tecnología", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(description = "Habilidades asociadas al área")
    private Set<Habilidades> habilidades = new HashSet<>();

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(description = "Vacantes asociadas al área")
    private Set<Vacante> vacantes = new HashSet<>();

    // Getters y Setters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Set<Habilidades> getHabilidades() { return habilidades; }
    public void setHabilidades(Set<Habilidades> habilidades) { this.habilidades = habilidades; }
    public Set<Vacante> getVacantes() { return vacantes; }
    public void setVacantes(Set<Vacante> vacantes) { this.vacantes = vacantes; }
}