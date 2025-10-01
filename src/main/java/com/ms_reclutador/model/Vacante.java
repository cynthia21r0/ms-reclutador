package com.ms_reclutador.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vacantes")
public class Vacante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
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

    // Constructores
    public Vacante() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Vacante(String titulo, String descripcion, String departamento, Double salario,
                   String ubicacion, String tipoContrato, Integer solicitudesPermitidas,
                   LocalDateTime fechaExpiracion, String requisitos, String beneficios) {
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
}
