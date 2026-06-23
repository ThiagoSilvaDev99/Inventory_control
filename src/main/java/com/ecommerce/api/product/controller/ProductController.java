package com.ecommerce.api.product.controller;

import com.ecommerce.api.product.controller.docs.ProductControllerDocs;
import com.ecommerce.api.product.service.ProductService;
import com.ecommerce.api.product.dto.ProductCreateDTO;
import com.ecommerce.api.product.dto.ProductResponseDTO;
import com.ecommerce.api.product.dto.ProductUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController implements ProductControllerDocs {

    private final ProductService productService;

    @Override
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductCreateDTO dto, UriComponentsBuilder uriBuilder){
        ProductResponseDTO newProduct = productService.createProduct(dto);

        URI location = uriBuilder.path("/products/{id}")
                .buildAndExpand(newProduct.id())
                .toUri();

        return ResponseEntity.created(location).body(newProduct);
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductUpdateDTO dto){
        ProductResponseDTO updatedProduct = productService.update(id, dto);
        return ResponseEntity.ok(updatedProduct);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
