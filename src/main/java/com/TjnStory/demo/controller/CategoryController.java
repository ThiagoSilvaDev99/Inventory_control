package com.TjnStory.demo.controller;

import com.TjnStory.demo.DTO.*;
import com.TjnStory.demo.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Endpoints for category management")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO>  getCategoryById(@RequestParam UUID id){
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryCreateDTO dto, UriComponentsBuilder uriBuilder){
        CategoryResponseDTO newCategory = categoryService.createCategory(dto);

        URI location = uriBuilder.path("/categories/{id}")
                .buildAndExpand(newCategory.id())
                .toUri();

        return ResponseEntity.created(location).body(newCategory);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<CategoryResponseDTO> changeName(@PathVariable UUID id, @RequestBody @Valid CategoryUpdateNameDTO dto){
        CategoryResponseDTO updatedCategory = categoryService.updateName(id, dto);
        return ResponseEntity.ok(updatedCategory);
    }

    @PatchMapping("/{id}/parent")
    public ResponseEntity<CategoryResponseDTO> changeParent(@PathVariable UUID id, @RequestBody CategoryUpdateParentDTO dto){
        CategoryResponseDTO updatedCategory = categoryService.changeParent(id, dto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
