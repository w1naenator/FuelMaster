package lv.ami.fuelmaster.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.Fuel;
import lv.ami.fuelmaster.models.FuelTank;

public interface FuelTankRepository {
	
	public Long count();

	public void delete(FuelTank entity);
	
	public FuelTank save(FuelTank entity);

	public List<FuelTank> findAll();

	public Page<FuelTank> findAll(Pageable pageable);

	public FuelTank findById(Long id);

	
}
