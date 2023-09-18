package lv.ami.fuelmaster.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "receipts")
public class Receipt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number")
	private String number;
	
	@Column(name = "volume")
	private Float volume;
	
	@Column(name = "price")
	private Float price;
	
	@Column(name = "date_time")
	private LocalDateTime receiptDateTime;

	@ManyToOne
    @JoinColumn(name = "invoice_id")
	private Invoice invoice;
	
	@ManyToOne
    @JoinColumn(name = "fuel_id")
	private Fuel fuel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public LocalDateTime getReceiptDateTime() {
		return receiptDateTime;
	}

	public void setReceiptDateTime(LocalDateTime receiptDateTime) {
		this.receiptDateTime = receiptDateTime;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Fuel getFuel() {
		return fuel;
	}

	public void setFuel(Fuel fuel) {
		this.fuel = fuel;
	}
	
	
}
