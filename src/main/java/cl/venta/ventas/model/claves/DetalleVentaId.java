package cl.venta.ventas.model.claves;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data

    public class DetalleVentaId implements Serializable {
        @Column(name = "numero_venta")
        private Integer numeroVenta;


        @Column(name = "id_producto")
        private Integer idProducto;
    }


