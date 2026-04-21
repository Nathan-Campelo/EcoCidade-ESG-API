package br.com.projetoesg.api.controller;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Value("${app.environment:local}")
    private String environment;

    @GetMapping
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "servico", "ecocidade-esg-api",
                "ambiente", environment,
                "dataHora", LocalDateTime.now()
        );
    }
}
