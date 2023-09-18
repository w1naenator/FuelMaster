package lv.ami.fuelmaster.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company")
	private String company;
	
	@Column(name = "number")
	private String number;
	
	@Column(name = "date_time")
	private LocalDateTime receiptDateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public LocalDateTime getReceiptDateTime() {
		return receiptDateTime;
	}

	public void setReceiptDateTime(LocalDateTime receiptDateTime) {
		this.receiptDateTime = receiptDateTime;
	}
	
	
}
