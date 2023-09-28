package lv.ami.fuelmaster.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.Receipt;
import lv.ami.fuelmaster.models.Trip;

public interface TripRepository {
	public Long count();

	public void delete(Trip entity);

	public void deleteById(Long id);

	public List<Trip> findAll();

	public Page<Trip> findAll(Pageable pageable);

	public Trip findById(Long id);

	public List<Trip> findByNumber(String number);
	
	public Trip findByNumberAndDate(String number, LocalDateTime localDateTime) throws Exception;
	
	public boolean existsByDateAndNumber(String number, LocalDateTime localDateTime) throws Exception;

	public Trip save(Trip entity);
}