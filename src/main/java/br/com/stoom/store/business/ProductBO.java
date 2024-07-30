package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.data.model.Product;
import br.com.stoom.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductBO implements IProductBO {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
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
