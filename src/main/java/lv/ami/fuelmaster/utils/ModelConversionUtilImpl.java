package lv.ami.fuelmaster.utils;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.dtos.ProductDto;
import lv.ami.fuelmaster.dtos.ProductExemplarDto;
import lv.ami.fuelmaster.dtos.ShelfDto;
import lv.ami.fuelmaster.models.Product;
import lv.ami.fuelmaster.models.ProductExemplar;
import lv.ami.fuelmaster.models.Shelf;
import lv.ami.fuelmaster.repositories.ProductExemplarRepository;
import lv.ami.fuelmaster.repositories.ProductRepository;
import lv.ami.fuelmaster.repositories.ShelfRepository;
import lv.ami.fuelmaster.repositories.WarehouseRepository;

@Service
@Transactional
public class ModelConversionUtilImpl {

	public Shelf convertDtoToShelf(ShelfDto shelfDto) {
		Shelf sfelf = null;
		if (sfelf == null) {
			sfelf = new Shelf();
		}
		sfelf.setId(shelfDto.getId());
		sfelf.setName(shelfDto.getName());
		return sfelf;
	}

	public ShelfDto convertShelfToDto(Shelf shelf) {
		ShelfDto dto = new ShelfDto();
		if (shelf != null) {
			dto.setId(shelf.getId());
			dto.setName(shelf.getName());
		}
		return dto;
	}
	
	public Product convertDtoToProduct(ProductDto productDto) {
		Product product = new Product();
		if (productDto != null) {
			product.setId(productDto.getId());
			product.setOrderCode(productDto.getOrderCode());
			product.setShortDescription(productDto.getShortDescription());
			product.setLongDescription(productDto.getLongDescription());
		}
		return product;
	}

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

	public ProductExemplarDto productExemplarToDao(ProductExemplar productExemplar) {
		ProductExemplarDto dto = new ProductExemplarDto();
		dto.setId(productExemplar.getId());
		dto.setSerialNumber(productExemplar.getSerialNumber());
		
		
		dto.setShelf(convertShelfToDto(productExemplar.getShelf()));
		return dto;

	}
}
