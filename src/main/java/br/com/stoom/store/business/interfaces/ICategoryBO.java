package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.data.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryBO {

    List<Category> findAll();

    Optional<Category> getCategory(Long id);

    Category saveCategory(Category category);

    Optional<Category> updateCategory(Long id, Category category);
}
