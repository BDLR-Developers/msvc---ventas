package cl.venta.ventas.model.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtoVentaRequest {
        private LocalDate fechaVenta;
        private String correoCliente;
        private Integer estadoVenta;
        private Integer idBodega;
        private Integer idUsuario;
        private List<DetalleRequestVenta> detalles;
        
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        public static class DetalleRequestVenta {
            private Integer idProducto;
            private Integer cantidad;
            private Integer precio;
        }
    }
