package cl.duoc.sistema_guias_despacho.service;

import cl.duoc.sistema_guias_despacho.model.GuiaDespacho;
import cl.duoc.sistema_guias_despacho.repository.GuiaDespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GuiaDespachoService {

    @Autowired
    private GuiaDespachoRepository repository;

    // 1. Crear guía
    public GuiaDespacho crearGuia(GuiaDespacho guia) {
        return repository.save(guia);
    }

    // 2. Consultar por transportista y fecha
    public List<GuiaDespacho> buscarPorTransportistaYFecha(String transportista, LocalDate fecha) {
        return repository.findByTransportistaAndFecha(transportista, fecha);
    }

    // 3. Modificar o actualizar guía
    public GuiaDespacho actualizarGuia(Long id, GuiaDespacho detallesNuevos) {
        Optional<GuiaDespacho> guiaExistente = repository.findById(id);
        if (guiaExistente.isPresent()) {
            GuiaDespacho guia = guiaExistente.get();
            guia.setTransportista(detallesNuevos.getTransportista());
            guia.setFecha(detallesNuevos.getFecha());
            guia.setDetallesPedido(detallesNuevos.getDetallesPedido());
            guia.setUrlArchivoS3(detallesNuevos.getUrlArchivoS3());
            return repository.save(guia);
        }
        return null; // En la práctica, aquí se podría lanzar una excepción personalizada
    }

    // 4. Eliminar guía
    public void eliminarGuia(Long id) {
        repository.deleteById(id);
    }
    
    // 5. Simulación de descarga S3
    public String obtenerUrlDescarga(Long id) {
        // Retorna un string simulando la URL del S3. Más adelante se integra el SDK de AWS.
        return "https://tu-bucket-s3.amazonaws.com/guias/guia-" + id + ".pdf";
    }
}