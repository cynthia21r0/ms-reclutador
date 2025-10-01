package com.ms_reclutador.controller;

import com.ms_reclutador.model.Vacante;
import com.ms_reclutador.service.VacanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vacantes")
public class VacanteController {

    @Autowired
    private VacanteService vacanteService;

    // Mostrar formulario para registrar nueva vacante
    @GetMapping("/nueva")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("vacante", new Vacante());
        return "registrar-vacante";
    }

    // Procesar el registro de nueva vacante
    @PostMapping("/guardar")
    public String guardarVacante(@ModelAttribute Vacante vacante, Model model) {
        try {
            // Establecer fecha de creación y estado inicial
            vacante.setFechaCreacion(LocalDateTime.now());
            vacante.setEstado("ACTIVA");
            vacante.setSolicitudesRecibidas(0);

            vacanteService.guardarVacante(vacante);
            model.addAttribute("mensaje", "Vacante registrada exitosamente");
            model.addAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al registrar vacante: " + e.getMessage());
            model.addAttribute("tipoMensaje", "error");
            model.addAttribute("vacante", vacante);
            return "registrar-vacante";
        }

        return "redirect:/vacantes/listar";
    }

    // Listar todas las vacantes
    @GetMapping("/listar")
    public String listarVacantes(Model model) {
        List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
        model.addAttribute("vacantes", vacantes);
        model.addAttribute("totalVacantes", vacantes.size());
        model.addAttribute("vacantesActivas", vacanteService.contarVacantesActivas());
        return "listar-vacantes";
    }

    // Ver detalles de una vacante específica
    @GetMapping("/detalle/{id}")
    public String verDetalleVacante(@PathVariable Long id, Model model) {
        Optional<Vacante> vacanteOpt = vacanteService.obtenerVacantePorId(id);
        if (vacanteOpt.isPresent()) {
            Vacante vacante = vacanteOpt.get();
            model.addAttribute("vacante", vacante);
            model.addAttribute("disponible", vacante.isDisponible());
            return "detalle-vacante";
        } else {
            model.addAttribute("mensaje", "Vacante no encontrada");
            model.addAttribute("tipoMensaje", "error");
            return "redirect:/vacantes/listar";
        }
    }

    // Mostrar formulario para editar vacante
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<Vacante> vacanteOpt = vacanteService.obtenerVacantePorId(id);
        if (vacanteOpt.isPresent()) {
            model.addAttribute("vacante", vacanteOpt.get());
            return "editar-vacante";
        } else {
            model.addAttribute("mensaje", "Vacante no encontrada");
            model.addAttribute("tipoMensaje", "error");
            return "redirect:/vacantes/listar";
        }
    }

    // Procesar la edición de vacante
    @PostMapping("/editar/{id}")
    public String actualizarVacante(@PathVariable Long id, @ModelAttribute Vacante vacante, Model model) {
        try {
            vacante.setId(id);
            vacanteService.actualizarVacante(vacante);
            model.addAttribute("mensaje", "Vacante actualizada exitosamente");
            model.addAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al actualizar vacante: " + e.getMessage());
            model.addAttribute("tipoMensaje", "error");
            model.addAttribute("vacante", vacante);
            return "editar-vacante";
        }

        return "redirect:/vacantes/listar";
    }

    // Eliminar vacante
    @GetMapping("/eliminar/{id}")
    public String eliminarVacante(@PathVariable Long id, Model model) {
        try {
            vacanteService.eliminarVacante(id);
            model.addAttribute("mensaje", "Vacante eliminada exitosamente");
            model.addAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al eliminar vacante: " + e.getMessage());
            model.addAttribute("tipoMensaje", "error");
        }

        return "redirect:/vacantes/listar";
    }

    // Registrar una nueva solicitud para la vacante
    @PostMapping("/solicitud/{id}")
    public String registrarSolicitud(@PathVariable Long id, Model model) {
        try {
            vacanteService.registrarSolicitud(id);
            model.addAttribute("mensaje", "Solicitud registrada exitosamente");
            model.addAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al registrar solicitud: " + e.getMessage());
            model.addAttribute("tipoMensaje", "error");
        }

        return "redirect:/vacantes/detalle/" + id;
    }
}
