package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.data.dto.ProductGetDto;
import br.com.stoom.store.data.dto.ProductPostDto;
import br.com.stoom.store.data.mappers.MapStructMapper;
import br.com.stoom.store.data.model.Product;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/api/products"}, produces = APPLICATION_JSON_VALUE)
@Api(tags = "Products API")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private MapStructMapper mapstructMapper;

    @Autowired
    private ProductBO productService;

    @Operation(summary = "Get all products", description = "Gets all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found some products", content = {
                    @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProductGetDto.class)))}),
            @ApiResponse(responseCode = "204", description = "No product found", content = @Content)})
    @GetMapping(value = "/")
    public ResponseEntity<List<ProductGetDto>> findAll() {
        List<Product> allProducts = productService.findAll();
        List<ProductGetDto> allProductsDto = mapstructMapper.productsToProductsGetDto(allProducts);
        logger.info(allProductsDto.toString());
        if(!allProductsDto.isEmpty())
            return ResponseEntity.ok(allProductsDto);
        else
            return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get product by ID", description = "Gets a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductGetDto.class))}),
            @ApiResponse(responseCode = "204", description = "Product not found", content = @Content)
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductGetDto> getProduct(
            @PathVariable(value = "id") Long id
    ) {
        Optional<Product> product = productService.getProduct(id);
        if(!product.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        ProductGetDto productDto = mapstructMapper.productToProductGetDto(product.get());
        logger.info(productDto.toString());
        return ResponseEntity.ok(productDto);
    }

    @Operation(summary = "List products by category", description = "Gets all products for a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found products in the category", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductGetDto.class))
                    )}),
            @ApiResponse(responseCode = "204", description = "No products found in the category", content = @Content)
    })
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductGetDto>> listProductsByCategory(
            @PathVariable("categoryId") Long categoryId
    ) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        List<ProductGetDto> productsDto = mapstructMapper.productsToProductsGetDto(products);
        if (productsDto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productsDto);
    }

    @Operation(summary = "List products by brand", description = "Gets all products for a specific brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found products in the brand", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductGetDto.class))
                    )}),
            @ApiResponse(responseCode = "204", description = "No products found in the brand", content = @Content)
    })
    @GetMapping("by-brand/{brandId}")
    public ResponseEntity<List<ProductGetDto>> listProductsByBrand(
            @PathVariable("brandId") Long brandId
    ) {
        List<Product> products = productService.getProductsByBrand(brandId);
        List<ProductGetDto> productsDto = mapstructMapper.productsToProductsGetDto(products);
        if (productsDto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productsDto);
    }

    @Operation(summary = "Search products", description = "Searches products based on a query string")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found products matching the query", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductGetDto.class))
                    )}),
            @ApiResponse(responseCode = "204", description = "No products found matching the query", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProductGetDto>> searchProducts(
            @RequestParam("query") String query
    ) {
        List<Product> products = productService.searchProducts(query);
        List<ProductGetDto> productsDto = mapstructMapper.productsToProductsGetDto(products);
        if (productsDto.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(productsDto);
    }

    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductGetDto.class)
                    )}),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping(value = "/")
    public ResponseEntity<ProductGetDto> createProduct(@Valid @RequestBody ProductPostDto productPostDto) {
        Product newProduct = productService.saveProduct(
            mapstructMapper.productPostDtoToProduct(productPostDto)
        );
        ProductGetDto newProductDto = mapstructMapper.productToProductGetDto(newProduct);
        logger.info(newProductDto.toString());
        return ResponseEntity.ok(newProductDto);
    }

    @Operation(summary = "Update an existing product", description = "Updates an existing product with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductGetDto.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductGetDto> updateProduct(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ProductPostDto productPostDto
    ) {
        Optional<Product> updatedProduct = productService.updateProduct(
                id,
                mapstructMapper.productPostDtoToProduct(productPostDto)
        );
        if(!updatedProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ProductGetDto updatedProductDto = mapstructMapper.productToProductGetDto(updatedProduct.get());
        logger.info(updatedProductDto.toString());
        return ResponseEntity.ok(updatedProductDto);
    }

    @Operation(summary = "Delete a product", description = "Deletes a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductGetDto.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ProductGetDto> deleteProduct(
            @PathVariable(value = "id") Long id
    ) {
        Optional<Product> deletedProduct = productService.deleteProduct(id);
        if(!deletedProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ProductGetDto deletedProductDto = mapstructMapper.productToProductGetDto(deletedProduct.get());
        logger.info(deletedProductDto.toString());
        return ResponseEntity.ok(deletedProductDto);
    }
}