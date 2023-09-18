package lv.ami.fuelmaster.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.Product;

public interface ProductRepository {
    public Product save(Product product);

    public void delete(Product product);

    public Optional<Product> findOptionalById(Long id);
    
    public Product findById(Long id);
    
    public boolean notUsed(String manufacturer, String orderCode);

    public List<Product> findAll();
    
    public Page<Product> findAll(Pageable pageable);
    
    public Long count();
    
    public void deleteById(Long id);
    
    public List<Product> search(String keyword);
    
    public Page<Product> search(String keyword, Pageable pageable);
}
