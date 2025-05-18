package cl.venta.ventas.model.dto;

import java.time.LocalDate;
import java.util.List;

import cl.venta.ventas.model.dto.DtoVentaRequest.DetalleRequestVenta;
import lombok.Data;

@Data
public class DtoVentaResponse {

    private Integer numeroVenta;
    private LocalDate fechaVenta;
    private String correoCliente;
    private Integer estadoVenta;
    private Integer idBodega;
    private Integer idUsuario;
    private List<DetalleRequestVenta> detalles;

}
