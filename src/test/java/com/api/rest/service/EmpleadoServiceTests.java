package com.api.rest.service;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.api.rest.exception.ResourceNotFoundException;
import com.api.rest.model.Empleado;
import com.api.rest.repository.EmpleadoRepository;
import com.api.rest.service.impl.EmpleadoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTests {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    private Empleado empleado;

    @BeforeEach
    void setup(){
        empleado = Empleado.builder()
                .id(2L)
                .nombre("christian")
                .apellido("Ramirez")
                .email("c1@gmail.com")
                .build();
    }

    @DisplayName("Test para guardar empleado")
    @Test
    void testGuardarEmpleado() {
        given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.empty());
        given(empleadoRepository.save(empleado)).willReturn(empleado);

        Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);

        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para guardar empleado con Throw Exception")
    @Test
    void testGuardarEmpleadoConThrowException() {
        given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.of(empleado));

        assertThrows(ResourceNotFoundException.class, () -> {
            empleadoService.saveEmpleado(empleado);
        });

        verify(empleadoRepository, never()).save(any(Empleado.class));
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    void testListarEmpleados(){
        Empleado empleado1 = Empleado.builder()
                .id(1L)
                .nombre("Julen")
                .apellido("Oliva")
                .email("j2@gmail.com")
                .build();

        given(empleadoRepository.findAll()).willReturn(List.of(empleado, empleado1));

        List<Empleado> empleados = empleadoService.getAllEmpleados();

        assertThat(empleados).isNotEmpty();
        assertThat(empleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para retornar una lista vacia")
    @Test
    void testListarColeccionEmpleadosVacia(){
        given(empleadoRepository.findAll()).willReturn(Collections.emptyList());

        List<Empleado> empleados = empleadoService.getAllEmpleados();

        assertThat(empleados).isEmpty();
        assertThat(empleados.size()).isEqualTo(0);
    }

    @DisplayName("Test para obtener un empleado por ID")
    @Test
    void testObtenerEmpleadoPorId(){
        given(empleadoRepository.findById(empleado.getId())).willReturn(Optional.of(empleado));

        Optional<Empleado> empleadoDB = empleadoService.getEmpleadoById(empleado.getId());

        assertThat(empleadoDB).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado(){
        given(empleadoRepository.save(empleado)).willReturn(empleado);
        empleado.setNombre("Fernando");
        empleado.setApellido("Fernandez");
        empleado.setEmail("ff@gmail.com");

        Empleado empleadoActualizado = empleadoService.updateEmpleado(empleado);

        assertThat(empleadoActualizado).isNotNull();
        assertEquals(empleadoActualizado.getNombre(),  "Fernando");
        assertEquals(empleadoActualizado.getApellido(),  "Fernandez");
        assertEquals(empleadoActualizado.getEmail(),  "ff@gmail.com");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado() {
        willDoNothing().given(empleadoRepository).deleteById(1L);

        empleadoService.deleteEmpleado(1L);

        verify(empleadoRepository, times(1)).deleteById(1L);
    }
}




































