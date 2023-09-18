package lv.ami.fuelmaster.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.Fuel;
import lv.ami.fuelmaster.models.Receipt;

public interface ReceiptRepository {
	public Long count();

	public void delete(Receipt entity);

	public void deleteById(Long id);

	public List<Receipt> findAll();

	public Page<Receipt> findAll(Pageable pageable);

	public Receipt findById(Long id);

	public List<Receipt> findByNumber(String number);
	
	public Receipt findByNumberAndDate(String number, LocalDateTime localDateTime);

	public Receipt save(Receipt entity);
}
