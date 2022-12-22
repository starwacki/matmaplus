package plmatmaplus.matmapluspl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plmatmaplus.matmapluspl.entity.PromoCode;

import java.util.Optional;

public interface PromoCodeRepository extends JpaRepository<PromoCode,Long> {

    Optional<PromoCode> findPromoCodeByCode(String code);

}
