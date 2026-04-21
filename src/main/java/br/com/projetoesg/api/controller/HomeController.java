package br.com.projetoesg.api.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
                "mensagem", "EcoCidade ESG API em execucao.",
                "endpoints", Map.of(
                        "health", "/api/health",
                        "cidades", "/api/cities"
                )
        );
    }
}
