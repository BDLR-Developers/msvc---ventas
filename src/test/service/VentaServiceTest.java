package cl.venta.ventas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import cl.venta.ventas.model.DetVenta;
import cl.venta.ventas.model.Venta;
import cl.venta.ventas.model.claves.DetalleVentaId;
import cl.venta.ventas.model.dto.DtoVentaRequest;
import cl.venta.ventas.model.dto.DtoVentaResponse;
import cl.venta.ventas.model.interfaces.DetalleVentaInterface;
import cl.venta.ventas.repository.DetVentaRepository;
import cl.venta.ventas.repository.VentRepository;


public class VentaServiceTest {

    @Mock
    private DetVentaRepository detalleRepo;

    @Mock
    private VentRepository ventaRepo;

    @InjectMocks
    private VentService ventaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerDetallePorNumeroVenta() {
        //Mock de la interfaz
        DetalleVentaInterface detalleMock = mock(DetalleVentaInterface.class);
        when(detalleMock.getNumeroVenta()).thenReturn(1);
        when(detalleMock.getIdProducto()).thenReturn(100);
        when(detalleMock.getCantidad()).thenReturn(2);
        when(detalleMock.getNombreProducto()).thenReturn("Producto A");
        when(detalleMock.getFechaVenta()).thenReturn(LocalDate.now());
        when(detalleMock.getPrecioUnitario()).thenReturn(50);
        when(detalleMock.getCantidadVendidas()).thenReturn(5);
        when(detalleMock.getTotalVendido()).thenReturn(250);

        List<DetalleVentaInterface> listaMock = List.of(detalleMock);

        // Stub del repositorio para devolver la lista mockeada

        when(detalleRepo.obtenerDetallePorNumeroVenta(1)).thenReturn(listaMock);

        //Ejecutamos el método del servicio
        List<DetalleVentaInterface> resultado = ventaService.obtenerDetallePorNumeroVenta(1);

        //Verificaciones

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getNumeroVenta());
        assertEquals("Producto A", resultado.get(0).getNombreProducto());

        // Verifica que se llamó el método del repositorio con el id correcto
        verify(detalleRepo).obtenerDetallePorNumeroVenta(1);
        
    }

    @Test
    void testCrearVentaConDetalles(){
        DtoVentaRequest postVenta = new DtoVentaRequest();
        postVenta.setFechaVenta(LocalDate.now());
        postVenta.setCorreoCliente("cliente@test.com");
        postVenta.setEstadoVenta(1);
        postVenta.setIdBodega(1);
        postVenta.setIdUsuario(1);

        DtoVentaRequest.DetalleRequestVenta detReq = new DtoVentaRequest.DetalleRequestVenta();
        detReq.setIdProducto(1);
        detReq.setCantidad(3);
        detReq.setPrecio(1500);
        postVenta.setDetalles(List.of(detReq));

        //Mock venta save: para que el numero venta esté presente, capturamos y seteamos el id
        doAnswer(invocation -> {
            Venta v = invocation.getArgument(0);
            v.setNumeroVenta(100); //simula el id generado
            return v;
        }).when(ventaRepo).save(any(Venta.class));

        doAnswer(invocation -> invocation.getArgument(0))
        .when(detalleRepo).save(any(DetVenta.class));

        ventaService.crearVentaConDetalles(postVenta);

        verify(ventaRepo).save(any(Venta.class));
        verify(detalleRepo).save(any(DetVenta.class));

    }

    @Test
    void testObtenerVentaFound(){
        Venta venta = new Venta();
        venta.setNumeroVenta(1);
        venta.setFechaVenta(LocalDate.now());
        venta.setCorreoCliente("cliente@test.com");
        venta.setEstadoVenta(1);
        venta.setIdBodega(1);
        venta.setIdUsuario(1);

        //Set productos para simular detalles
        DetVenta det = new DetVenta();
        DetalleVentaId id = new DetalleVentaId(1, 10);
        det.setId(id);
        det.setCantidad(5);
        det.setPrecio(2500);
        det.setVenta(venta);
        List<DetVenta> detalles = List.of(det);
        venta.setProductos(detalles);

        when(ventaRepo.findById(1)).thenReturn(Optional.of(venta));

        DtoVentaResponse response = ventaService.obtenerVenta(1);

        assertNotNull(response);
        assertEquals(1, response.getNumeroVenta());
        assertEquals("cliente@test.com", response.getCorreoCliente());
        assertEquals(1, response.getProductos().size());

        verify(ventaRepo).findById(1);
    }

    @Test
    void testObtenerVentaNotFound(){
        when(ventaRepo.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ventaService.obtenerVenta(999);
        });

        assertEquals("Venta no encontrada", exception.getMessage());
        verify(ventaRepo).findById(999);
    }


    

}
