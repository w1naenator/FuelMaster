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
@Table(name = "trips")
public class Trip {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "date_time")
	private LocalDateTime tripDateTime;
	
	@Column (name = "distance")
	private Long distance;
	
	@ManyToOne
    @JoinColumn(name = "vehicle_id")
	private Vehicle tripByVehicle;

	
	
	
	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTripDateTime() {
		return tripDateTime;
	}

	public void setTripDateTime(LocalDateTime tripDateTime) {
		this.tripDateTime = tripDateTime;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public Vehicle getTripByVehicle() {
		return tripByVehicle;
	}

	public void setTripByVehicle(Vehicle tripByVehicle) {
		this.tripByVehicle = tripByVehicle;
	}
	
	
}