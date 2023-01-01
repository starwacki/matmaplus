package plmatmaplus.matmapluspl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plmatmaplus.matmapluspl.entity.Cart;

import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUserId(Integer userID);

}
