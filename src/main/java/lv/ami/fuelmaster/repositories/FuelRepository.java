package lv.ami.fuelmaster.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.Fuel;

public interface FuelRepository {
	public Long count();

	public void delete(Fuel entity);

	public void deleteById(Long id);

	public List<Fuel> findAll();

	public Page<Fuel> findAll(Pageable pageable);

	public Fuel findById(Long id);

	public Fuel findByName(String name);

	public Fuel save(Fuel entity);
}
