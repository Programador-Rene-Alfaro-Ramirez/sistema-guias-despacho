package cl.duoc.sistema_guias_despacho.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "guias_despacho")
public class GuiaDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String transportista;
    
    @Column(nullable = false)
    private LocalDate fecha;

    private String detallesPedido;

    // Ruta del documento subido a AWS S3
    private String urlArchivoS3; 

    // Constructor vacío
    public GuiaDespacho() {}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransportista() {
        return transportista;
    }

    public void setTransportista(String transportista) {
        this.transportista = transportista;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(String detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    public String getUrlArchivoS3() {
        return urlArchivoS3;
    }

    public void setUrlArchivoS3(String urlArchivoS3) {
        this.urlArchivoS3 = urlArchivoS3;
    }
} 