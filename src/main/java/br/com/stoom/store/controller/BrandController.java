package br.com.stoom.store.controller;

import br.com.stoom.store.business.BrandBO;
import br.com.stoom.store.data.dto.BrandGetDto;
import br.com.stoom.store.data.dto.BrandPostDto;
import br.com.stoom.store.data.mappers.MapStructMapper;
import br.com.stoom.store.data.model.Brand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = {"/api/brands"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class BrandController {

    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private MapStructMapper mapstructMapper;

    @Autowired
    private BrandBO brandService;

    @GetMapping(value = "/")
    public ResponseEntity<List<BrandGetDto>> findAll() {
        List<Brand> allBrands = brandService.findAll();
        List<BrandGetDto> allBrandsDto = mapstructMapper.brandsToBrandsGetDto(allBrands);
        logger.info(allBrandsDto.toString());
        if(!allBrandsDto.isEmpty())
            return ResponseEntity.ok(allBrandsDto);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/")
    public ResponseEntity<BrandGetDto> createBrand(@Valid @RequestBody BrandPostDto brandPostDto) {
        Brand newBrand = brandService.saveBrand(
                mapstructMapper.brandPostDtoToBrand(brandPostDto)
        );
        BrandGetDto newBrandDto = mapstructMapper.brandToBrandGetDto(newBrand);
        logger.info(newBrandDto.toString());
        return ResponseEntity.ok(newBrandDto);
    }
}