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
import javax.persistence.Table;

@Entity
@Table(name = "fuel_tanks")
public class FuelTank {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "consumption")
	private Float consumption;
	
	@Column(name = "capacity")
	private Float capacity;
	
	@ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
	
    @ManyToMany
    @JoinTable(
        name = "fuel_tank_fuel",
        joinColumns = @JoinColumn(name = "fuel_tank_id"),
        inverseJoinColumns = @JoinColumn(name = "fuel_id")
    )
    private List<Fuel> fuels = new ArrayList<>();

	public FuelTank(){
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getConsumption() {
		return consumption;
	}

	public void setConsumption(Float consumption) {
		this.consumption = consumption;
	}

	public Float getCapacity() {
		return capacity;
	}

	public void setCapacity(Float capacity) {
		this.capacity = capacity;
	}

	public List<Fuel> getFuels() {
		return fuels;
	}

	public void setFuels(List<Fuel> fuels) {
		this.fuels = fuels;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}


	

	
}
