/*package com.ms_reclutador.controller;

import com.ms_reclutador.model.Modalidad;
import com.ms_reclutador.service.ModalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modalidades")
@CrossOrigin(origins = "*")
public class ModalidadController {

    @Autowired
    private ModalidadService modalidadService;

    @GetMapping
    public ResponseEntity<List<Modalidad>> obtenerTodas() {
        return ResponseEntity.ok(modalidadService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modalidad> obtenerPorId(@PathVariable Long id) {
        return modalidadService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Modalidad> obtenerPorNombre(@PathVariable String nombre) {
        return modalidadService.obtenerPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Modalidad> crear(@RequestBody Modalidad modalidad) {
        try {
            Modalidad nuevaModalidad = modalidadService.crear(modalidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaModalidad);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modalidad> actualizar(@PathVariable Long id, @RequestBody Modalidad modalidad) {
        try {
            Modalidad modalidadActualizada = modalidadService.actualizar(id, modalidad);
            return ResponseEntity.ok(modalidadActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            modalidadService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}*/