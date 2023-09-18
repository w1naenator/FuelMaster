package lv.ami.fuelmaster.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.models.Product;
import lv.ami.fuelmaster.repositories.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product findProductById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Optional<Product> findOptionalById(Long id) {
		return productRepository.findOptionalById(id);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public void save(Product product) {
		productRepository.save(product);
	}

	@Override
	public Product saveAndReturn(Product product) {
		productRepository.save(product);
		return product;
	}

	@Override
	public void delete(Product product) {
		productRepository.delete(product);
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public Long countProducts() {
		return productRepository.count();
	}

	@Override
	public boolean notUsed(String manufacturer, String orderCode) {
		return productRepository.notUsed(manufacturer, orderCode);
	}

	@Override
	public List<Product> search(String keyword) {
		return productRepository.search(keyword);
	}

	@Override
	public Page<Product> search(String keyword, Pageable pageable) {
		return productRepository.search(keyword, pageable);
	}

}
