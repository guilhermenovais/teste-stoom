package br.com.stoom.store.controller;

import br.com.stoom.store.business.BrandBO;
import br.com.stoom.store.data.dto.BrandGetDto;
import br.com.stoom.store.data.dto.BrandPostDto;
import br.com.stoom.store.data.mappers.MapStructMapper;
import br.com.stoom.store.data.model.Brand;
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
    private MapStructMapper mapstructMapper;

    @Autowired
    private BrandBO brandService;

    @GetMapping(value = "/")
    public ResponseEntity<List<BrandGetDto>> findAll() {
        List<Brand> b = brandService.findAll();
        if(!b.isEmpty())
            return new ResponseEntity<>(mapstructMapper.brandsToBrandsGetDto(b), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<BrandGetDto> createBrand(@RequestBody BrandPostDto brandPostDto) {
        Brand newBrand = brandService.saveBrand(
                mapstructMapper.brandPostDtoToBrand(brandPostDto)
        );
        return new ResponseEntity<>(mapstructMapper.brandToBrandGetDto(newBrand), HttpStatus.OK);
    }
}