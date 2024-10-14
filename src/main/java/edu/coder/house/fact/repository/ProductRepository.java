package edu.coder.house.fact.repository;

import edu.coder.house.fact.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    void deleteById(Long id);
}
