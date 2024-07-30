package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.data.dto.ProductGetDto;
import br.com.stoom.store.data.dto.ProductPostDto;
import br.com.stoom.store.data.mappers.MapStructMapper;
import br.com.stoom.store.data.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {"/api/products"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private MapStructMapper mapstructMapper;

    @Autowired
    private ProductBO productService;

    @GetMapping(value = "/")
    public ResponseEntity<List<ProductGetDto>> findAll() {
        List<Product> allProducts = productService.findAll();
        List<ProductGetDto> allProductsDto = mapstructMapper.productsToProductsGetDto(allProducts);
        logger.info(allProductsDto.toString());
        if(!allProductsDto.isEmpty())
            return ResponseEntity.ok(allProductsDto);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/")
    public ResponseEntity<ProductGetDto> createProduct(@Valid @RequestBody ProductPostDto productPostDto) {
        Product newProduct = productService.saveProduct(
                mapstructMapper.productPostDtoToProduct(productPostDto)
        );
        ProductGetDto newProductDto = mapstructMapper.productToProductGetDto(newProduct);
        logger.info(newProductDto.toString());
        return ResponseEntity.ok(newProductDto);
    }

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