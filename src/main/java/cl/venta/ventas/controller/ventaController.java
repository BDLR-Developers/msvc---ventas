package cl.venta.ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.venta.ventas.model.dto.DtoVentaPost;
import cl.venta.ventas.model.interfaces.DetalleVentaInterface;
import cl.venta.ventas.service.VentaService;


@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {

    @Autowired
    private VentaService vService;

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerDetalleVenta(@PathVariable Integer id) {
        List<DetalleVentaInterface> productoOptional = vService.obtenerDetallePorNumeroVenta(id);
        if(productoOptional.size() > 0) {
            return ResponseEntity.ok(productoOptional);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody DtoVentaPost postVenta) {
        vService.crearVentaConDetalles(postVenta);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    
    






}
