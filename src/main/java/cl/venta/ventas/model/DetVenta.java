
package cl.venta.ventas.model;
import cl.venta.ventas.model.claves.DetalleVentaId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalle_venta")

public class DetVenta {

    @EmbeddedId
    private DetalleVentaId id;

    private Integer cantidad;
    private Integer precio;

    @ManyToOne
    @MapsId("numeroVenta")
    @JoinColumn(name = "numero_venta")
    private Venta venta;
    

}
