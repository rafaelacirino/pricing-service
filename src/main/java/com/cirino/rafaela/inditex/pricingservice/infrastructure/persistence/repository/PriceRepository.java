package com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.repository;

import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    List<PriceEntity> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long productId,
            Long brandId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
