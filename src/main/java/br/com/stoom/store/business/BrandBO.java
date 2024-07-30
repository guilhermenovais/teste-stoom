package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.data.model.Brand;
import br.com.stoom.store.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandBO implements IBrandBO {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> getBrand(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> updateBrand(Long id, Brand brand) {
        Optional<Brand> brandToUpdate = brandRepository.findById(id);
        if (!brandToUpdate.isPresent()) return brandToUpdate;
        brand.setId(id);
        return Optional.of(brandRepository.save(brand));
    }

    @Override
    public Optional<Brand> deleteBrand(Long id) {
        Optional<Brand> brandToDelete = brandRepository.findById(id);
        brandToDelete.ifPresent(brand -> brandRepository.delete(brand));
        return brandToDelete;
    }
}
