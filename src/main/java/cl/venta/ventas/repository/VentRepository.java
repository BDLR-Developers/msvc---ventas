package cl.venta.ventas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cl.venta.ventas.model.Venta;

public interface VentRepository extends CrudRepository<Venta,Integer> {
    List<Venta> findByFechaVenta(LocalDate fechaVenta);
}

