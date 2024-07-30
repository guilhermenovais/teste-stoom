package br.com.stoom.store.data.mappers;

import br.com.stoom.store.data.dto.*;
import br.com.stoom.store.data.model.Brand;
import br.com.stoom.store.data.model.Category;
import br.com.stoom.store.data.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {
    CategoryGetDto categoryToCategoryGetDto(Category category);

    List<CategoryGetDto> categoriesToCategoriesGetDto(List<Category> categories);

    BrandGetDto brandToBrandGetDto(Brand brand);

    List<BrandGetDto> brandsToBrandsGetDto(List<Brand> brands);

    ProductGetDto productToProductGetDto(Product product);

    List<ProductGetDto> productsToProductsGetDto(List<Product> products);

    Category categoryGetDtoToCategory(CategoryGetDto categoryGetDto);

    Brand brandGetDtoToBrand(BrandGetDto brandGetDto);

    Category categoryPostDtoToCategory(CategoryPostDto categoryPostDto);

    Brand brandPostDtoToBrand(BrandPostDto brandPostDto);

    Product productPostDtoToProduct(ProductPostDto productPostDto);
}
