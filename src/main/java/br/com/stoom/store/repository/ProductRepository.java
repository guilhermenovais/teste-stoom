package br.com.stoom.store.repository;

import br.com.stoom.store.data.model.Category;
import br.com.stoom.store.data.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
            "JOIN p.category c " +
            "JOIN p.brand b " +
            "WHERE p.enabled = true AND c.enabled = true AND b.enabled = true " +
            "AND c.id = :categoryId")
    List<Product> findByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p " +
            "JOIN p.category c " +
            "JOIN p.brand b " +
            "WHERE p.enabled = true AND c.enabled = true AND b.enabled = true " +
            "AND b.id = :brandId")
    List<Product> findByBrand(@Param("brandId") Long brandId);
}