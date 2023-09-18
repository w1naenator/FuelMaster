package lv.ami.fuelmaster.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.dtos.ShelfDto;
import lv.ami.fuelmaster.models.Shelf;
import lv.ami.fuelmaster.repositories.ShelfRepository;

@Service
@Transactional
public class ShelfServiceImpl implements ShelfService {

	@Autowired
	private ShelfRepository shelfRepository;

	@Override
	public Shelf convertDtoToShelf(ShelfDto shelfDto) {
		Shelf sfelf = null;
		if (sfelf == null) {
			sfelf = new Shelf();
		}
		sfelf.setId(shelfDto.getId());
		sfelf.setName(shelfDto.getName());
		return sfelf;
	}

	@Override
	public ShelfDto convertShelftoDto(Shelf shelf) {
		ShelfDto dto = new ShelfDto();
		if (shelf != null) {
			dto.setId(shelf.getId());
			dto.setName(shelf.getName());
		}
		return dto;
	}

	@Override
	public List<Shelf> convertDtosToShelves(List<ShelfDto> shelfDtos) {
		List<Shelf> shelves = new ArrayList<>();
		if (shelfDtos != null) {
			for (ShelfDto shelfDto : shelfDtos) {
				shelves.add(convertDtoToShelf(shelfDto));
			}
		}
		return shelves;
	}

	@Override
	public List<ShelfDto> convertShelvesToDtos(List<Shelf> shelves) {
		List<ShelfDto> shelfdtos = new ArrayList<>();
		if (shelves != null) {
			for (Shelf shelf : shelves) {
				shelfdtos.add(convertShelftoDto(shelf));
			}
		}
		return shelfdtos;
	}

	@Override
	public Shelf findShelfById(Long id) {
		return shelfRepository.findById(id);
	}

	@Override
	public Optional<Shelf> findOptionalById(Long id) {
		return shelfRepository.findOptionalById(id);
	}

	@Override
	public List<Shelf> findAll() {
		return shelfRepository.findAll();
	}

	@Override
	public Page<Shelf> findAll(Pageable pageable) {
		return shelfRepository.findAll(pageable);
	}

	@Override
	public void save(Shelf hydraulicRotatorTest) {
		shelfRepository.save(hydraulicRotatorTest);
	}

	@Override
	public Shelf saveAndReturn(Shelf hydraulicRotatorTest) {
		shelfRepository.save(hydraulicRotatorTest);
		return hydraulicRotatorTest;
	}

	@Override
	public void delete(Shelf hydraulicRotatorTest) {
		shelfRepository.delete(hydraulicRotatorTest);
	}

	@Override
	public void deleteById(Long id) {
		shelfRepository.deleteById(id);
	}

	@Override
	public Long countShelves() {
		return shelfRepository.count();
	}

}
