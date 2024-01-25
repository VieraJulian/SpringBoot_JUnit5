package com.api.rest.repository;

import static org.assertj.core.api.Assertions.*;
import com.api.rest.model.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmpleadoRepositoryTests {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    private Empleado empleado;

    @BeforeEach
    void setup(){
        empleado = Empleado.builder()
                .nombre("christian")
                .apellido("Ramirez")
                .email("c1@gmail.com")
                .build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado() {

        Empleado empleado1 = Empleado.builder()
                .nombre("Pepe")
                .apellido("Lopez")
                .email("p12@gmail.com")
                .build();

        Empleado empleadoGuardado = empleadoRepository.save(empleado1);

        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    void testListarEmpleados(){

        Empleado empleado1 = Empleado.builder()
                .nombre("Julen")
                .apellido("Oliva")
                .email("j2@gmail.com")
                .build();

        empleadoRepository.save(empleado1);
        empleadoRepository.save(empleado);

        List<Empleado> listaEmpleados = empleadoRepository.findAll();

        assertThat(listaEmpleados).isNotNull();
        assertThat(listaEmpleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener un empleado por ID")
    @Test
    void testObtenerEmpladoPorId(){

        empleadoRepository.save(empleado);

        Empleado empleadoBD = empleadoRepository.findById(empleado.getId()).orElse(null);

        assertThat(empleadoBD).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado(){
        empleadoRepository.save(empleado);

        Empleado empleadoBD = empleadoRepository.findById(empleado.getId()).orElse(null);
        empleadoBD.setNombre("Christian Raul");
        empleadoBD.setApellido("Ramirez Cucitini");
        empleadoBD.setEmail("c232@gamil.com");

        Empleado empleadoActualizado = empleadoRepository.save(empleadoBD);

        assertThat(empleadoActualizado.getNombre()).isEqualTo("Christian Raul");
        assertThat(empleadoActualizado.getEmail()).isEqualTo("c232@gamil.com");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado() {
        empleadoRepository.save(empleado);

        empleadoRepository.deleteById(empleado.getId());
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleado.getId());

        assertThat(empleadoOptional).isEmpty();
    }
}
