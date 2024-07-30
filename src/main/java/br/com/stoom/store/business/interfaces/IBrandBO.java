package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.data.model.Brand;

import java.util.List;
import java.util.Optional;

public interface IBrandBO {
    List<Brand> findAll();

    Optional<Brand> getBrand(Long id);

    Brand saveBrand(Brand brand);
}
