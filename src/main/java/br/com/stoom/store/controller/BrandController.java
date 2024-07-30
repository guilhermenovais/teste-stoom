package br.com.stoom.store.controller;

import br.com.stoom.store.business.BrandBO;
import br.com.stoom.store.model.Brand;
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
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandBO brandService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Brand>> findAll() {
        List<Brand> b = brandService.findAll();
        if(!b.isEmpty())
            return new ResponseEntity<>(b, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        Brand newBrand = brandService.saveBrand(brand);
        return new ResponseEntity<>(newBrand, HttpStatus.OK);
    }
}
