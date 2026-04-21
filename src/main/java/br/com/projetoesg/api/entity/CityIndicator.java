package br.com.projetoesg.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "city_indicators")
public class CityIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String cityName;

    @Column(nullable = false, length = 20, unique = true)
    private String ibgeCode;

    @Column(nullable = false, length = 2)
    private String stateCode;

    @Column(nullable = false)
    private Integer population;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal renewableEnergyPercentage;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal wasteRecyclingPercentage;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal socialInclusionScore;

    @Column(nullable = false)
    private LocalDate referenceDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getIbgeCode() {
        return ibgeCode;
    }

    public void setIbgeCode(String ibgeCode) {
        this.ibgeCode = ibgeCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public BigDecimal getRenewableEnergyPercentage() {
        return renewableEnergyPercentage;
    }

    public void setRenewableEnergyPercentage(BigDecimal renewableEnergyPercentage) {
        this.renewableEnergyPercentage = renewableEnergyPercentage;
    }

    public BigDecimal getWasteRecyclingPercentage() {
        return wasteRecyclingPercentage;
    }

    public void setWasteRecyclingPercentage(BigDecimal wasteRecyclingPercentage) {
        this.wasteRecyclingPercentage = wasteRecyclingPercentage;
    }

    public BigDecimal getSocialInclusionScore() {
        return socialInclusionScore;
    }

    public void setSocialInclusionScore(BigDecimal socialInclusionScore) {
        this.socialInclusionScore = socialInclusionScore;
    }

    public LocalDate getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(LocalDate referenceDate) {
        this.referenceDate = referenceDate;
    }
}
