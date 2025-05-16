package cl.venta.ventas.model.dto;

import java.time.LocalDate;
import java.util.List;


import lombok.Data;


@Data
public class DtoVentaPost {    
        private LocalDate fechaVenta;
        private String correoCliente;
        private Integer estadoVenta;
        private Integer idBodega;
        private Integer idUsuario;
        private List<DetalleRequestVenta> detalles;

        @Data
        public static class DetalleRequestVenta {
            private Integer idProducto;
            private Integer cantidad;
            private Integer precio;
        }
    }
