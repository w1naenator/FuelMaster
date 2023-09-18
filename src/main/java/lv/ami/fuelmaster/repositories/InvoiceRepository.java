package lv.ami.fuelmaster.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.Invoice;

public interface InvoiceRepository {
	public Long count();

	public void delete(Invoice entity);

	public void deleteById(Long id);

	public List<Invoice> findAll();

	public Page<Invoice> findAll(Pageable pageable);

	public Invoice findById(Long id);
	
	public Invoice findByNumber(String number);

	public Invoice save(Invoice entity);
}
