package com.TjnStory.demo.entities;

import com.TjnStory.demo.exceptions.ProductValidationException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Product {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "product_id", columnDefinition = "VARCHAR(36)")
    private UUID id;

    @NotBlank
    @Column(name = "product_name")
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @Positive
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    @NotNull
    @Positive
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @Positive
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "active",  nullable = false)
    private boolean active = true;

    private Product(String name, Category category, BigDecimal costPrice, BigDecimal price, Integer stockQuantity) {
        this.name = name;
        this.category = category;
        this.costPrice = costPrice;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public static Product create(String name, Category category, BigDecimal costPrice, BigDecimal price, Integer stockQuantity){

        if(price.compareTo(costPrice) < 0){
            throw new ProductValidationException("The price must be higher than the cost price.");
        }

        return new Product(name, category, costPrice, price, stockQuantity);
    }

    public void changePrice(BigDecimal newPrice) {

        if(newPrice == null){
            throw new ProductValidationException("New price cannot be null");
        }

        if(newPrice.compareTo(costPrice) < 0){
             throw new ProductValidationException("New price must be greater than or equal to cost price");
        }

        this.price = newPrice;
    }

    public void deactivate(){
        this.active = false;
    }

    public void activate(){
        this.active = true;
    }

}
