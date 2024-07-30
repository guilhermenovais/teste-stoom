package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.data.model.Brand;

import java.util.List;

public interface IBrandBO {
    List<Brand> findAll();

    Brand saveBrand(Brand brand);
}
