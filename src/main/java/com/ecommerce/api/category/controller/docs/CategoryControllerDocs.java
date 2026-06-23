package com.ecommerce.api.category.controller.docs;

import com.ecommerce.api.category.dto.CategoryCreateDTO;
import com.ecommerce.api.category.dto.CategoryResponseDTO;
import com.ecommerce.api.category.dto.CategoryUpdateNameDTO;
import com.ecommerce.api.category.dto.CategoryUpdateParentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Tag(name = "Categories", description = "Endpoints para gerenciamento da árvore de categorias da loja")
public interface CategoryControllerDocs {

    @Operation(
            summary = "Listar todas as Categorias",
            description = "Retorna a lista completa de categorias ativas da loja. Ideal para montagem de menus de navegação."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso")
    })
    ResponseEntity<List<CategoryResponseDTO>> getAllCategories();

    @Operation(
            summary = "Busca categoria pelo id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    ResponseEntity<CategoryResponseDTO>  getCategoryById(@PathVariable UUID id);

    @Operation(
            summary = "Criar uma nova categoria",
            description = "Cria uma categoria raiz (se parentId for nulo) ou uma subcategoria. O ID gerado é retornado no header 'Location'."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação (dados inválidos ou faltando)"),
            @ApiResponse(responseCode = "404", description = "Categoria pai informada não existe (se parentId foi passado)")
    })
    ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryCreateDTO dto, UriComponentsBuilder uriBuilder);

    @Operation(
            summary = "Muda o nome da categoria",
            description = "Altera o nome da categoria"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nome atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação (ex: nome em branco)"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    ResponseEntity<CategoryResponseDTO> changeName(@PathVariable UUID id, @RequestBody @Valid CategoryUpdateNameDTO dto);

    @Operation(
            summary = "Mover Categoria (Alterar Pai)",
            description = "Altera a hierarquia da categoria. Passar newParent como null transforma a categoria em raiz."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hierarquia alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria atual ou nova categoria pai não encontrada"),
            @ApiResponse(responseCode = "422", description = "Referência Circular Detectada (tentativa de mover para dentro da própria subárvore)")
    })
    ResponseEntity<CategoryResponseDTO> changeParent(@PathVariable UUID id, @RequestBody CategoryUpdateParentDTO dto);

    @Operation(
            summary = "Deleta a categoria",
            description = "Verifica se a categoria existe e realiza o soft delete"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria desativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito: Não é possível deletar, pois a categoria possui subcategorias ou produtos ativos vinculados")
    })
    ResponseEntity<Void> deleteCategory(@PathVariable UUID id);


}
