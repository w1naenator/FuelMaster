package lv.ami.fuelmaster.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "fuels")
public class Fuel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToMany(mappedBy = "fuels")
    private List<FuelTank> fuelTanks = new ArrayList<>();


	public Fuel(){
		
	}
	
	public Fuel(String name){
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

	public List<FuelTank> getFuelTanks() {
		return fuelTanks;
	}

	public void setFuelTanks(List<FuelTank> fuelTanks) {
		this.fuelTanks = fuelTanks;
	}




	

	
}
