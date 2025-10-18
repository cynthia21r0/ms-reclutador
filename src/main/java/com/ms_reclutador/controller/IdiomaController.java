/*package com.ms_reclutador.controller;

import com.ms_reclutador.model.Idiomas;
import com.ms_reclutador.service.IdiomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/idiomas")
@CrossOrigin(origins = "*")
public class IdiomaController {

    @Autowired
    private IdiomaService idiomasService;

    @GetMapping
    public ResponseEntity<List<Idiomas>> obtenerTodos() {
        return ResponseEntity.ok(idiomasService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idiomas> obtenerPorId(@PathVariable Long id) {
        return idiomasService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{idioma}")
    public ResponseEntity<Idiomas> obtenerPorNombre(@PathVariable String idioma) {
        return idiomasService.obtenerPorNombre(idioma)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Idiomas> crear(@RequestBody Idiomas idioma) {
        try {
            Idiomas nuevoIdioma = idiomasService.crear(idioma);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoIdioma);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Idiomas> actualizar(@PathVariable Long id, @RequestBody Idiomas idioma) {
        try {
            Idiomas idiomaActualizado = idiomasService.actualizar(id, idioma);
            return ResponseEntity.ok(idiomaActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            idiomasService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}*/