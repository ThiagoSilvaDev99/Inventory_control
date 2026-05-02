package com.TjnStory.demo.repository;

import com.TjnStory.demo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByActiveTrue();

    Optional<Product> findByIdAndActiveTrue(UUID id);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByCategoryIdAndActiveTrue(UUID categoryId);
}
