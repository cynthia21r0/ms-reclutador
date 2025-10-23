package com.ms_reclutador.controller;

import com.ms_reclutador.dto.DipomexResponseDTO;
import com.ms_reclutador.service.DipomexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reclutador/utilidades")
@CrossOrigin(origins = "*")
@Tag(name = "Utilidades", description = "API para funcionalidades auxiliares (Códigos Postales, Geocodificación, etc.)")
public class UtilidadesController {

    @Autowired
    private DipomexService dipomexService;

    @Operation(summary = "Consultar información de Código Postal (México)",
            description = "Consulta la API externa de DIPOMEX. La clave API está protegida en el backend.")
    @GetMapping("/cp/{cp}")
    public ResponseEntity<?> consultarCodigoPostal(
            @Parameter(description = "Código Postal de 5 dígitos", example = "09000", required = true)
            @PathVariable String cp) {
        try {
            DipomexResponseDTO responseDTO = dipomexService.buscarCodigoPostal(cp);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", responseDTO);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // Manejo de error de validación de entrada
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error de entrada: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException e) {
            // Manejo de errores de la API externa
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al consultar API externa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}