package com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.repository;

import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * The interface Price repository.
 */
@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query(value = "SELECT * FROM prices p " +
            "WHERE p.brand_id = :brandId " +
            "AND p.product_id = :productId " +
            "AND :applicationDate BETWEEN p.start_date AND p.end_date " +
            "ORDER BY p.priority DESC LIMIT 1", nativeQuery = true)
    Optional<PriceEntity> findApplicablePrices(@Param("applicationDate") LocalDateTime applicationDate,
                                               @Param("productId") Long productId,
                                               @Param("brandId") Long brandId);
}
