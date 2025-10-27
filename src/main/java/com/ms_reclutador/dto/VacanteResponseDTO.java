package com.ms_reclutador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Schema(description = "DTO para respuesta completa de vacante con todas las relaciones")
public class VacanteResponseDTO {

    @Schema(description = "ID único de la vacante", example = "1")
    private Long id;

    @Schema(description = "Título de la vacante", example = "Desarrollador Backend Java Senior")
    private String titulo;

    @Schema(description = "Descripción detallada de la vacante")
    private String descripcion;

    @Schema(description = "Salario ofrecido", example = "45000.00")
    private Double salario;

    @Schema(description = "Tipo de contrato", example = "Tiempo completo")
    private String tipoContrato;

    @Schema(description = "Número máximo de solicitudes permitidas", example = "50")
    private Integer solicitudesPermitidas;

    @Schema(description = "Estado de la vacante", example = "ACTIVA")
    private String estado;

    @Schema(description = "Fecha de creación de la vacante")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de expiración de la vacante")
    private LocalDateTime fechaExpiracion;

    @Schema(description = "Beneficios ofrecidos")
    private String beneficios;

    @Schema(description = "Hora de inicio de la jornada", example = "09:00:00")
    private LocalTime horaInicio;

    @Schema(description = "Hora de fin de la jornada", example = "18:00:00")
    private LocalTime horaFin;

    @Schema(description = "Días laborales", example = "Lunes a Viernes")
    private String diasLaborales;

    @Schema(description = "Horas por semana", example = "40")
    private Integer horasPorSemana;

    @Schema(description = "Turno de trabajo", example = "Matutino")
    private String turno;

    @Schema(description = "Indica si el horario es flexible", example = "true")
    private Boolean horarioFlexible;

    @Schema(description = "ID de la empresa", example = "2")
    private Long empresaId;

    @Schema(description = "Área de la vacante")
    private AreaDTO area;

    @Schema(description = "Modalidad de trabajo")
    private ModalidadDTO modalidad;

    @Schema(description = "Lista de habilidades requeridas")
    private List<HabilidadDTO> habilidades;

    @Schema(description = "Lista de idiomas requeridos")
    private List<IdiomaDTO> idiomas;

    // Constructores
    public VacanteResponseDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public AreaDTO getArea() { return area; }
    public void setArea(AreaDTO area) { this.area = area; }
    public ModalidadDTO getModalidad() { return modalidad; }
    public void setModalidad(ModalidadDTO modalidad) { this.modalidad = modalidad; }
    public List<HabilidadDTO> getHabilidades() { return habilidades; }
    public void setHabilidades(List<HabilidadDTO> habilidades) { this.habilidades = habilidades; }
    public List<IdiomaDTO> getIdiomas() { return idiomas; }
    public void setIdiomas(List<IdiomaDTO> idiomas) { this.idiomas = idiomas; }
    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }

    // DTOs internos para las relaciones
    public static class AreaDTO {
        private Long id;
        private String nombre;

        public AreaDTO() {}
        public AreaDTO(Long id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }
        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }

    public static class ModalidadDTO {
        private Long id;
        private String nombre;

        public ModalidadDTO() {}
        public ModalidadDTO(Long id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }
        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }

    public static class HabilidadDTO {
        private Long id;
        private String nombre;
        private Long areaId;

        public HabilidadDTO() {}
        public HabilidadDTO(Long id, String nombre, Long areaId) {
            this.id = id;
            this.nombre = nombre;
            this.areaId = areaId;
        }
        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public Long getAreaId() { return areaId; }
        public void setAreaId(Long areaId) { this.areaId = areaId; }
    }

    public static class IdiomaDTO {
        private Long id;
        private String idioma;

        public IdiomaDTO() {}
        public IdiomaDTO(Long id, String idioma) {
            this.id = id;
            this.idioma = idioma;
        }
        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getIdioma() { return idioma; }
        public void setIdioma(String idioma) { this.idioma = idioma; }
    }
}