package com.TjnStory.demo.repository;

import com.TjnStory.demo.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByParentIdAndActiveTrue(UUID parentId);
}
