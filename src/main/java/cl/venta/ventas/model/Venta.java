package cl.venta.ventas.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "venta")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numeroVenta;
    private LocalDate fechaVenta;
    private String correoCliente;
    private Integer estadoVenta;
    private Integer idBodega;
    private Integer idUsuario;

    @OneToMany(mappedBy = "venta")
    private List<DetVenta> productos = new ArrayList<>();

    public void addProducto(DetVenta producto) {
        productos.add(producto);
        
    }
}