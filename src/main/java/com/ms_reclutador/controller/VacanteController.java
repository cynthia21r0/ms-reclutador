package com.ms_reclutador.controller;

import com.ms_reclutador.model.Vacante;
import com.ms_reclutador.service.VacanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vacantes")
@CrossOrigin(origins = "*")
public class VacanteController {

    @Autowired
    private VacanteService vacanteService;

    // Crear nueva vacante
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearVacante(@RequestBody Vacante vacante) {
        try {
            vacante.setFechaCreacion(LocalDateTime.now());
            vacante.setEstado("ACTIVA");
            vacante.setSolicitudesRecibidas(0);

            Vacante nuevaVacante = vacanteService.guardarVacante(vacante);

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Vacante creada exitosamente");
            response.put("vacante", nuevaVacante);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al crear vacante: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Obtener todas las vacantes
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarVacantes() {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();

            Map<String, Object> response = new HashMap<>();
            response.put("total", vacantes.size());
            response.put("vacantesActivas", vacanteService.contarVacantesActivas());
            response.put("vacantes", vacantes);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al obtener vacantes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Obtener vacante por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerVacante(@PathVariable Long id) {
        try {
            return vacanteService.obtenerVacantePorId(id)
                    .map(vacante -> {
                        Map<String, Object> response = new HashMap<>();
                        response.put("vacante", vacante);
                        response.put("disponible", vacante.isDisponible());
                        response.put("horarioFormateado", vacante.getHorarioFormateado());
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        Map<String, Object> error = new HashMap<>();
                        error.put("error", "Vacante no encontrada con ID: " + id);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                    });
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al obtener vacante: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar vacante
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarVacante(@PathVariable Long id, @RequestBody Vacante vacante) {
        try {
            return vacanteService.obtenerVacantePorId(id)
                    .map(vacanteExistente -> {
                        vacante.setId(id);
                        // Preservar valores que no deberían cambiarse
                        vacante.setFechaCreacion(vacanteExistente.getFechaCreacion());
                        vacante.setSolicitudesRecibidas(vacanteExistente.getSolicitudesRecibidas());

                        Vacante vacanteActualizada = vacanteService.actualizarVacante(vacante);

                        Map<String, Object> response = new HashMap<>();
                        response.put("mensaje", "Vacante actualizada exitosamente");
                        response.put("vacante", vacanteActualizada);

                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        Map<String, Object> error = new HashMap<>();
                        error.put("error", "Vacante no encontrada con ID: " + id);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                    });
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al actualizar vacante: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar vacante
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarVacante(@PathVariable Long id) {
        try {
            return vacanteService.obtenerVacantePorId(id)
                    .map(vacante -> {
                        vacanteService.eliminarVacante(id);

                        Map<String, Object> response = new HashMap<>();
                        response.put("mensaje", "Vacante eliminada exitosamente");

                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        Map<String, Object> error = new HashMap<>();
                        error.put("error", "Vacante no encontrada con ID: " + id);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                    });
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al eliminar vacante: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Registrar solicitud a una vacante
    @PostMapping("/{id}/solicitud")
    public ResponseEntity<Map<String, Object>> registrarSolicitud(@PathVariable Long id) {
        try {
            return vacanteService.obtenerVacantePorId(id)
                    .map(vacante -> {
                        if (!vacante.isDisponible()) {
                            Map<String, Object> error = new HashMap<>();
                            error.put("error", "La vacante no está disponible");
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
                        }

                        vacanteService.registrarSolicitud(id);
                        Vacante vacanteActualizada = vacanteService.obtenerVacantePorId(id).get();

                        Map<String, Object> response = new HashMap<>();
                        response.put("mensaje", "Solicitud registrada exitosamente");
                        response.put("vacante", vacanteActualizada);
                        response.put("solicitudesRestantes",
                                vacanteActualizada.getSolicitudesPermitidas() -
                                        vacanteActualizada.getSolicitudesRecibidas());

                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        Map<String, Object> error = new HashMap<>();
                        error.put("error", "Vacante no encontrada con ID: " + id);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                    });
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al registrar solicitud: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Obtener vacantes activas
    @GetMapping("/activas")
    public ResponseEntity<Map<String, Object>> obtenerVacantesActivas() {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
            List<Vacante> vacantesActivas = vacantes.stream()
                    .filter(Vacante::isDisponible)
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("total", vacantesActivas.size());
            response.put("vacantes", vacantesActivas);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al obtener vacantes activas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Buscar vacantes por departamento
    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<Map<String, Object>> buscarPorDepartamento(@PathVariable String departamento) {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
            List<Vacante> vacantesDepartamento = vacantes.stream()
                    .filter(v -> v.getDepartamento().equalsIgnoreCase(departamento))
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("departamento", departamento);
            response.put("total", vacantesDepartamento.size());
            response.put("vacantes", vacantesDepartamento);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al buscar vacantes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Buscar vacantes por empresa
    @GetMapping("/empresa/{empresa}")
    public ResponseEntity<Map<String, Object>> buscarPorEmpresa(@PathVariable String empresa) {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
            List<Vacante> vacantesEmpresa = vacantes.stream()
                    .filter(v -> v.getEmpresa().equalsIgnoreCase(empresa))
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("empresa", empresa);
            response.put("total", vacantesEmpresa.size());
            response.put("vacantes", vacantesEmpresa);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al buscar vacantes por empresa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Buscar vacantes con horario flexible
    @GetMapping("/horario/flexible")
    public ResponseEntity<Map<String, Object>> buscarHorarioFlexible() {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
            List<Vacante> vacantesFlexibles = vacantes.stream()
                    .filter(v -> v.getHorarioFlexible() != null && v.getHorarioFlexible())
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("total", vacantesFlexibles.size());
            response.put("vacantes", vacantesFlexibles);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al buscar vacantes con horario flexible: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Buscar vacantes por rango de horas (ejemplo: antes de las 10:00 AM)
    @GetMapping("/horario/inicio-antes/{hora}")
    public ResponseEntity<Map<String, Object>> buscarPorHoraInicio(@PathVariable String hora) {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
            LocalTime horaLimite = LocalTime.parse(hora);

            List<Vacante> vacantesHorario = vacantes.stream()
                    .filter(v -> v.getHoraInicio() != null && v.getHoraInicio().isBefore(horaLimite))
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("hora_limite", hora);
            response.put("total", vacantesHorario.size());
            response.put("vacantes", vacantesHorario);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al buscar vacantes por horario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Buscar vacantes por horas por semana
    @GetMapping("/horario/horas-semanales")
    public ResponseEntity<Map<String, Object>> buscarPorHorasSemanales(
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max) {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();

            if (min != null) {
                vacantes = vacantes.stream()
                        .filter(v -> v.getHorasPorSemana() != null && v.getHorasPorSemana() >= min)
                        .toList();
            }

            if (max != null) {
                vacantes = vacantes.stream()
                        .filter(v -> v.getHorasPorSemana() != null && v.getHorasPorSemana() <= max)
                        .toList();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("horas_minimas", min != null ? min : "sin límite");
            response.put("horas_maximas", max != null ? max : "sin límite");
            response.put("total", vacantes.size());
            response.put("vacantes", vacantes);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al buscar vacantes por horas semanales: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Buscar vacantes por día laboral
    @GetMapping("/horario/dia/{dia}")
    public ResponseEntity<Map<String, Object>> buscarPorDiaLaboral(@PathVariable String dia) {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
            List<Vacante> vacantesDia = vacantes.stream()
                    .filter(v -> v.trabajaDia(dia))
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("dia", dia);
            response.put("total", vacantesDia.size());
            response.put("vacantes", vacantesDia);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al buscar vacantes por día: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Buscar por modalidad
    @GetMapping("/modalidad/{modalidad}")
    public ResponseEntity<Map<String, Object>> buscarPorModalidad(@PathVariable String modalidad) {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
            List<Vacante> vacantesModalidad = vacantes.stream()
                    .filter(v -> v.getModalidad() != null &&
                            v.getModalidad().equalsIgnoreCase(modalidad))
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("modalidad", modalidad);
            response.put("total", vacantesModalidad.size());
            response.put("vacantes", vacantesModalidad);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al buscar vacantes por modalidad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Buscar vacantes por turno
    @GetMapping("/turno/{turno}")
    public ResponseEntity<Map<String, Object>> buscarPorTurno(@PathVariable String turno) {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
            List<Vacante> vacantesTurno = vacantes.stream()
                    .filter(v -> v.getTurno() != null &&
                            v.getTurno().equalsIgnoreCase(turno))
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("turno", turno);
            response.put("total", vacantesTurno.size());
            response.put("vacantes", vacantesTurno);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al buscar vacantes por turno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Filtrado múltiple con parámetros opcionales
    @GetMapping("/filtrar")
    public ResponseEntity<Map<String, Object>> filtrarVacantes(
            @RequestParam(required = false) String empresa,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String turno,
            @RequestParam(required = false) String ubicacion,
            @RequestParam(required = false) String tipoContrato,
            @RequestParam(required = false) String modalidad,
            @RequestParam(required = false) Boolean horarioFlexible,
            @RequestParam(required = false) Integer horasMin,
            @RequestParam(required = false) Integer horasMax,
            @RequestParam(required = false) String dia) {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();

            // Aplicar filtros
            if (empresa != null && !empresa.isEmpty()) {
                vacantes = vacantes.stream()
                        .filter(v -> v.getEmpresa().equalsIgnoreCase(empresa))
                        .toList();
            }

            if (departamento != null && !departamento.isEmpty()) {
                vacantes = vacantes.stream()
                        .filter(v -> v.getDepartamento().equalsIgnoreCase(departamento))
                        .toList();
            }

            if (turno != null && !turno.isEmpty()) {
                vacantes = vacantes.stream()
                        .filter(v -> v.getTurno() != null &&
                                v.getTurno().equalsIgnoreCase(turno))
                        .toList();
            }

            if (ubicacion != null && !ubicacion.isEmpty()) {
                vacantes = vacantes.stream()
                        .filter(v -> v.getUbicacion().toLowerCase().contains(ubicacion.toLowerCase()))
                        .toList();
            }

            if (tipoContrato != null && !tipoContrato.isEmpty()) {
                vacantes = vacantes.stream()
                        .filter(v -> v.getTipoContrato().equalsIgnoreCase(tipoContrato))
                        .toList();
            }

            if (modalidad != null && !modalidad.isEmpty()) {
                vacantes = vacantes.stream()
                        .filter(v -> v.getModalidad() != null &&
                                v.getModalidad().equalsIgnoreCase(modalidad))
                        .toList();
            }

            if (horarioFlexible != null) {
                vacantes = vacantes.stream()
                        .filter(v -> v.getHorarioFlexible() != null &&
                                v.getHorarioFlexible().equals(horarioFlexible))
                        .toList();
            }

            if (horasMin != null) {
                vacantes = vacantes.stream()
                        .filter(v -> v.getHorasPorSemana() != null &&
                                v.getHorasPorSemana() >= horasMin)
                        .toList();
            }

            if (horasMax != null) {
                vacantes = vacantes.stream()
                        .filter(v -> v.getHorasPorSemana() != null &&
                                v.getHorasPorSemana() <= horasMax)
                        .toList();
            }

            if (dia != null && !dia.isEmpty()) {
                vacantes = vacantes.stream()
                        .filter(v -> v.trabajaDia(dia))
                        .toList();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("total", vacantes.size());
            Map<String, Object> filtros = new HashMap<>();
            filtros.put("empresa", empresa != null ? empresa : "ninguno");
            filtros.put("departamento", departamento != null ? departamento : "ninguno");
            filtros.put("turno", turno != null ? turno : "ninguno");
            filtros.put("ubicacion", ubicacion != null ? ubicacion : "ninguno");
            filtros.put("tipoContrato", tipoContrato != null ? tipoContrato : "ninguno");
            filtros.put("modalidad", modalidad != null ? modalidad : "ninguno");
            filtros.put("horarioFlexible", horarioFlexible != null ? horarioFlexible : "ninguno");
            filtros.put("horasMin", horasMin != null ? horasMin : "ninguno");
            filtros.put("horasMax", horasMax != null ? horasMax : "ninguno");
            filtros.put("dia", dia != null ? dia : "ninguno");
            response.put("filtros_aplicados", filtros);
            response.put("vacantes", vacantes);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al filtrar vacantes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Listar todas las empresas disponibles
    @GetMapping("/empresas")
    public ResponseEntity<Map<String, Object>> listarEmpresas() {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
            List<String> empresas = vacantes.stream()
                    .map(Vacante::getEmpresa)
                    .distinct()
                    .sorted()
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("total", empresas.size());
            response.put("empresas", empresas);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al obtener empresas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Listar todos los horarios disponibles
    @GetMapping("/horarios")
    public ResponseEntity<Map<String, Object>> listarHorarios() {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
            List<Map<String, Object>> horarios = vacantes.stream()
                    .filter(v -> v.getHoraInicio() != null && v.getHoraFin() != null)
                    .map(v -> {
                        Map<String, Object> horario = new HashMap<>();
                        horario.put("horaInicio", v.getHoraInicio().toString());
                        horario.put("horaFin", v.getHoraFin().toString());
                        horario.put("horarioFormateado", v.getHorarioFormateado());
                        horario.put("diasLaborales", v.getDiasLaborales());
                        horario.put("horasPorSemana", v.getHorasPorSemana());
                        return horario;
                    })
                    .distinct()
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("total", horarios.size());
            response.put("horarios", horarios);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al obtener horarios: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Listar todas las modalidades disponibles
    @GetMapping("/modalidades")
    public ResponseEntity<Map<String, Object>> listarModalidades() {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
            List<String> modalidades = vacantes.stream()
                    .map(Vacante::getModalidad)
                    .filter(m -> m != null && !m.isEmpty())
                    .distinct()
                    .sorted()
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("total", modalidades.size());
            response.put("modalidades", modalidades);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al obtener modalidades: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Listar todos los turnos disponibles
    @GetMapping("/turnos")
    public ResponseEntity<Map<String, Object>> listarTurnos() {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();
            List<String> turnos = vacantes.stream()
                    .map(Vacante::getTurno)
                    .filter(t -> t != null && !t.isEmpty())
                    .distinct()
                    .sorted()
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("total", turnos.size());
            response.put("turnos", turnos);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al obtener turnos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}