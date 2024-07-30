package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.Category;

import java.util.List;

public interface ICategoryBO {

    List<Category> findAll();

    Category saveCategory(Category category);
}
