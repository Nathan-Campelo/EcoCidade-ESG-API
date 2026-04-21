package br.com.projetoesg.api.controller;

import br.com.projetoesg.api.dto.CityIndicatorRequest;
import br.com.projetoesg.api.dto.CityIndicatorResponse;
import br.com.projetoesg.api.service.CityIndicatorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cities")
public class CityIndicatorController {

    private final CityIndicatorService service;

    public CityIndicatorController(CityIndicatorService service) {
        this.service = service;
    }

    @GetMapping
    public List<CityIndicatorResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CityIndicatorResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityIndicatorResponse create(@Valid @RequestBody CityIndicatorRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public CityIndicatorResponse update(@PathVariable Long id, @Valid @RequestBody CityIndicatorRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
