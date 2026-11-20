package org.uns.patitassanta;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.uns.patitassanta.dto.*;
import org.uns.patitassanta.exception.ReglaNegocioException;
import org.uns.patitassanta.service.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReglasNegocioTests {
    @Autowired DuenoService duenos;
    @Autowired MascotaService mascotas;
    @Autowired VeterinarioService veterinarios;
    @Autowired CitaService citas;

    @Test void noPermiteDniDuplicado() {
        var d = new DuenoRequest("12345678", "Ana", "Rojas", "999999999", "ana@test.com", "Chimbote");
        duenos.crear(d);
        var e = assertThrows(ReglaNegocioException.class, () -> duenos.crear(d));
        assertEquals(HttpStatus.CONFLICT, e.getStatus());
    }

    @Test void permiteBuscarMascotasPorDniYNoEliminarDuenoConMascotas() {
        var d = duenos.crear(new DuenoRequest("87654321", "Rosa", "Medina", "988777666", null, "Nuevo Chimbote"));
        var m = mascotas.crear(new MascotaRequest("Luna", org.uns.patitassanta.model.Especie.GATO,
                "Mestiza", org.uns.patitassanta.model.SexoMascota.HEMBRA,
                null, new BigDecimal("3.40"), d.id()));
        assertEquals(m.id(), mascotas.porDni("87654321").getFirst().id());
        assertThrows(ReglaNegocioException.class, () -> duenos.eliminar(d.id()));
    }

    @Test void noPermiteAsignarVeterinarioInactivo() {
        var d = duenos.crear(new DuenoRequest("11112222", "Luis", "Torres", "955111222", null, "Santa"));
        var m = mascotas.crear(new MascotaRequest("Toby", org.uns.patitassanta.model.Especie.PERRO,
                "Mestizo", org.uns.patitassanta.model.SexoMascota.MACHO,
                null, new BigDecimal("5.20"), d.id()));
        var v = veterinarios.crear(new VeterinarioRequest("CMVP-54321", "Marco Paredes", "Cirugía", false));
        assertThrows(ReglaNegocioException.class,
                () -> citas.crear(new CitaRequest(m.id(), v.id(), LocalDateTime.now().plusDays(2), "Chequeo")));
    }
}
