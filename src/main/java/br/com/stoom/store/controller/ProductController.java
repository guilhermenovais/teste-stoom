package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.data.dto.ProductGetDto;
import br.com.stoom.store.data.dto.ProductPostDto;
import br.com.stoom.store.data.mappers.MapStructMapper;
import br.com.stoom.store.data.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private MapStructMapper mapstructMapper;

    @Autowired
    private ProductBO productService;

    @GetMapping(value = "/")
    public ResponseEntity<List<ProductGetDto>> findAll() {
        List<Product> p = productService.findAll();
        if(!p.isEmpty())
            return new ResponseEntity<>(mapstructMapper.productsToProductsGetDto(p), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<ProductGetDto> createProduct(@RequestBody ProductPostDto productPostDto) {
        Product newProduct = productService.saveProduct(
                mapstructMapper.productPostDtoToProduct(productPostDto)
        );
        return new ResponseEntity<>(mapstructMapper.productToProductGetDto(newProduct), HttpStatus.OK);
    }
}