package lv.ami.fuelmaster.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.Shelf;

public interface ShelfRepository {
	public Long count();

	public void delete(Shelf shelf);

	public void deleteById(Long id);

	public List<Shelf> findAll();

	public Page<Shelf> findAll(Pageable pageable);

	public Shelf findById(Long id);

	public Optional<Shelf> findOptionalById(Long id);

	public Shelf save(Shelf shelf);
}
