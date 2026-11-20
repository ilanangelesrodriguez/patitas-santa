package org.uns.patitassanta;

import org.junit.jupiter.api.Test;
import org.uns.patitassanta.config.OpenApiConfig;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTests {

    @Test
    void swaggerUsesRelativeServerToAvoidMixedContent() {
        var openApi = new OpenApiConfig().patitasSantaOpenApi();
        assertNotNull(openApi.getServers());
        assertEquals("/", openApi.getServers().get(0).getUrl());
        assertEquals("Patitas del Santa - API REST", openApi.getInfo().getTitle());
    }
}
