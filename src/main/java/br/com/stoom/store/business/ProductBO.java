package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.data.model.Brand;
import br.com.stoom.store.data.model.Category;
import br.com.stoom.store.data.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductBO implements IProductBO {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            return productRepository.findByCategory(categoryId);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Product> getProductsByBrand(Long brandId) {
        Optional<Brand> brand = brandRepository.findById(brandId);
        if (brand.isPresent()) {
            return productRepository.findByBrand(brandId);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> updateProduct(Long id, Product product) {
        Optional<Product> productToUpdate = productRepository.findById(id);
        if(!productToUpdate.isPresent()) return productToUpdate;
        product.setId(id);
        return Optional.of(productRepository.save(product));
    }

    @Override
    public Optional<Product> deleteProduct(Long id) {
        Optional<Product> productToDelete = productRepository.findById(id);
        productToDelete.ifPresent(product -> productRepository.delete(product));
        return productToDelete;
    }
}
