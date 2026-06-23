package com.ecommerce.api.category.repository;

import com.ecommerce.api.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByParentIdAndActiveTrue(UUID parentId);
}
