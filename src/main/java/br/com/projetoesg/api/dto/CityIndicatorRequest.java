package br.com.projetoesg.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CityIndicatorRequest(
        @NotBlank(message = "O nome da cidade e obrigatorio.")
        @JsonProperty("nomeCidade")
        @JsonAlias("cityName")
        String cityName,

        @NotBlank(message = "O codigo IBGE e obrigatorio.")
        @Pattern(regexp = "^[0-9]{7}$", message = "O codigo IBGE deve conter 7 numeros.")
        @JsonProperty("codigoIbge")
        @JsonAlias("ibgeCode")
        String ibgeCode,

        @NotBlank(message = "A sigla do estado e obrigatoria.")
        @Pattern(regexp = "^[A-Z]{2}$", message = "A sigla do estado deve conter 2 letras maiusculas.")
        @JsonProperty("siglaEstado")
        @JsonAlias("stateCode")
        String stateCode,

        @NotNull(message = "A populacao e obrigatoria.")
        @Min(value = 1, message = "A populacao deve ser maior que zero.")
        @JsonProperty("populacao")
        @JsonAlias("population")
        Integer population,

        @NotNull(message = "O percentual de energia renovavel e obrigatorio.")
        @DecimalMin(value = "0.00", message = "O percentual de energia renovavel nao pode ser negativo.")
        @DecimalMax(value = "100.00", message = "O percentual de energia renovavel nao pode ser maior que 100.")
        @JsonProperty("percentualEnergiaRenovavel")
        @JsonAlias("renewableEnergyPercentage")
        BigDecimal renewableEnergyPercentage,

        @NotNull(message = "O percentual de reciclagem e obrigatorio.")
        @DecimalMin(value = "0.00", message = "O percentual de reciclagem nao pode ser negativo.")
        @DecimalMax(value = "100.00", message = "O percentual de reciclagem nao pode ser maior que 100.")
        @JsonProperty("percentualReciclagemResiduos")
        @JsonAlias("wasteRecyclingPercentage")
        BigDecimal wasteRecyclingPercentage,

        @NotNull(message = "A nota de inclusao social e obrigatoria.")
        @DecimalMin(value = "0.00", message = "A nota de inclusao social nao pode ser negativa.")
        @DecimalMax(value = "100.00", message = "A nota de inclusao social nao pode ser maior que 100.")
        @JsonProperty("notaInclusaoSocial")
        @JsonAlias("socialInclusionScore")
        BigDecimal socialInclusionScore,

        @NotNull(message = "A data de referencia e obrigatoria.")
        @PastOrPresent(message = "A data de referencia nao pode estar no futuro.")
        @JsonProperty("dataReferencia")
        @JsonAlias("referenceDate")
        LocalDate referenceDate
) {
}
