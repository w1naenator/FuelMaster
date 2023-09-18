package lv.ami.fuelmaster.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "hydraulic_rotator_test")
public class HydraulicRotatorTestDTO {

	private Long id;
	private String serialNumber;
	private String rotatorModel;
	private String createdBy;
	private Long appUserId;
	private LocalDateTime dateCreated;
	private List<HydraulicRotatorMeasurementsSetDTO> measurementsSets = new ArrayList<>();

	public HydraulicRotatorTestDTO() {}
	
	public HydraulicRotatorTestDTO(HydraulicRotatorTest test) {
		this.id = test.getId();
		this.serialNumber = test.getSerialNumber();
		this.rotatorModel = test.getSerialNumber();
		this.createdBy = test.getCreatedBy().getUsername();
		this.appUserId = test.getCreatedBy().getId();
		List<HydraulicRotatorMeasurementsSet> sets = test.getMeasurementsSets();
		if (sets != null) {
			List<HydraulicRotatorMeasurementsSetDTO> setdto = new ArrayList<>();
			for (HydraulicRotatorMeasurementsSet set : sets) {
				setdto.add(new HydraulicRotatorMeasurementsSetDTO(set));
			}
			this.measurementsSets = setdto;
		}
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

	public String getRotatorModel() {
		return rotatorModel;
	}

	public void setRotatorModel(String rotatorModel) {
		this.rotatorModel = rotatorModel;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Long appUserId) {
		this.appUserId = appUserId;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<HydraulicRotatorMeasurementsSetDTO> getMeasurementsSets() {
		return measurementsSets;
	}

	public void setMeasurementsSets(List<HydraulicRotatorMeasurementsSetDTO> measurementsSets) {
		this.measurementsSets = measurementsSets;
	}

}
