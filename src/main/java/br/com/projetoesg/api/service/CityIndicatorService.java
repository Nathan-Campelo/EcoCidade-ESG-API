package br.com.projetoesg.api.service;

import br.com.projetoesg.api.dto.CityIndicatorRequest;
import br.com.projetoesg.api.dto.CityIndicatorResponse;
import br.com.projetoesg.api.entity.CityIndicator;
import br.com.projetoesg.api.exception.BusinessException;
import br.com.projetoesg.api.exception.ResourceNotFoundException;
import br.com.projetoesg.api.repository.CityIndicatorRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityIndicatorService {

    private final CityIndicatorRepository repository;

    public CityIndicatorService(CityIndicatorRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CityIndicatorResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CityIndicatorResponse findById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Indicador ESG nao encontrado."));
    }

    @Transactional
    public CityIndicatorResponse create(CityIndicatorRequest request) {
        validateIbgeCodeAvailability(request.ibgeCode(), null);
        CityIndicator entity = new CityIndicator();
        applyValues(entity, request);
        return toResponse(repository.save(entity));
    }

    @Transactional
    public CityIndicatorResponse update(Long id, CityIndicatorRequest request) {
        CityIndicator entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Indicador ESG nao encontrado."));

        validateIbgeCodeAvailability(request.ibgeCode(), id);
        applyValues(entity, request);
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Indicador ESG nao encontrado.");
        }
        repository.deleteById(id);
    }

    private void validateIbgeCodeAvailability(String ibgeCode, Long currentId) {
        repository.findByIbgeCode(ibgeCode)
                .filter(existing -> !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new BusinessException("Ja existe um indicador cadastrado para este codigo IBGE.");
                });
    }

    private void applyValues(CityIndicator entity, CityIndicatorRequest request) {
        entity.setCityName(request.cityName().trim());
        entity.setIbgeCode(request.ibgeCode().trim());
        entity.setStateCode(request.stateCode().trim().toUpperCase());
        entity.setPopulation(request.population());
        entity.setRenewableEnergyPercentage(request.renewableEnergyPercentage());
        entity.setWasteRecyclingPercentage(request.wasteRecyclingPercentage());
        entity.setSocialInclusionScore(request.socialInclusionScore());
        entity.setReferenceDate(request.referenceDate());
    }

    private CityIndicatorResponse toResponse(CityIndicator entity) {
        BigDecimal esgScore = entity.getRenewableEnergyPercentage()
                .add(entity.getWasteRecyclingPercentage())
                .add(entity.getSocialInclusionScore())
                .divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);

        return new CityIndicatorResponse(
                entity.getId(),
                entity.getCityName(),
                entity.getIbgeCode(),
                entity.getStateCode(),
                entity.getPopulation(),
                entity.getRenewableEnergyPercentage(),
                entity.getWasteRecyclingPercentage(),
                entity.getSocialInclusionScore(),
                esgScore,
                entity.getReferenceDate()
        );
    }
}
