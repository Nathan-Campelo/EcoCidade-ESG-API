package br.com.projetoesg.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CityIndicatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAndListCityIndicator() throws Exception {
        String payload = """
                {
                  "nomeCidade": "Curitiba",
                  "codigoIbge": "4106902",
                  "siglaEstado": "PR",
                  "populacao": 1963726,
                  "percentualEnergiaRenovavel": 73.50,
                  "percentualReciclagemResiduos": 68.20,
                  "notaInclusaoSocial": 81.00,
                  "dataReferencia": "2026-04-20"
                }
                """;

        mockMvc.perform(post("/api/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeCidade").value("Curitiba"))
                .andExpect(jsonPath("$.codigoIbge").value("4106902"))
                .andExpect(jsonPath("$.siglaEstado").value("PR"))
                .andExpect(jsonPath("$.notaEsg").value(74.23));

        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeCidade").value("Curitiba"));
    }

    @Test
    void shouldRejectInvalidPayload() throws Exception {
        String payload = """
                {
                  "nomeCidade": "",
                  "codigoIbge": "123",
                  "siglaEstado": "Parana",
                  "populacao": 0,
                  "percentualEnergiaRenovavel": 101,
                  "percentualReciclagemResiduos": -1,
                  "notaInclusaoSocial": 200,
                  "dataReferencia": "2999-01-01"
                }
                """;

        mockMvc.perform(post("/api/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Dados da requisicao sao invalidos."));
    }
}
