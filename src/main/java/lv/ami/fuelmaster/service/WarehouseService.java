package lv.ami.fuelmaster.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.dtos.ProductDto;
import lv.ami.fuelmaster.dtos.WarehouseDto;
import lv.ami.fuelmaster.models.Product;
import lv.ami.fuelmaster.models.Warehouse;

@Service
@Transactional
public interface WarehouseService {

	Warehouse convertDtoToWarehouse(WarehouseDto warehouseDto);
	
	WarehouseDto convertWarehousetoDto(Warehouse warehouse);
	
	List<Warehouse> convertStockDTOs2Stocks(List<WarehouseDto> warehouseDtos);
	
	List<WarehouseDto> convertStocksToDTOs(List<Warehouse> warehouses);
    
	Product convertDTOToProduct(ProductDto productDto);
	
	ProductDto convertProductToDto(Product product);
	
	List<Product> convertDtosToProducts(List<ProductDto> hydraulicRotatorMeasurementsSetDTOs);
	
	List<ProductDto> convertProductsToDTOs(List<Product> hydraulicRotatorMeasurementsSets);
	
    
    Warehouse findById(Long id);
    
    Optional<Warehouse> findOptionalById(Long id);

    List<Warehouse> findAll();
    
    Page<Warehouse> findAll(Pageable pageable);
    
    
    void save(Warehouse hydraulicRotatorTest);
    
    Warehouse saveAndReturn(Warehouse hydraulicRotatorTest);
    
    void delete(Warehouse hydraulicRotatorTest);
    
    void deleteById(Long id);
    
    
    Long countUsers();
}
