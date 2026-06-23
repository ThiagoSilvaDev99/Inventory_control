package com.ecommerce.api.category.entity;

import com.ecommerce.api.shared.exception.CategoryValidationException;
import com.ecommerce.api.shared.util.NameNormalizer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "parent_id"})
})

public class Category {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "category_id", columnDefinition = "VARCHAR(36)")
    private UUID id;

    @NotBlank
    @Column(name = "category_name")
    private String name;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(name = "active",  nullable = false)
    private boolean active = true;

    private Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    public static Category createCategory(String name, Category parent) {
        if(name == null || name.isBlank()){
            throw new CategoryValidationException("Category name is null or blank");
        }

        return new Category(NameNormalizer.normalize(name), parent);
    }

    public void changeName(String newName){
        if(name == null || name.isBlank()){
            throw new CategoryValidationException("Category's new name is null or blank");
        }

        this.name = NameNormalizer.normalize(newName);
    }

    public void changeParent(Category newParent) {

        if (newParent == null) {
            this.parent = null;
            return;
        }

        if (this.id.equals(newParent.getId())) {
            throw new CategoryValidationException("A category cannot be its own parent.");
        }

        Category ancestor = newParent.getParent();

        while (ancestor != null) {

            if (ancestor.getId().equals(this.id)) {
                throw new CategoryValidationException("Circular reference detected: a category cannot be moved inside its own subcategory");
            }

            ancestor = ancestor.getParent();
        }

        this.parent = newParent;
    }

    public void deactivate(){
        this.active = false;
    }

    public void activate(){
        this.active = true;
    }

}
