package com.ms_reclutador.service;

import com.ms_reclutador.model.Vacante;
import com.ms_reclutador.repository.VacanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VacanteService {

    @Autowired
    private VacanteRepository vacanteRepository;

    public List<Vacante> obtenerTodasLasVacantes() {
        return vacanteRepository.findAllByOrderByFechaCreacionDesc();
    }

    public List<Vacante> obtenerVacantesActivas() {
        return vacanteRepository.findByEstadoOrderByFechaCreacionDesc("ACTIVA");
    }

    public List<Vacante> obtenerVacantesDisponibles() {
        return vacanteRepository.findVacantesDisponibles();
    }

    public Optional<Vacante> obtenerVacantePorId(Long id) {
        return vacanteRepository.findById(id);
    }

    public Vacante guardarVacante(Vacante vacante) {
        // Validar que la fecha de expiración sea futura
        if (vacante.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de expiración debe ser futura");
        }

        // Validar que las solicitudes permitidas sean positivas
        if (vacante.getSolicitudesPermitidas() <= 0) {
            throw new IllegalArgumentException("El número de solicitudes permitidas debe ser mayor a cero");
        }

        return vacanteRepository.save(vacante);
    }

    public void eliminarVacante(Long id) {
        vacanteRepository.deleteById(id);
    }

    public Vacante actualizarVacante(Vacante vacante) {
        return vacanteRepository.save(vacante);
    }

    public void registrarSolicitud(Long vacanteId) {
        Optional<Vacante> vacanteOpt = vacanteRepository.findById(vacanteId);
        if (vacanteOpt.isPresent()) {
            Vacante vacante = vacanteOpt.get();
            if (vacante.isDisponible()) {
                vacante.incrementarSolicitudes();
                vacanteRepository.save(vacante);
            } else {
                throw new IllegalStateException("La vacante no está disponible para más solicitudes");
            }
        } else {
            throw new IllegalArgumentException("Vacante no encontrada");
        }
    }

    public Long contarVacantesActivas() {
        return vacanteRepository.countVacantesActivas();
    }
}
