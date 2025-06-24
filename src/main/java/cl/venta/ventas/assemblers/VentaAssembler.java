package cl.venta.ventas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import cl.venta.ventas.controller.VenController;

import cl.venta.ventas.model.dto.DtoVentaResponse;

@Component


public class VentaAssembler implements RepresentationModelAssembler<DtoVentaResponse, EntityModel<DtoVentaResponse>>{

    @Override
    public EntityModel<DtoVentaResponse> toModel(DtoVentaResponse venta) {
        return EntityModel.of(venta,
        linkTo(methodOn(VenController.class).obtenerVenta(venta.getNumeroVenta())).withSelfRel(),

        linkTo(methodOn(VenController.class).eliminarVenta(venta.getNumeroVenta())).withRel("Eliminar")
        );
    }

}
