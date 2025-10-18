package com.ms_reclutador.controller;

import com.ms_reclutador.dto.VacanteRequestDTO;
import com.ms_reclutador.model.Vacante;
import com.ms_reclutador.service.VacanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reclutador/vacantes")
@CrossOrigin(origins = "*")
@Tag(name = "Vacantes", description = "API para la gestión de vacantes laborales")
public class VacanteController {

    @Autowired
    private VacanteService vacanteService;

    @Operation(summary = "Registrar nueva vacante", description = "Crea una nueva vacante laboral con toda la información requerida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vacante creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Error en los datos de entrada",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping
    public ResponseEntity<?> registrarVacante(
            @Parameter(description = "Datos de la vacante a crear", required = true)
            @RequestBody VacanteRequestDTO vacanteRequest) {
        try {
            Vacante vacante = new Vacante();
            // Mapear campos básicos (mantener el código existente)
            vacante.setTitulo(vacanteRequest.getTitulo());
            vacante.setDescripcion(vacanteRequest.getDescripcion());
            vacante.setSalario(vacanteRequest.getSalario());
            vacante.setUbicacion(vacanteRequest.getUbicacion());
            vacante.setTipoContrato(vacanteRequest.getTipoContrato());
            vacante.setSolicitudesPermitidas(vacanteRequest.getSolicitudesPermitidas());
            vacante.setEstado(vacanteRequest.getEstado());
            vacante.setFechaExpiracion(vacanteRequest.getFechaExpiracion());
            vacante.setBeneficios(vacanteRequest.getBeneficios());
            vacante.setEmpresa(vacanteRequest.getEmpresa());
            vacante.setHoraInicio(vacanteRequest.getHoraInicio());
            vacante.setHoraFin(vacanteRequest.getHoraFin());
            vacante.setDiasLaborales(vacanteRequest.getDiasLaborales());
            vacante.setHorasPorSemana(vacanteRequest.getHorasPorSemana());
            vacante.setTurno(vacanteRequest.getTurno());
            vacante.setHorarioFlexible(vacanteRequest.getHorarioFlexible());

            Vacante vacanteCreada = vacanteService.crearVacante(
                    vacante,
                    vacanteRequest.getAreaId(),
                    vacanteRequest.getModalidadId(),
                    vacanteRequest.getHabilidadesIds(),
                    vacanteRequest.getIdiomasIds()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Vacante registrada exitosamente");
            response.put("data", vacanteCreada);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al registrar vacante: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(summary = "Obtener todas las vacantes", description = "Retorna una lista de todas las vacantes registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de vacantes obtenida exitosamente")
    @GetMapping
    public ResponseEntity<?> obtenerTodasLasVacantes() {
        try {
            List<Vacante> vacantes = vacanteService.obtenerTodasLasVacantes();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", vacantes);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al obtener vacantes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Obtener vacantes por empresa", description = "Retorna las vacantes asociadas a una empresa específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacantes obtenidas exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/empresa/{empresa}")
    public ResponseEntity<?> obtenerVacantesPorEmpresa(
            @Parameter(description = "Nombre de la empresa", example = "Tech Solutions SA de CV", required = true)
            @PathVariable String empresa) {
        try {
            List<Vacante> vacantes = vacanteService.obtenerVacantesPorEmpresa(empresa);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", vacantes);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al obtener vacantes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Obtener vacante por ID", description = "Retorna una vacante específica basada en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacante encontrada"),
            @ApiResponse(responseCode = "404", description = "Vacante no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerVacantePorId(
            @Parameter(description = "ID de la vacante", example = "1", required = true)
            @PathVariable Long id) {
        try {
            Vacante vacante = vacanteService.obtenerVacantePorId(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", vacante);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al obtener vacante: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Operation(summary = "Actualizar vacante", description = "Actualiza la información de una vacante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacante actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos de entrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarVacante(
            @Parameter(description = "ID de la vacante a actualizar", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la vacante", required = true)
            @RequestBody VacanteRequestDTO vacanteRequest) {
        try {
            Vacante vacante = new Vacante();
            // Mapear campos básicos (mantener el código existente)
            vacante.setTitulo(vacanteRequest.getTitulo());
            vacante.setDescripcion(vacanteRequest.getDescripcion());
            vacante.setSalario(vacanteRequest.getSalario());
            vacante.setUbicacion(vacanteRequest.getUbicacion());
            vacante.setTipoContrato(vacanteRequest.getTipoContrato());
            vacante.setSolicitudesPermitidas(vacanteRequest.getSolicitudesPermitidas());
            vacante.setBeneficios(vacanteRequest.getBeneficios());
            vacante.setHoraInicio(vacanteRequest.getHoraInicio());
            vacante.setHoraFin(vacanteRequest.getHoraFin());
            vacante.setDiasLaborales(vacanteRequest.getDiasLaborales());
            vacante.setHorasPorSemana(vacanteRequest.getHorasPorSemana());
            vacante.setTurno(vacanteRequest.getTurno());
            vacante.setHorarioFlexible(vacanteRequest.getHorarioFlexible());

            Vacante vacanteActualizada = vacanteService.actualizarVacante(
                    id,
                    vacante,
                    vacanteRequest.getAreaId(),
                    vacanteRequest.getModalidadId(),
                    vacanteRequest.getHabilidadesIds(),
                    vacanteRequest.getIdiomasIds()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Vacante actualizada exitosamente");
            response.put("data", vacanteActualizada);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al actualizar vacante: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(summary = "Cambiar estado de vacante", description = "Actualiza el estado de una vacante (ACTIVA, INACTIVA, etc.)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al cambiar estado")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstadoVacante(
            @Parameter(description = "ID de la vacante", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado de la vacante", required = true,
                    schema = @Schema(allowableValues = {"ACTIVA", "INACTIVA", "CANCELADA", "CERRADA"}))
            @RequestBody Map<String, String> request) {
        try {
            String estado = request.get("estado");
            Vacante vacante = vacanteService.cambiarEstadoVacante(id, estado);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Estado de vacante actualizado exitosamente");
            response.put("data", vacante);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al cambiar estado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(summary = "Eliminar vacante", description = "Elimina permanentemente una vacante del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacante eliminada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al eliminar vacante")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarVacante(
            @Parameter(description = "ID de la vacante a eliminar", example = "1", required = true)
            @PathVariable Long id) {
        try {
            vacanteService.eliminarVacante(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Vacante eliminada exitosamente");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al eliminar vacante: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(summary = "Obtener habilidades por área", description = "Retorna las habilidades asociadas a un área específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilidades obtenidas exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al obtener habilidades")
    })
    @GetMapping("/habilidades/area/{areaId}")
    public ResponseEntity<?> obtenerHabilidadesPorArea(
            @Parameter(description = "ID del área", example = "1", required = true)
            @PathVariable Long areaId) {
        try {
            var habilidades = vacanteService.obtenerHabilidadesPorArea(areaId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", habilidades);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al obtener habilidades: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}