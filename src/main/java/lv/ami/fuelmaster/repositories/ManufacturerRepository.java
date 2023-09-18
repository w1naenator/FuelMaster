package lv.ami.fuelmaster.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.Manufacturer;

public interface ManufacturerRepository {
    public Manufacturer save(Manufacturer product);

    public void delete(Manufacturer product);

    public Optional<Manufacturer> findOptionalById(Long id);
    
    public Manufacturer findById(Long id);
    
    public boolean notUsed(String manufacturer);

    public List<Manufacturer> findAll();
    
    public Page<Manufacturer> findAll(Pageable pageable);
    
    public Long count();
    
    public void deleteById(Long id);
    
    public List<Manufacturer> search(String keyword);
    
    public Page<Manufacturer> search(String keyword, Pageable pageable);
}
