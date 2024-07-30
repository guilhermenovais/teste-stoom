package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.data.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductBO {

    List<Product> findAll();

    Product saveProduct(Product product);

    Optional<Product> updateProduct(Long id, Product product);

    Optional<Product> deleteProduct(Long id);
}
