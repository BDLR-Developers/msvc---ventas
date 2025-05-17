package cl.venta.ventas.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.venta.ventas.model.DetalleVenta;
import cl.venta.ventas.model.Venta;
import cl.venta.ventas.model.claves.DetalleVentaId;
import cl.venta.ventas.model.dto.DtoVentaPost;
import cl.venta.ventas.model.interfaces.DetalleVentaInterface;
import cl.venta.ventas.repository.DetalleVentaRepository;
import cl.venta.ventas.repository.VentaRepository;
import jakarta.transaction.Transactional;

@Service
public class VentaService {

    @Autowired
    private DetalleVentaRepository repository;


    private VentaRepository vrepository;

    public List<DetalleVentaInterface> obtenerDetallePorNumeroVenta(Integer numeroVenta){
        return repository.obtenerDetallePorNumeroVenta(numeroVenta);
    }


    @Transactional
    public void crearVentaConDetalles(DtoVentaPost postVenta){
        Venta venta = new Venta();
        venta.setFechaVenta(postVenta.getFechaVenta());
        venta.setCorreoCliente(postVenta.getCorreoCliente());
        venta.setEstadoVenta(postVenta.getEstadoVenta());
        venta.setIdBodega(postVenta.getIdBodega());
        venta.setIdUsuario(postVenta.getIdUsuario());

        vrepository.save(venta);

        for (DtoVentaPost.DetalleRequestVenta det : postVenta.getDetalles()) {

            DetalleVenta detalle = new DetalleVenta();
            DetalleVentaId  id = new DetalleVentaId(venta.getNumeroVenta(), det.getIdProducto());
            detalle.setId(id);
            detalle.setCantidad(det.getCantidad());
            detalle.setPrecio(det.getPrecio());
            detalle.setVenta(venta);

            repository.save(detalle);
        }

    }

}
