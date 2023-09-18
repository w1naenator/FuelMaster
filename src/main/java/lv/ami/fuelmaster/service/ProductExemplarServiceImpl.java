package lv.ami.fuelmaster.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.models.Product;
import lv.ami.fuelmaster.models.ProductExemplar;
import lv.ami.fuelmaster.repositories.ProductExemplarRepository;
import lv.ami.fuelmaster.repositories.ProductRepository;

@Service
@Transactional
public class ProductExemplarServiceImpl implements ProductExemplarService {

	@Autowired
	private ProductExemplarRepository productExemplarRepository;

	@Override
	public ProductExemplar findById(Long id) {
		return productExemplarRepository.findById(id);
	}

	@Override
	public Optional<ProductExemplar> findOptionalById(Long id) {
		return productExemplarRepository.findOptionalById(id);
	}

	@Override
	public List<ProductExemplar> findAll() {
		return productExemplarRepository.findAll();
	}

	@Override
	public Page<ProductExemplar> findAll(Pageable pageable) {
		return productExemplarRepository.findAll(pageable);
	}

	@Override
	public void save(ProductExemplar productExemplar) {
		productExemplarRepository.save(productExemplar);
	}

	@Override
	public ProductExemplar saveAndReturn(ProductExemplar productExemplar) {
		productExemplarRepository.save(productExemplar);
		return productExemplar;
	}

	@Override
	public void delete(ProductExemplar product) {
		productExemplarRepository.delete(product);
	}

	@Override
	public void deleteById(Long id) {
		productExemplarRepository.deleteById(id);
	}

	@Override
	public Long count() {
		return productExemplarRepository.count();
	}

	/*@Override
	public boolean notUsed(String manufacturer, String orderCode) {
		return productExemplarRepository.notUsed(manufacturer, orderCode);
	}*/

	@Override
	public List<ProductExemplar> search(String keyword) {
		return productExemplarRepository.search(keyword);
	}

	@Override
	public Page<ProductExemplar> search(String keyword, Pageable pageable) {
		return productExemplarRepository.search(keyword, pageable);
	}

}
