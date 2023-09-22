package lv.ami.fuelmaster.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number")
	private String number;

	@Column(name = "default_vehicle")
	private String defaultVehicle;

	@ManyToMany
    @JoinTable(name = "vehicle_fuel",
            joinColumns = @JoinColumn(name = "vehicle_id"),
            inverseJoinColumns = @JoinColumn(name = "fuel_id"))
	private List<Fuel> fuelTypes = new ArrayList<Fuel>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDefaultVehicle() {
		return defaultVehicle;
	}

	public void setDefaultVehicle(String defaultVehicle) {
		this.defaultVehicle = defaultVehicle;
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

}
