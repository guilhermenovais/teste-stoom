package br.com.stoom.store;

import br.com.stoom.store.data.model.Brand;
import br.com.stoom.store.data.model.Category;
import br.com.stoom.store.data.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testSaveProduct() {
        Category category = new Category();
        category.setName("Electronics");
        categoryRepository.save(category);

        Brand brand = new Brand();
        brand.setName("Texas Instruments");
        brandRepository.save(brand);

        Product product = new Product();
        product.setName("MSP430");
        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(19.99);

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("MSP430");
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getPrice()).isEqualTo(19.99);
    }
}