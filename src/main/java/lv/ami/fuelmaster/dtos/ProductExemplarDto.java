package lv.ami.fuelmaster.dtos;



public class ProductExemplarDto {

	private Long id;
	private String serialNumber;
	private Long productId;
	private String productManuafacturer;
	
	private ShelfDto shelf;

	public ProductExemplarDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public ShelfDto getShelf() {
		return shelf;
	}

	public void setShelf(ShelfDto shelf) {
		this.shelf = shelf;
	}



}
