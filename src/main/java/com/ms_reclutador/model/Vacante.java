package com.ms_reclutador.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "vacantes", schema = "reclutador")
public class Vacante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private String departamento;

    @Column(nullable = false)
    private Double salario;

    @Column(nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private String tipoContrato;

    @Column(nullable = false)
    private Integer solicitudesPermitidas;

    @Column(nullable = false)
    private Integer solicitudesRecibidas = 0;

    @Column(nullable = false)
    private String estado = "ACTIVA";

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaExpiracion;

    @Column
    private String requisitos;

    @Column
    private String beneficios;

    @Column(nullable = false)
    private String empresa;

    // Campos de horario dinámico
    @Column
    private LocalTime horaInicio; // Ejemplo: 09:00:00

    @Column
    private LocalTime horaFin; // Ejemplo: 18:00:00

    @Column
    private String diasLaborales; // Ejemplo: "Lunes,Martes,Miércoles,Jueves,Viernes" o "L-V"

    @Column
    private Integer horasPorSemana; // Total de horas semanales

    @Column
    private String modalidad; // "Presencial", "Remoto", "Híbrido"

    @Column
    private String turno; // "Matutino", "Vespertino", "Nocturno", "Mixto", "Flexible"

    @Column
    private Boolean horarioFlexible = false; // Si tiene flexibilidad de horario

    // Constructores
    public Vacante() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Vacante(String titulo, String descripcion, String departamento, Double salario,
                   String ubicacion, String tipoContrato, Integer solicitudesPermitidas,
                   LocalDateTime fechaExpiracion, String requisitos, String beneficios,
                   String empresa, LocalTime horaInicio, LocalTime horaFin,
                   String diasLaborales, Integer horasPorSemana, String modalidad,
                   String turno, Boolean horarioFlexible) {
        this();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.departamento = departamento;
        this.salario = salario;
        this.ubicacion = ubicacion;
        this.tipoContrato = tipoContrato;
        this.solicitudesPermitidas = solicitudesPermitidas;
        this.fechaExpiracion = fechaExpiracion;
        this.requisitos = requisitos;
        this.beneficios = beneficios;
        this.empresa = empresa;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.diasLaborales = diasLaborales;
        this.horasPorSemana = horasPorSemana;
        this.modalidad = modalidad;
        this.turno = turno;
        this.horarioFlexible = horarioFlexible;
    }
    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Integer getSolicitudesPermitidas() {
        return solicitudesPermitidas;
    }

    public void setSolicitudesPermitidas(Integer solicitudesPermitidas) {
        this.solicitudesPermitidas = solicitudesPermitidas;
    }

    public Integer getSolicitudesRecibidas() {
        return solicitudesRecibidas;
    }

    public void setSolicitudesRecibidas(Integer solicitudesRecibidas) {
        this.solicitudesRecibidas = solicitudesRecibidas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getDiasLaborales() {
        return diasLaborales;
    }

    public void setDiasLaborales(String diasLaborales) {
        this.diasLaborales = diasLaborales;
    }

    public Integer getHorasPorSemana() {
        return horasPorSemana;
    }

    public void setHorasPorSemana(Integer horasPorSemana) {
        this.horasPorSemana = horasPorSemana;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Boolean getHorarioFlexible() {
        return horarioFlexible;
    }

    public void setHorarioFlexible(Boolean horarioFlexible) {
        this.horarioFlexible = horarioFlexible;
    }

    // Métodos de negocio
    public boolean isDisponible() {
        return "ACTIVA".equals(estado) &&
                solicitudesRecibidas < solicitudesPermitidas &&
                LocalDateTime.now().isBefore(fechaExpiracion);
    }

    public void incrementarSolicitudes() {
        this.solicitudesRecibidas++;
        if (this.solicitudesRecibidas >= this.solicitudesPermitidas) {
            this.estado = "CERRADA";
        }
    }

    // Metodo helper para obtener el horario formateado
    public String getHorarioFormateado() {
        if (horarioFlexible != null && horarioFlexible) {
            return "Horario Flexible";
        }
        if (horaInicio != null && horaFin != null) {
            return horaInicio + " - " + horaFin;
        }
        return "Por definir";
    }

    // Metodo helper para verificar si trabaja un día específico
    public boolean trabajaDia(String dia) {
        if (diasLaborales == null) return false;
        return diasLaborales.toLowerCase().contains(dia.toLowerCase());
    }
}
