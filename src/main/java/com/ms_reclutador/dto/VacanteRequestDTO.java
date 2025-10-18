package com.ms_reclutador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Schema(description = "DTO para la creación y actualización de vacantes")
public class VacanteRequestDTO {

    @Schema(description = "Título de la vacante", example = "Desarrollador Backend Java Senior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String titulo;

    @Schema(description = "Descripción detallada de la vacante", example = "Estamos buscando un desarrollador backend con experiencia en Java Spring Boot...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    @Schema(description = "Salario ofrecido", example = "45000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double salario;

    @Schema(description = "Ubicación del trabajo", example = "Ciudad de México", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ubicacion;

    @Schema(description = "Tipo de contrato", example = "Tiempo completo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tipoContrato;

    @Schema(description = "Número máximo de solicitudes permitidas", example = "50", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer solicitudesPermitidas;

    @Schema(description = "Estado de la vacante", example = "ACTIVA", allowableValues = {"ACTIVA", "INACTIVA", "CANCELADA", "CERRADA"})
    private String estado;

    @Schema(description = "Fecha de expiración de la vacante", example = "2024-12-31T23:59:59")
    private LocalDateTime fechaExpiracion;

    @Schema(description = "Beneficios ofrecidos", example = "Seguro médico, vales de despensa, fondo de ahorro")
    private String beneficios;

    @Schema(description = "Nombre de la empresa", example = "Tech Solutions SA de CV", requiredMode = Schema.RequiredMode.REQUIRED)
    private String empresa;

    @Schema(description = "Hora de inicio de la jornada", example = "09:00:00")
    private LocalTime horaInicio;

    @Schema(description = "Hora de fin de la jornada", example = "18:00:00")
    private LocalTime horaFin;

    @Schema(description = "Días laborales", example = "Lunes a Viernes")
    private String diasLaborales;

    @Schema(description = "Horas por semana", example = "40")
    private Integer horasPorSemana;

    @Schema(description = "Turno de trabajo", example = "Matutino", allowableValues = {"Matutino", "Vespertino", "Nocturno", "Mixto", "Flexible"})
    private String turno;

    @Schema(description = "Indica si el horario es flexible", example = "true")
    private Boolean horarioFlexible;

    @Schema(description = "ID del área", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long areaId;

    @Schema(description = "ID de la modalidad", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long modalidadId;

    @Schema(description = "IDs de las habilidades requeridas", example = "[1, 2, 3]")
    private Set<Long> habilidadesIds;

    @Schema(description = "IDs de los idiomas requeridos", example = "[1, 2]")
    private Set<Long> idiomasIds;

    // Getters y Setters (mantener los mismos)
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }
    public Integer getSolicitudesPermitidas() { return solicitudesPermitidas; }
    public void setSolicitudesPermitidas(Integer solicitudesPermitidas) { this.solicitudesPermitidas = solicitudesPermitidas; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDateTime fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
    public String getBeneficios() { return beneficios; }
    public void setBeneficios(String beneficios) { this.beneficios = beneficios; }
    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }
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
    public Long getAreaId() { return areaId; }
    public void setAreaId(Long areaId) { this.areaId = areaId; }
    public Long getModalidadId() { return modalidadId; }
    public void setModalidadId(Long modalidadId) { this.modalidadId = modalidadId; }
    public Set<Long> getHabilidadesIds() { return habilidadesIds; }
    public void setHabilidadesIds(Set<Long> habilidadesIds) { this.habilidadesIds = habilidadesIds; }
    public Set<Long> getIdiomasIds() { return idiomasIds; }
    public void setIdiomasIds(Set<Long> idiomasIds) { this.idiomasIds = idiomasIds; }
}