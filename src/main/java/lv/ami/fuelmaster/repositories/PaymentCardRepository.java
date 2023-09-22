package lv.ami.fuelmaster.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.Fuel;
import lv.ami.fuelmaster.models.PaymentCard;
import lv.ami.fuelmaster.models.Vehicle;

public interface PaymentCardRepository {
	public Long count();

	public void delete(PaymentCard entity);

	public void deleteById(Long id);

	public List<PaymentCard> findAll();

	public Page<PaymentCard> findAll(Pageable pageable);

	public PaymentCard findById(Long id);

	public PaymentCard findByName(String name);

	public PaymentCard save(PaymentCard entity);
}
