package br.com.stoom.store.controller;

import br.com.stoom.store.business.CategoryBO;
import br.com.stoom.store.data.dto.CategoryGetDto;
import br.com.stoom.store.data.dto.CategoryPostDto;
import br.com.stoom.store.data.mappers.MapStructMapper;
import br.com.stoom.store.data.model.Category;
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
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private MapStructMapper mapstructMapper;

    @Autowired
    private CategoryBO categoryService;

    @GetMapping(value = "/")
    public ResponseEntity<List<CategoryGetDto>> findAll() {
        List<Category> c = categoryService.findAll();
        if(!c.isEmpty())
            return new ResponseEntity<>(mapstructMapper.categoriesToCategoriesGetDto(c), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<CategoryGetDto> createCategory(@RequestBody CategoryPostDto categoryPostDto) {
        Category newCategory = categoryService.saveCategory(
                mapstructMapper.categoryPostDtoToCategory(categoryPostDto)
        );
        return new ResponseEntity<>(mapstructMapper.categoryToCategoryGetDto(newCategory), HttpStatus.OK);
    }
}