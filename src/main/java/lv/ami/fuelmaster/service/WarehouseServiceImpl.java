package lv.ami.fuelmaster.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.dtos.ProductDto;
import lv.ami.fuelmaster.dtos.WarehouseDto;
import lv.ami.fuelmaster.models.Product;
import lv.ami.fuelmaster.models.Warehouse;
import lv.ami.fuelmaster.repositories.ShelfRepository;
import lv.ami.fuelmaster.repositories.WarehouseRepository;

@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {
	
	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Autowired
	private ShelfRepository shelfRepository;

	@Override
	public Warehouse convertDtoToWarehouse(WarehouseDto warehouseDto) {
		Warehouse warehouse = null;
		//if (hydraulicRotatorTestDTO.getAppUserId() != null) {
		//	hydraulicRotatorTest = warehouseRepository.findById(hydraulicRotatorTestDTO.getAppUserId());
		//}
		if (warehouse == null) {
			warehouse = new Warehouse();
		}
		warehouse.setId(warehouseDto.getId());
		warehouse.setName(warehouseDto.getName());
		
		
		return warehouse;
	}

	@Override
	public WarehouseDto convertWarehousetoDto(Warehouse warehouse) {
		 WarehouseDto dto = new WarehouseDto();
	       if (warehouse != null) {
	           dto.setId(warehouse.getId());
	           dto.setName(warehouse.getName());
	       }
	       return dto;
	}
	
	@Override
	public List<Warehouse> convertStockDTOs2Stocks(List<WarehouseDto> warehouseDtos) {
		List<Warehouse> warehouses = new ArrayList<>();
		if (warehouseDtos != null) {
			for (WarehouseDto testdto : warehouseDtos) {
				warehouses.add(convertDtoToWarehouse(testdto));
			}
		}
		return warehouses;
	}

	@Override
	public List<WarehouseDto> convertStocksToDTOs(List<Warehouse> warehouses) {
		List<WarehouseDto> stockdtos = new ArrayList<>();
		if (warehouses != null) {
			for (Warehouse warehouse : warehouses) {
				stockdtos.add(convertWarehousetoDto(warehouse));
			}
		}
		return stockdtos;
	}
	
	@Override
	public Product convertDTOToProduct(ProductDto productDto) {
		Product product = new Product();
		if (productDto != null) {
			product.setId(productDto.getId());
			product.setOrderCode(productDto.getOrderCode());
			product.setShortDescription(productDto.getShortDescription());
			product.setLongDescription(productDto.getLongDescription());
		}
		return product;
	}
	
	

	@Override
	public ProductDto convertProductToDto(Product product) {
		ProductDto productDto = new ProductDto();
		if (product != null) {
			productDto.setId(product.getId());
			productDto.setOrderCode(product.getOrderCode());
			productDto.setShortDescription(product.getShortDescription());
			productDto.setLongDescription(product.getLongDescription());
		}
		return productDto;
	}
	
	@Override
	public List<Product> convertDtosToProducts(List<ProductDto> productDtos) {
		List<Product> products = new ArrayList<>();
		if (productDtos != null) {
			for (ProductDto productdtos : productDtos) {
				products.add(convertDTOToProduct(productdtos));
			}
		}
		return products;
	}

	@Override
	public List<ProductDto> convertProductsToDTOs(List<Product> products) {
		List<ProductDto> setdtos = new ArrayList<>();
		if (products != null) {
			for (Product set : products) {
				setdtos.add(convertProductToDto(set));
			}
		}
		return setdtos;
	}

	@Override
	public Warehouse findById(Long id) {
		return warehouseRepository.findById(id);
	}

	@Override
	public Optional<Warehouse> findOptionalById(Long id) {
		return warehouseRepository.findOptionalById(id);
	}

	@Override
	public List<Warehouse> findAll() {
		return warehouseRepository.findAll();
	}

	@Override
	public Page<Warehouse> findAll(Pageable pageable) {
		return warehouseRepository.findAll(pageable);
	}

	@Override
	public void save(Warehouse hydraulicRotatorTest) {
		warehouseRepository.save(hydraulicRotatorTest);
	}

	@Override
	public Warehouse saveAndReturn(Warehouse hydraulicRotatorTest) {
		warehouseRepository.save(hydraulicRotatorTest);
		return hydraulicRotatorTest;
	}
	
	@Override
	public void delete(Warehouse hydraulicRotatorTest) {
		warehouseRepository.delete(hydraulicRotatorTest);
	}

	@Override
	public void deleteById(Long id) {
		warehouseRepository.deleteById(id);
	}

	@Override
	public Long countUsers() {
		return warehouseRepository.countUsers();
	}









}
