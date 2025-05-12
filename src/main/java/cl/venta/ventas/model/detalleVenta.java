package cl.venta.ventas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalleVenta")

public class detalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int cantidad;
    private int precio;
    private int numeroVenta;
    private Producto idProd;

}
