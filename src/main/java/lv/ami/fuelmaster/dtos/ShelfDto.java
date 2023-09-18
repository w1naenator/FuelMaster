package lv.ami.fuelmaster.dtos;

import java.util.List;

public class ShelfDto {
	private Long id;
	private String name;
	private List<ProductDto> products;

	public List<ProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}

	public ShelfDto() {

	}

	public ShelfDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public ShelfDto(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
