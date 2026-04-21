package br.com.projetoesg.api.repository;

import br.com.projetoesg.api.entity.CityIndicator;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityIndicatorRepository extends JpaRepository<CityIndicator, Long> {

    Optional<CityIndicator> findByIbgeCode(String ibgeCode);
}
