package lv.ami.fuelmaster.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.models.Product;

@Service
@Transactional
public interface ProductService {

	public Product findProductById(Long id);

	public Optional<Product> findOptionalById(Long id);

	public List<Product> findAll();

	public Page<Product> findAll(Pageable pageable);

	public void save(Product product);

	public Product saveAndReturn(Product product);

	public void delete(Product product);

	public void deleteById(Long id);

	public Long countProducts();
	
	public boolean notUsed(String manufacturer, String orderCode);
	
	public List<Product> search(String keyword);
    
    public Page<Product> search(String keyword, Pageable pageable);
}
