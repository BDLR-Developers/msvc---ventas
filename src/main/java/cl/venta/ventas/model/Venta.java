package cl.venta.ventas.model;

import java.util.Date;

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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "venta")

public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int nroVenta;
    private Date fecha_venta;
    private String correoCliente;
    private String estadoVenta;
    private String idSucursal;
    private String idUsuario;
    private String idBodega;
    private String idSucursal_1;


}
