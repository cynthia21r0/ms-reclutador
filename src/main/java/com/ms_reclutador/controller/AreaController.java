/*package com.ms_reclutador.controller;

import com.ms_reclutador.model.Area;
import com.ms_reclutador.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@CrossOrigin(origins = "*")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    public ResponseEntity<List<Area>> obtenerTodas() {
        return ResponseEntity.ok(areaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Area> obtenerPorId(@PathVariable Long id) {
        return areaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Area> obtenerPorNombre(@PathVariable String nombre) {
        return areaService.obtenerPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Area> crear(@RequestBody Area area) {
        try {
            Area nuevaArea = areaService.crear(area);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaArea);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Area> actualizar(@PathVariable Long id, @RequestBody Area area) {
        try {
            Area areaActualizada = areaService.actualizar(id, area);
            return ResponseEntity.ok(areaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            areaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}*/