package cl.duoc.sistema_guias_despacho.controller;

import cl.duoc.sistema_guias_despacho.model.GuiaDespacho;
import cl.duoc.sistema_guias_despacho.service.GuiaDespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/guias")
public class GuiaDespachoController {

    @Autowired
    private GuiaDespachoService service;

    // Crear guías de despacho [cite: 18]
    @PostMapping
    public ResponseEntity<GuiaDespacho> crearGuia(@RequestBody GuiaDespacho guia) {
        return ResponseEntity.ok(service.crearGuia(guia));
    }

    // Descargar guías con validación de permisos [cite: 20]
    @GetMapping("/{id}/descargar")
    public ResponseEntity<String> descargarGuia(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerUrlDescarga(id));
    }

    // Modificar o actualizar guías [cite: 21]
    @PutMapping("/{id}")
    public ResponseEntity<GuiaDespacho> actualizarGuia(@PathVariable Long id, @RequestBody GuiaDespacho guia) {
        GuiaDespacho actualizada = service.actualizarGuia(id, guia);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar guías específicas [cite: 22]
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGuia(@PathVariable Long id) {
        service.eliminarGuia(id);
        return ResponseEntity.noContent().build();
    }

    // Consultar guías por transportista y fecha 
    @GetMapping("/buscar")
    public ResponseEntity<List<GuiaDespacho>> buscarGuias(
            @RequestParam String transportista, 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.buscarPorTransportistaYFecha(transportista, fecha));
    }
} 