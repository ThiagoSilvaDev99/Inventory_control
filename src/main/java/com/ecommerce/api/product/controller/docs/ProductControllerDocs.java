package com.ecommerce.api.product.controller.docs;

import com.ecommerce.api.product.dto.ProductCreateDTO;
import com.ecommerce.api.product.dto.ProductResponseDTO;
import com.ecommerce.api.product.dto.ProductUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Tag(name = "Products", description = "Endpoints para gerenciamento do catálogo de produtos da loja")
public interface ProductControllerDocs {

    @Operation(summary = "Listar todos os Produtos", description = "Retorna o catálogo completo de produtos ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    ResponseEntity<List<ProductResponseDTO>> getAllProducts();

    @Operation(summary = "Buscar Produto por ID", description = "Retorna os detalhes de um produto específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    ResponseEntity<ProductResponseDTO> getProductById(UUID id);

    @Operation(summary = "Criar um novo Produto", description = "Cadastra um produto vinculando-o a uma categoria existente. O ID gerado retorna no header 'Location'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação (ex: campos em branco, preços negativos)"),
            @ApiResponse(responseCode = "404", description = "Categoria informada não existe"),
            @ApiResponse(responseCode = "409", description = "Conflito: Produto com este nome já existe"),
            @ApiResponse(responseCode = "422", description = "Regra de Negócio: Preço de venda não pode ser menor que o preço de custo")
    })
    ResponseEntity<ProductResponseDTO> createProduct(ProductCreateDTO dto, UriComponentsBuilder uriBuilder);

    @Operation(summary = "Atualizar Produto", description = "Atualiza os dados de um produto existente (ex: preço, estoque).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "422", description = "Regra de Negócio: Preço de venda menor que custo")
    })
    ResponseEntity<ProductResponseDTO> updateProduct(UUID id, ProductUpdateDTO dto);

    @Operation(summary = "Deletar Produto", description = "Realiza o soft delete de um produto do catálogo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    ResponseEntity<Void> deleteProduct(UUID id);
}
