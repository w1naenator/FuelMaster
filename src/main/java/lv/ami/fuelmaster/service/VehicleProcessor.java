package lv.ami.fuelmaster.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.models.Vehicle;

@Service
@Transactional

public interface VehicleProcessor {
	
List<Vehicle> getVehicleList();

}
