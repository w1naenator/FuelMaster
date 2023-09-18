package lv.ami.fuelmaster.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.ProductExemplar;

public interface ProductExemplarRepository {
    public ProductExemplar save(ProductExemplar productExemplar);

    public void delete(ProductExemplar productExemplar);

    public Optional<ProductExemplar> findOptionalById(Long id);
    
    public ProductExemplar findById(Long id);

    public List<ProductExemplar> findAll();
    
    public Page<ProductExemplar> findAll(Pageable pageable);
    
    public void deleteById(Long id);
    
    public Long count();
    
	 public List<ProductExemplar> search(String keyword);

	 public Page<ProductExemplar> search(String keyword, Pageable pageable);
}
