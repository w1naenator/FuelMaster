package lv.ami.fuelmaster.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number")
	private String number;
	
	@Column(name = "consumption")
	private Float consumption;
	
	@Column(name = "tank_capacity")
	private Float tankCapacity;

	@ManyToMany
    @JoinTable(name = "vehicle_fuel", joinColumns = @JoinColumn(name = "vehicle_id"), inverseJoinColumns = @JoinColumn(name = "fuel_id"))
	private List<Fuel> fuelTypes = new ArrayList<>();
	
	@OneToOne
	@JoinColumn(name = "default_vehicle_id", unique = true)
	private Vehicle defaultVehicle;
	
	
	

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

	public List<Fuel> getFuelTypes() {
		return fuelTypes;
	}

	public void setFuelTypes(List<Fuel> fuelTypes) {
		this.fuelTypes = fuelTypes;
	}

	public Vehicle getDefaultVehicle() {
		return defaultVehicle;
	}

	public void setDefaultVehicle(Vehicle defaultVehicle) {
		this.defaultVehicle = defaultVehicle;
	}

	public Float getConsumption() {
		return consumption;
	}

	public void setConsumption(Float consumption) {
		this.consumption = consumption;
	}

	public Float getTankCapacity() {
		return tankCapacity;
	}

	public void setTankCapacity(Float tankCapacity) {
		this.tankCapacity = tankCapacity;
	}


	

}
