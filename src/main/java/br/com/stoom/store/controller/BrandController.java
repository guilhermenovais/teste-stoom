package br.com.stoom.store.controller;

import br.com.stoom.store.business.BrandBO;
import br.com.stoom.store.data.dto.BrandGetDto;
import br.com.stoom.store.data.dto.BrandPostDto;
import br.com.stoom.store.data.mappers.MapStructMapper;
import br.com.stoom.store.data.model.Brand;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {"/api/brands"}, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Brand API")
public class BrandController {

    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private MapStructMapper mapstructMapper;

    @Autowired
    private BrandBO brandService;

    @Operation(summary = "Get all brands", description = "Retrieves a list of all brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found some brands", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BrandGetDto.class))
                    )}),
            @ApiResponse(responseCode = "404", description = "No brands found", content = @Content)
    })
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

    @Operation(summary = "Get a brand by ID", description = "Retrieves a brand by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand found", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BrandGetDto.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BrandGetDto> getBrand(@PathVariable("id") Long id) {
        Optional<Brand> brand = brandService.getBrand(id);
        if (!brand.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        BrandGetDto brandDto = mapstructMapper.brandToBrandGetDto(brand.get());
        logger.info(brandDto.toString());
        return ResponseEntity.ok(brandDto);
    }

    @Operation(summary = "Create a new brand", description = "Creates a new brand with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand created successfully", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BrandGetDto.class)
                    )}),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping(value = "/")
    public ResponseEntity<BrandGetDto> createBrand(@Valid @RequestBody BrandPostDto brandPostDto) {
        Brand newBrand = brandService.saveBrand(
                mapstructMapper.brandPostDtoToBrand(brandPostDto)
        );
        BrandGetDto newBrandDto = mapstructMapper.brandToBrandGetDto(newBrand);
        logger.info(newBrandDto.toString());
        return ResponseEntity.ok(newBrandDto);
    }

    @Operation(summary = "Update an existing brand", description = "Updates the brand with the provided ID using the given details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated successfully", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BrandGetDto.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<BrandGetDto> updateBrand(
            @PathVariable("id") Long id,
            @Valid @RequestBody BrandPostDto brandPostDto
    ) {
        Optional<Brand> updatedBrand = brandService.updateBrand(
                id,
                mapstructMapper.brandPostDtoToBrand(brandPostDto)
        );
        if (!updatedBrand.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        BrandGetDto updatedBrandDto = mapstructMapper.brandToBrandGetDto(updatedBrand.get());
        logger.info(updatedBrandDto.toString());
        return ResponseEntity.ok(updatedBrandDto);
    }

    @Operation(summary = "Delete a brand by ID", description = "Deletes the brand with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand deleted successfully", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BrandGetDto.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BrandGetDto> deleteBrand(@PathVariable("id") Long id) {
        Optional<Brand> deletedBrand = brandService.deleteBrand(id);
        if (!deletedBrand.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        BrandGetDto deletedBrandDto = mapstructMapper.brandToBrandGetDto(deletedBrand.get());
        logger.info(deletedBrandDto.toString());
        return ResponseEntity.ok(deletedBrandDto);
    }
}