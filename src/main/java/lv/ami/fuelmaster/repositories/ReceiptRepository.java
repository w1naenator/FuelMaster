package lv.ami.fuelmaster.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.Receipt;

public interface ReceiptRepository {
	
	public Receipt save(Receipt entity);

	public void delete(Receipt entity);

	public void deleteById(Long id);

	
	public boolean existsByDateAndNumber(String number, LocalDateTime localDateTime) throws Exception;
	
	public Receipt getByNumberAndDate(String number, LocalDateTime localDateTime)  throws Exception;
	
	public Long count();
	
	
	public Receipt findById(Long id);
	

	public List<Receipt> findAll();

	public Page<Receipt> findAll(Pageable pageable);
	
	
	public List<Receipt> findByNumber(String number);
	
	public Page<Receipt> findByNumber(String number, Pageable pageable);
	
	
	public List<Receipt> findByMonth(LocalDate localDate) throws Exception;
	
	public Page<Receipt> findByMonth(LocalDate localDate, Pageable pageable) throws Exception;
	
	
	public List<Receipt> findByNumberAndMonth(String number, LocalDate localDate) throws Exception;
	
	public Page<Receipt> findByNumberAndMonth(String number, LocalDate localDate, Pageable pageable);


	
	

	
}
