package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.data.dto.ProductGetDto;
import br.com.stoom.store.data.dto.ProductPostDto;
import br.com.stoom.store.data.mappers.MapStructMapper;
import br.com.stoom.store.data.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return new ResponseEntity<>(allProductsDto, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<ProductGetDto> createProduct(@RequestBody ProductPostDto productPostDto) {
        Product newProduct = productService.saveProduct(
                mapstructMapper.productPostDtoToProduct(productPostDto)
        );
        ProductGetDto newProductDto = mapstructMapper.productToProductGetDto(newProduct);
        logger.info(newProductDto.toString());
        return new ResponseEntity<>(newProductDto, HttpStatus.OK);
    }
}