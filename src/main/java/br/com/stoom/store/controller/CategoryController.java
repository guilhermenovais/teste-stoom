package br.com.stoom.store.controller;

import br.com.stoom.store.business.CategoryBO;
import br.com.stoom.store.data.dto.CategoryGetDto;
import br.com.stoom.store.data.dto.CategoryPostDto;
import br.com.stoom.store.data.mappers.MapStructMapper;
import br.com.stoom.store.data.model.Category;
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
@RequestMapping(path = {"/api/categories"}, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Categories API")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private MapStructMapper mapstructMapper;

    @Autowired
    private CategoryBO categoryService;

    @Operation(summary = "Get all categories", description = "Retrieves a list of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CategoryGetDto.class)
                            )
                    )}),
            @ApiResponse(responseCode = "404", description = "No categories found", content = @Content)
    })
    @GetMapping(value = "/")
    public ResponseEntity<List<CategoryGetDto>> findAll() {
        List<Category> allCategories = categoryService.findAll();
        List<CategoryGetDto> allCategoriesDto = mapstructMapper.categoriesToCategoriesGetDto(allCategories);
        logger.info(allCategoriesDto.toString());
        if(!allCategoriesDto.isEmpty())
            return ResponseEntity.ok(allCategoriesDto);
        else
            return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get category by ID", description = "Retrieves the category with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved category", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryGetDto.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryGetDto> getCategory(
            @PathVariable(value = "id") Long id
    ) {
        Optional<Category> category = categoryService.getCategory(id);
        if(!category.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        CategoryGetDto categoryDto = mapstructMapper.categoryToCategoryGetDto(category.get());
        logger.info(categoryDto.toString());
        return ResponseEntity.ok(categoryDto);
    }

    @Operation(summary = "Create a new category", description = "Creates a new category with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created successfully", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryGetDto.class)
                    )}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping(value = "/")
    public ResponseEntity<CategoryGetDto> createCategory(@Valid @RequestBody CategoryPostDto categoryPostDto) {
        Category newCategory = categoryService.saveCategory(
                mapstructMapper.categoryPostDtoToCategory(categoryPostDto)
        );
        CategoryGetDto newCategoryDto = mapstructMapper.categoryToCategoryGetDto(newCategory);
        logger.info(newCategoryDto.toString());
        return ResponseEntity.ok(newCategoryDto);
    }

    @Operation(summary = "Update an existing category", description = "Updates the details of an existing category with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryGetDto.class)
                    )}),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryGetDto> putCategory(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody CategoryPostDto categoryPostDto
    ) {
        Optional<Category> updatedCategory = categoryService.updateCategory(
                id,
                mapstructMapper.categoryPostDtoToCategory(categoryPostDto)
        );
        if(!updatedCategory.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        CategoryGetDto updatedCategoryDto = mapstructMapper.categoryToCategoryGetDto(updatedCategory.get());
        logger.info(updatedCategoryDto.toString());
        return ResponseEntity.ok(updatedCategoryDto);
    }

    @Operation(summary = "Delete a category", description = "Deletes the category with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted successfully", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryGetDto.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CategoryGetDto> deleteCategory(
            @PathVariable(value = "id") Long id
    ) {
        Optional<Category> deletedCategory = categoryService.deleteCategory(id);
        if(!deletedCategory.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        CategoryGetDto deletedCategoryDto = mapstructMapper.categoryToCategoryGetDto(deletedCategory.get());
        logger.info(deletedCategoryDto.toString());
        return ResponseEntity.ok(deletedCategoryDto);
    }
}