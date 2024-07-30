package br.com.stoom.store.controller;

import br.com.stoom.store.business.CategoryBO;
import br.com.stoom.store.data.dto.CategoryGetDto;
import br.com.stoom.store.data.dto.CategoryPostDto;
import br.com.stoom.store.data.mappers.MapStructMapper;
import br.com.stoom.store.data.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = {"/api/categories"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private MapStructMapper mapstructMapper;

    @Autowired
    private CategoryBO categoryService;

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

    @PostMapping(value = "/")
    public ResponseEntity<CategoryGetDto> createCategory(@Valid @RequestBody CategoryPostDto categoryPostDto) {
        Category newCategory = categoryService.saveCategory(
                mapstructMapper.categoryPostDtoToCategory(categoryPostDto)
        );
        CategoryGetDto newCategoryDto = mapstructMapper.categoryToCategoryGetDto(newCategory);
        logger.info(newCategoryDto.toString());
        return ResponseEntity.ok(newCategoryDto);
    }
}