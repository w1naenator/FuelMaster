package lv.ami.fuelmaster.utils;

import java.util.List;

import lv.ami.fuelmaster.dtos.ProductDto;
import lv.ami.fuelmaster.dtos.ProductExemplarDto;
import lv.ami.fuelmaster.dtos.ShelfDto;
import lv.ami.fuelmaster.dtos.WarehouseDto;
import lv.ami.fuelmaster.models.Product;
import lv.ami.fuelmaster.models.ProductExemplar;
import lv.ami.fuelmaster.models.Shelf;
import lv.ami.fuelmaster.models.Warehouse;

public interface ModelConversionUtil {
	
	
	//public Warehouse convertDtoToWarehouse(WarehouseDto warehouseDto);
	
	//public WarehouseDto convertWarehousetoDto(Warehouse warehouse);
    
	public Shelf convertDtoToShelf(ShelfDto shelfDto);
	
	public ShelfDto convertShelftoDto(Shelf shelf);
	
	public Product convertDTOToProduct(ProductDto productDto);
	
	public ProductDto convertProductToDto(Product product);
	
	
	
	public ProductExemplarDto productExemplarToDao (ProductExemplar productExemplar);
}
