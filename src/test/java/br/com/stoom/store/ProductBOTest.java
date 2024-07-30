package br.com.stoom.store;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ProductBOTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private ProductBO productService;

    public ProductBOTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveProduct() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Texas Instruments");

        Product product = new Product();
        product.setName("MSP430");
        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(19.99);

        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("MSP430");
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getPrice()).isEqualTo(19.99);
    }
}
