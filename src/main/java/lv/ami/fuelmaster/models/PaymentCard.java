package lv.ami.fuelmaster.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payment_card")
public class PaymentCard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
    @JoinColumn(name = "default_vehicle_id")
	private Vehicle defaultVehicle;

	public PaymentCard(){}
	
	public PaymentCard(String name){
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

	public Vehicle getDefaultVehicle() {
		return defaultVehicle;
	}

	public void setDefaultVehicle(Vehicle defaultVehicle) {
		this.defaultVehicle = defaultVehicle;
	}

	

}
