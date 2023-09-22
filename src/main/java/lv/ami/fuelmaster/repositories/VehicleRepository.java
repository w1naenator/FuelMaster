package lv.ami.fuelmaster.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.Fuel;
import lv.ami.fuelmaster.models.Vehicle;

public interface VehicleRepository {
	public Long count();

	public void delete(Vehicle entity);

	public void deleteById(Long id);

	public List<Vehicle> findAll();

	public Page<Vehicle> findAll(Pageable pageable);

	public Vehicle findById(Long id);

	public Vehicle findByNumber(String number);

	public Vehicle save(Vehicle entity);
}
