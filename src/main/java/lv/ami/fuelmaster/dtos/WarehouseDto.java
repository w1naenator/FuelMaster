package lv.ami.fuelmaster.dtos;

import java.util.List;

public class WarehouseDto {

	private Long id;
	private String name;
	private List<ShelfDto> shelves;

	public List<ShelfDto> getShelves() {
		return shelves;
	}

	public void setShelves(List<ShelfDto> shelves) {
		this.shelves = shelves;
	}

	public WarehouseDto() {
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
