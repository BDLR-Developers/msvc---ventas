package cl.venta.ventas.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.venta.ventas.model.dto.DtoVentaRequest;
import cl.venta.ventas.model.dto.DtoVentaResponse;
//import cl.venta.ventas.model.interfaces.DetalleVentaInterface;
import cl.venta.ventas.service.VentService;


@RestController
@RequestMapping("/api/v1/ventas")
public class VenController {

    private static final Logger logger = LoggerFactory.getLogger(VenController.class);

    @Autowired
    private VentService service;
    /* 
    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> obtenerDetalleVenta(@PathVariable Integer id) {
        List<DetalleVentaInterface> ventasOptional = service.obtenerDetallePorNumeroVenta(id);
        if(ventasOptional.size() > 0) {
            return ResponseEntity.ok(ventasOptional);
        }
        return ResponseEntity.notFound().build();
    }
*/
    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody DtoVentaRequest postVenta) {
        service.crearVentaConDetalles(postVenta);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerVenta(@PathVariable Integer id) {
        try{
            DtoVentaResponse dto = service.obtenerVenta(id);
            return ResponseEntity.ok(dto);
        } catch(RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<DtoVentaResponse>> obtenerVentasPorFecha(@RequestParam String fecha) {

        try {
            LocalDate fechaVenta = LocalDate.parse(fecha);
            List<DtoVentaResponse> ventas = service.obtenerVentasPorFecha(fechaVenta);
            return ResponseEntity.ok(ventas);
            
        } catch (DateTimeParseException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarVenta(@PathVariable Integer id) {
        try {
            DtoVentaResponse dto = service.eliminarVenta(id);
            return ResponseEntity.ok(dto);

        } catch (RuntimeException ex) {


            return ResponseEntity.notFound().build();
        }}
    }
