package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.data.model.Category;
import br.com.stoom.store.data.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductBO {

    List<Product> findAll();

    Optional<Product> getProduct(Long id);

    List<Product> getProductsByCategory(Long categoryId);

    List<Product> getProductsByBrand(Long brandId);

    Product saveProduct(Product product);

    Optional<Product> updateProduct(Long id, Product product);

    Optional<Product> deleteProduct(Long id);
}
