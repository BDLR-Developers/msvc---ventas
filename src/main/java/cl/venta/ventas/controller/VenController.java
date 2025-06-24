package cl.venta.ventas.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import cl.venta.ventas.assemblers.VentaAssembler;
import cl.venta.ventas.model.dto.DtoVentaRequest;
import cl.venta.ventas.model.dto.DtoVentaResponse;
//import cl.venta.ventas.model.interfaces.DetalleVentaInterface;
import cl.venta.ventas.service.VentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;




@Tag(name = "Ventas", description = "Controlador para gestionar ventas") 
@RestController
@RequestMapping("/api/v1/ventas")
public class VenController {

    private static final Logger logger = LoggerFactory.getLogger(VenController.class);

    @Autowired
    private VentService service;
    @Autowired
    private VentaAssembler ventaAssembler;
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
    @Operation(
        summary = "Guardar una nueva venta", 
        description = "Crea una nueva venta y la guarda en la base de datos", 
        responses={@ApiResponse (responseCode = "201", 
            description = "Venta creada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DtoVentaResponse.class)))}
        )
    public ResponseEntity<?> crearVenta(@RequestBody DtoVentaRequest postVenta) {
        service.crearVentaConDetalles(postVenta);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener una venta segun el id", 
        description = "Devuelve una venta", 
        responses={
            @ApiResponse (responseCode = "200", 
            description = "Venta obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DtoVentaResponse.class))),
            @ApiResponse (responseCode = "404", 
            description = "No se encontró una venta con esa id",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DtoVentaResponse.class)))
            }
        )
    public ResponseEntity<?> obtenerVenta(@PathVariable Integer id) {
        try{
            DtoVentaResponse dto = service.obtenerVenta(id);
            return ResponseEntity.ok(ventaAssembler.toModel(dto));
        } catch(RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    @Operation(
        summary = "Obtener una venta segun fecha", 
        description = "Devuelve una venta por fecha", 
        responses={
            @ApiResponse (responseCode = "200", 
            description = "Venta obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DtoVentaResponse.class))),
            @ApiResponse (responseCode = "404", 
            description = "No se encontró venta según la fecha ingresada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DtoVentaResponse.class)))
            }
    )
    public ResponseEntity<CollectionModel<EntityModel<DtoVentaResponse>>> obtenerVentasPorFecha(@RequestParam String fecha) {

        try {
            LocalDate fechaVenta = LocalDate.parse(fecha);
            List<DtoVentaResponse> ventas = service.obtenerVentasPorFecha(fechaVenta);
            if(ventas.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CollectionModel.empty());
            }
            else{
                List<EntityModel<DtoVentaResponse>> ventaModels = new ArrayList<>();
                for (DtoVentaResponse venta : ventas){
                    ventaModels.add(ventaAssembler.toModel(venta));
                }
                return ResponseEntity.ok(CollectionModel.of(ventaModels));
            }
            
        } catch (DateTimeParseException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
     @Operation(
        summary = "Eliminar una venta", 
        description = "Elimina una venta existente según su ID", 
        responses={@ApiResponse (responseCode = "200", 
            description = "Venta eliminada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DtoVentaResponse.class))),
            @ApiResponse (responseCode = "404", 
            description = "Pedido no encontrado",
            content = @Content(
                mediaType = "application/json"))},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "ID de la venta a eliminar", 
            required = true)}
        )
    public ResponseEntity<?> eliminarVenta(@PathVariable Integer id) {
        try {
            DtoVentaResponse dto = service.eliminarVenta(id);
            return ResponseEntity.ok(ventaAssembler.toModel(dto));

        } catch (RuntimeException ex) {


            return ResponseEntity.notFound().build();
        }}
    }
