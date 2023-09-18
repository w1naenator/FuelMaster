package lv.ami.fuelmaster.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

public class HydraulicRotatorMeasurementsSetDTO {

    private Long id;
    private Integer recordNumber;
    private Short rotationDirection;
    private Float angle;
    private Float forwardPressure;
    private Float reversePressure;
    private Float drainagePressure;
    private Float brakeAPressure;
    private Float brakeBPressure;
    private Float cylinderAPressure;
    private Float cylinderBPressure;
    private Float scales;
    private Float forwardFlow;
    private Float reverseFlow;
    private Float drainageFlow;
    private Float tankTemperature;
    private Float radiatorInTemperature;
    private Float radiatorOutTemperature;
    private Float rotationATemperature;
    private Float rotationBTemperature;
    
    HydraulicRotatorMeasurementsSetDTO(HydraulicRotatorMeasurementsSet measurement){
        this.id = measurement.getId();
	    this.recordNumber = measurement.getRecordNumber();
	    this.rotationDirection = measurement.getRotationDirection();
	    this.angle = measurement.getAngle();
	    this.forwardPressure = measurement.getForwardPressure();
	    this.reversePressure = measurement.getReversePressure();
	    this.drainagePressure = measurement.getDrainagePressure();
	    this.brakeAPressure = measurement.getBrakeAPressure();
	    this.brakeBPressure = measurement.getBrakeBPressure();
	    this.cylinderAPressure = measurement.getCylinderAPressure();
	    this.cylinderBPressure = measurement.getCylinderBPressure();
	    this.scales = measurement.getScales();
	    this.forwardFlow = measurement.getForwardFlow();
	    this.reverseFlow = measurement.getReverseFlow();
	    this.drainageFlow = measurement.getDrainageFlow();
	    this.tankTemperature = measurement.getTankTemperature();
	    this.radiatorInTemperature = measurement.getRadiatorInTemperature();
	    this.radiatorOutTemperature = measurement.getRadiatorOutTemperature();
	    this.rotationATemperature = measurement.getRotationATemperature();
	    this.rotationBTemperature = measurement.getRotationBTemperature();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getRotationDirection() {
		return rotationDirection;
	}

	public void setRotationDirection(Short rotationDirection) {
		this.rotationDirection = rotationDirection;
	}

	public Float getAngle() {
		return angle;
	}

	public void setAngle(Float angle) {
		this.angle = angle;
	}

	public Float getForwardPressure() {
		return forwardPressure;
	}

	public void setForwardPressure(Float forwardPressure) {
		this.forwardPressure = forwardPressure;
	}

	public Float getReversePressure() {
		return reversePressure;
	}

	public void setReversePressure(Float reversePressure) {
		this.reversePressure = reversePressure;
	}

	public Float getDrainagePressure() {
		return drainagePressure;
	}

	public void setDrainagePressure(Float drainagePressure) {
		this.drainagePressure = drainagePressure;
	}

	public Float getBrakeAPressure() {
		return brakeAPressure;
	}

	public void setBrakeAPressure(Float brakeAPressure) {
		this.brakeAPressure = brakeAPressure;
	}

	public Float getBrakeBPressure() {
		return brakeBPressure;
	}

	public void setBrakeBPressure(Float brakeBPressure) {
		this.brakeBPressure = brakeBPressure;
	}

	public Float getCylinderAPressure() {
		return cylinderAPressure;
	}

	public void setCylinderAPressure(Float cylinderAPressure) {
		this.cylinderAPressure = cylinderAPressure;
	}

	public Float getCylinderBPressure() {
		return cylinderBPressure;
	}

	public void setCylinderBPressure(Float cylinderBPressure) {
		this.cylinderBPressure = cylinderBPressure;
	}

	public Float getScales() {
		return scales;
	}

	public void setScales(Float scales) {
		this.scales = scales;
	}

	public Float getForwardFlow() {
		return forwardFlow;
	}

	public void setForwardFlow(Float forwardFlow) {
		this.forwardFlow = forwardFlow;
	}

	public Float getReverseFlow() {
		return reverseFlow;
	}

	public void setReverseFlow(Float reverseFlow) {
		this.reverseFlow = reverseFlow;
	}

	public Float getDrainageFlow() {
		return drainageFlow;
	}

	public void setDrainageFlow(Float drainageFlow) {
		this.drainageFlow = drainageFlow;
	}

	public Float getTankTemperature() {
		return tankTemperature;
	}

	public void setTankTemperature(Float tankTemperature) {
		this.tankTemperature = tankTemperature;
	}

	public Float getRadiatorInTemperature() {
		return radiatorInTemperature;
	}

	public void setRadiatorInTemperature(Float radiatorInTemperature) {
		this.radiatorInTemperature = radiatorInTemperature;
	}

	public Float getRadiatorOutTemperature() {
		return radiatorOutTemperature;
	}

	public void setRadiatorOutTemperature(Float radiatorOutTemperature) {
		this.radiatorOutTemperature = radiatorOutTemperature;
	}

	public Float getRotationATemperature() {
		return rotationATemperature;
	}

	public void setRotationATemperature(Float rotationATemperature) {
		this.rotationATemperature = rotationATemperature;
	}

	public Float getRotationBTemperature() {
		return rotationBTemperature;
	}

	public void setRotationBTemperature(Float rotationBTemperature) {
		this.rotationBTemperature = rotationBTemperature;
	}

	public Integer getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber;
	}

	
	

}
