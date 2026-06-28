package cl.duoc.sistema_guias_despacho.controller;

import cl.duoc.sistema_guias_despacho.model.GuiaDespacho;
import cl.duoc.sistema_guias_despacho.service.GuiaDespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/guias")
public class GuiaDespachoController {

    @Autowired
    private GuiaDespachoService service;

    // 1. Crear la guía en la base de datos (petición JSON básica)
    @PostMapping
    public ResponseEntity<GuiaDespacho> crearGuia(@RequestBody GuiaDespacho guia) {
        return ResponseEntity.ok(service.crearGuia(guia));
    }

    // 2. NUEVO: Subir el archivo físico de la guía a AWS S3 vinculándolo a su ID
    @PostMapping("/{id}/archivo")
    public ResponseEntity<?> subirArchivo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            GuiaDespacho actualizada = service.subirDocumentoS3(id, file);
            if (actualizada != null) {
                return ResponseEntity.ok(actualizada);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La guía con ID " + id + " no existe.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el archivo a AWS S3: " + e.getMessage());
        }
    }

    // 3. Descargar u obtener la URL del archivo en S3
    @GetMapping("/{id}/descargar")
    public ResponseEntity<String> descargarGuia(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerUrlDescarga(id));
    }

    // 4. Modificar o actualizar datos de la guía
    @PutMapping("/{id}")
    public ResponseEntity<GuiaDespacho> actualizarGuia(@PathVariable Long id, @RequestBody GuiaDespacho guia) {
        GuiaDespacho actualizada = service.actualizarGuia(id, guia);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }

    // 5. Eliminar una guía específica
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGuia(@PathVariable Long id) {
        service.eliminarGuia(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Consultar guías por transportista y fecha (Requerimiento de la pauta)
    @GetMapping("/buscar")
    public ResponseEntity<List<GuiaDespacho>> buscarGuias(
            @RequestParam String transportista, 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.buscarPorTransportistaYFecha(transportista, fecha));
    }
} 