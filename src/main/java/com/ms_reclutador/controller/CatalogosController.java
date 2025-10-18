package com.ms_reclutador.controller;

import com.ms_reclutador.model.*;
import com.ms_reclutador.service.CatalogosService;
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
@RequestMapping("/api/reclutador/catalogos")
@CrossOrigin(origins = "*")
@Tag(name = "Catálogos", description = "API para la gestión de catálogos del sistema (áreas, habilidades, idiomas, modalidades)")
public class CatalogosController {

    @Autowired
    private CatalogosService catalogosService;

    // Áreas
    @Operation(summary = "Obtener todas las áreas", description = "Retorna la lista completa de áreas disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de áreas obtenida exitosamente")
    @GetMapping("/areas")
    public ResponseEntity<?> obtenerAreas() {
        try {
            List<Area> areas = catalogosService.obtenerTodasLasAreas();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", areas);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al obtener áreas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Crear nueva área", description = "Registra una nueva área en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Área creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al crear área")
    })
    @PostMapping("/areas")
    public ResponseEntity<?> crearArea(
            @Parameter(description = "Datos del área a crear", required = true)
            @RequestBody Area area) {
        try {
            Area areaCreada = catalogosService.crearArea(area);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Área creada exitosamente");
            response.put("data", areaCreada);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al crear área: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Habilidades
    @Operation(summary = "Obtener todas las habilidades", description = "Retorna la lista completa de habilidades disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de habilidades obtenida exitosamente")
    @GetMapping("/habilidades")
    public ResponseEntity<?> obtenerHabilidades() {
        try {
            List<Habilidades> habilidades = catalogosService.obtenerTodasLasHabilidades();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", habilidades);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al obtener habilidades: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
            List<Habilidades> habilidades = catalogosService.obtenerHabilidadesPorArea(areaId);

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

    @Operation(summary = "Crear nueva habilidad", description = "Registra una nueva habilidad en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Habilidad creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al crear habilidad")
    })
    @PostMapping("/habilidades")
    public ResponseEntity<?> crearHabilidad(
            @Parameter(description = "Datos de la habilidad a crear", required = true)
            @RequestBody Habilidades habilidad) {
        try {
            Habilidades habilidadCreada = catalogosService.crearHabilidad(habilidad);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Habilidad creada exitosamente");
            response.put("data", habilidadCreada);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al crear habilidad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Idiomas
    @Operation(summary = "Obtener todos los idiomas", description = "Retorna la lista completa de idiomas disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de idiomas obtenida exitosamente")
    @GetMapping("/idiomas")
    public ResponseEntity<?> obtenerIdiomas() {
        try {
            List<Idiomas> idiomas = catalogosService.obtenerTodosLosIdiomas();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", idiomas);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al obtener idiomas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Crear nuevo idioma", description = "Registra un nuevo idioma en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Idioma creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al crear idioma")
    })
    @PostMapping("/idiomas")
    public ResponseEntity<?> crearIdioma(
            @Parameter(description = "Datos del idioma a crear", required = true)
            @RequestBody Idiomas idioma) {
        try {
            Idiomas idiomaCreado = catalogosService.crearIdioma(idioma);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Idioma creado exitosamente");
            response.put("data", idiomaCreado);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al crear idioma: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Modalidades
    @Operation(summary = "Obtener todas las modalidades", description = "Retorna la lista completa de modalidades disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de modalidades obtenida exitosamente")
    @GetMapping("/modalidades")
    public ResponseEntity<?> obtenerModalidades() {
        try {
            List<Modalidad> modalidades = catalogosService.obtenerTodasLasModalidades();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", modalidades);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al obtener modalidades: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Crear nueva modalidad", description = "Registra una nueva modalidad en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Modalidad creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al crear modalidad")
    })
    @PostMapping("/modalidades")
    public ResponseEntity<?> crearModalidad(
            @Parameter(description = "Datos de la modalidad a crear", required = true)
            @RequestBody Modalidad modalidad) {
        try {
            Modalidad modalidadCreada = catalogosService.crearModalidad(modalidad);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Modalidad creada exitosamente");
            response.put("data", modalidadCreada);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al crear modalidad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}