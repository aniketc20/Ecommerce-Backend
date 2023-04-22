package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import models.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
	CartEntity findByUsername(String username);
}
