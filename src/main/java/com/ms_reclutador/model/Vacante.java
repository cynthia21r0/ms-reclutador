package com.ms_reclutador.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vacantes")
@Schema(description = "Entidad que representa una vacante laboral")
public class Vacante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la vacante", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Título de la vacante", example = "Desarrollador Backend Java Senior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String titulo;

    @Column(nullable = false, length = 1000)
    @Schema(description = "Descripción detallada de la vacante", example = "Estamos buscando un desarrollador backend con experiencia en Java Spring Boot...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Salario ofrecido", example = "45000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double salario;

    @Column(nullable = false)
    @Schema(description = "Tipo de contrato", example = "Tiempo completo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tipoContrato;

    @Column(nullable = false)
    @Schema(description = "Número máximo de solicitudes permitidas", example = "50", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer solicitudesPermitidas;

    @Column(nullable = false)
    @Schema(description = "Estado de la vacante", example = "ACTIVA", allowableValues = {"ACTIVA", "INACTIVA", "CANCELADA", "CERRADA"})
    private String estado = "ACTIVA";

    @Column(nullable = false)
    @Schema(description = "Fecha de creación de la vacante", example = "2024-01-15T10:30:00")
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    @Schema(description = "Fecha de expiración de la vacante", example = "2024-12-31T23:59:59", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime fechaExpiracion;

    @Column(length = 1000)
    @Schema(description = "Beneficios ofrecidos", example = "Seguro médico, vales de despensa, fondo de ahorro")
    private String beneficios;

    @Column
    @Schema(description = "Hora de inicio de la jornada", example = "09:00:00")
    private LocalTime horaInicio;

    @Column
    @Schema(description = "Hora de fin de la jornada", example = "18:00:00")
    private LocalTime horaFin;

    @Column(length = 100)
    @Schema(description = "Días laborales", example = "Lunes a Viernes")
    private String diasLaborales;

    @Column
    @Schema(description = "Horas por semana", example = "40")
    private Integer horasPorSemana;

    @Column(length = 50)
    @Schema(description = "Turno de trabajo", example = "Matutino", allowableValues = {"Matutino", "Vespertino", "Nocturno", "Mixto", "Flexible"})
    private String turno;

    @Column(nullable = false)
    @Schema(description = "Indica si el horario es flexible", example = "true")
    private Boolean horarioFlexible = false;

    // 🔹 Relaciones
    @Column(name = "empresa_id") // 🔹 Esto debe coincidir EXACTAMENTE con el nombre en la BD
    @Schema(description = "ID de la empresa", example = "2")
    private Long empresaId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id", nullable = false)
    @JsonIgnore
    @Schema(description = "Área de la vacante")
    private Area area;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modalidad_id", nullable = false)
    @JsonIgnore
    @Schema(description = "Modalidad de trabajo")
    private Modalidad modalidad;

    @ManyToMany
    @JoinTable(
            name = "vacantes_habilidades",
            joinColumns = @JoinColumn(name = "vacante_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidad_id")
    )
    @JsonIgnore
    @Schema(description = "Conjunto de habilidades requeridas")
    private Set<Habilidades> habilidades = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "vacantes_idiomas",
            joinColumns = @JoinColumn(name = "vacante_id"),
            inverseJoinColumns = @JoinColumn(name = "idioma_id")
    )
    @JsonIgnore
    @Schema(description = "Conjunto de idiomas requeridos")
    private Set<Idiomas> idiomas = new HashSet<>();

    // Getters y Setters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }
    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }
    public Integer getSolicitudesPermitidas() { return solicitudesPermitidas; }
    public void setSolicitudesPermitidas(Integer solicitudesPermitidas) { this.solicitudesPermitidas = solicitudesPermitidas; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDateTime fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
    public String getBeneficios() { return beneficios; }
    public void setBeneficios(String beneficios) { this.beneficios = beneficios; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public String getDiasLaborales() { return diasLaborales; }
    public void setDiasLaborales(String diasLaborales) { this.diasLaborales = diasLaborales; }
    public Integer getHorasPorSemana() { return horasPorSemana; }
    public void setHorasPorSemana(Integer horasPorSemana) { this.horasPorSemana = horasPorSemana; }
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    public Boolean getHorarioFlexible() { return horarioFlexible; }
    public void setHorarioFlexible(Boolean horarioFlexible) { this.horarioFlexible = horarioFlexible; }
    public Area getArea() { return area; }
    public void setArea(Area area) { this.area = area; }
    public Modalidad getModalidad() { return modalidad; }
    public void setModalidad(Modalidad modalidad) { this.modalidad = modalidad; }
    public Set<Habilidades> getHabilidades() { return habilidades; }
    public void setHabilidades(Set<Habilidades> habilidades) { this.habilidades = habilidades; }
    public Set<Idiomas> getIdiomas() { return idiomas; }
    public void setIdiomas(Set<Idiomas> idiomas) { this.idiomas = idiomas; }
    public void setId(Long id) {this.id = id;}
    public Long getEmpresaId() {return empresaId;}
    public void setEmpresaId(Long empresa_id) {this.empresaId = empresa_id;}
}