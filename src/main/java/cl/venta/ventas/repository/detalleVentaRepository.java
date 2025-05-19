package cl.venta.ventas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cl.venta.ventas.model.DetalleVenta;
import cl.venta.ventas.model.claves.DetalleVentaId;
import cl.venta.ventas.model.interfaces.DetalleVentaInterface;




public interface DetalleVentaRepository extends CrudRepository<DetalleVenta, DetalleVentaId>{
    @Query(value = """
        SELECT
            dv.numero_venta AS numeroVenta,
            dv.id_producto AS idProducto,
            p.nombre_producto AS nombreProducto,
            v.fecha_venta AS fechaVenta,
            dv.precio AS precioUnitario,
            dv.cantidad as cantidad
        FROM detalle_venta dv
        JOIN venta v ON dv.numero_venta = v.numero_venta
        JOIN baseproductos.producto p ON dv.id_producto = p.id_producto
        WHERE dv.numero_venta = :numeroVenta
            """, nativeQuery = true)

    List<DetalleVentaInterface> obtenerDetallePorNumeroVenta(@Param("numeroVenta") Integer numeroVenta);
    

    @Query(value = """
            SELECT
                count(dv.numero_venta) as cantidadVendida,
                sum(cantidad*precio) as totalVendido
            FROM detalle_venta dv
            JOIN venta v ON dv.numero_venta = v.numero_venta
            JOIN baseproductos.producto p ON dv.id_producto = p.id_producto
            WHERE dv.fechaVenta = :fechaVenta
            """, nativeQuery = true)

    DetalleVentaInterface obtenerReportebyFecha(@Param("fechaVenta") LocalDate fechaVenta);

}
