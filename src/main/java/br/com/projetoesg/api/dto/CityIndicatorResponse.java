package br.com.projetoesg.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CityIndicatorResponse(
        Long id,
        @JsonProperty("nomeCidade")
        String cityName,
        @JsonProperty("codigoIbge")
        String ibgeCode,
        @JsonProperty("siglaEstado")
        String stateCode,
        @JsonProperty("populacao")
        Integer population,
        @JsonProperty("percentualEnergiaRenovavel")
        BigDecimal renewableEnergyPercentage,
        @JsonProperty("percentualReciclagemResiduos")
        BigDecimal wasteRecyclingPercentage,
        @JsonProperty("notaInclusaoSocial")
        BigDecimal socialInclusionScore,
        @JsonProperty("notaEsg")
        BigDecimal esgScore,
        @JsonProperty("dataReferencia")
        LocalDate referenceDate
) {
}
