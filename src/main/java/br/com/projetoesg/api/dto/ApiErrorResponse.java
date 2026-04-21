package br.com.projetoesg.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(
        int status,
        @JsonProperty("erro")
        String error,
        @JsonProperty("mensagem")
        String message,
        @JsonProperty("detalhes")
        List<String> details,
        @JsonProperty("dataHora")
        LocalDateTime timestamp
) {
}
