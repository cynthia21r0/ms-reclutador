/*package com.ms_reclutador.controller;

import com.ms_reclutador.model.Habilidades;
import com.ms_reclutador.service.HabilidadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habilidades")
@CrossOrigin(origins = "*")
public class HabilidadesController {

    @Autowired
    private HabilidadesService habilidadesService;

    @GetMapping
    public ResponseEntity<List<Habilidades>> obtenerTodas() {
        return ResponseEntity.ok(habilidadesService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habilidades> obtenerPorId(@PathVariable Long id) {
        return habilidadesService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/area/{areaId}")
    public ResponseEntity<List<Habilidades>> obtenerPorArea(@PathVariable Long areaId) {
        return ResponseEntity.ok(habilidadesService.obtenerPorArea(areaId));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Habilidades> obtenerPorNombre(@PathVariable String nombre) {
        return habilidadesService.obtenerPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Habilidades> crear(@RequestBody Habilidades habilidad) {
        try {
            Habilidades nuevaHabilidad = habilidadesService.crear(habilidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaHabilidad);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habilidades> actualizar(@PathVariable Long id, @RequestBody Habilidades habilidad) {
        try {
            Habilidades habilidadActualizada = habilidadesService.actualizar(id, habilidad);
            return ResponseEntity.ok(habilidadActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            habilidadesService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}*/