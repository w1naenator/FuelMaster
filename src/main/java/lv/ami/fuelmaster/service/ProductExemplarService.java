package lv.ami.fuelmaster.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.models.Product;
import lv.ami.fuelmaster.models.ProductExemplar;

@Service
@Transactional
public interface ProductExemplarService {

	public ProductExemplar findById(Long id);

	public Optional<ProductExemplar> findOptionalById(Long id);

	public List<ProductExemplar> findAll();

	public Page<ProductExemplar> findAll(Pageable pageable);

	public void save(ProductExemplar productExemplar);

	public ProductExemplar saveAndReturn(ProductExemplar productExemplar);

	public void delete(ProductExemplar productExemplar);

	public void deleteById(Long id);

	public Long count();
	
	//public boolean notUsed(String manufacturer, String orderCode);
	
	public List<ProductExemplar> search(String keyword);
    
    public Page<ProductExemplar> search(String keyword, Pageable pageable);
}
