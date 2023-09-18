package lv.ami.fuelmaster.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.dtos.ShelfDto;
import lv.ami.fuelmaster.models.Shelf;

@Service
@Transactional
public interface ShelfService {

	Shelf convertDtoToShelf(ShelfDto shelfDto);
	
	ShelfDto convertShelftoDto(Shelf shelf);
	
	List<Shelf> convertDtosToShelves(List<ShelfDto> hydraulicRotatorTestDTOs);
	
	List<ShelfDto> convertShelvesToDtos(List<Shelf> hydraulicRotatorTests);
    
    Shelf findShelfById(Long id);
    
    Optional<Shelf> findOptionalById(Long id);

    List<Shelf> findAll();
    
    Page<Shelf> findAll(Pageable pageable);
    
    
    void save(Shelf hydraulicRotatorTest);
    
    Shelf saveAndReturn(Shelf hydraulicRotatorTest);
    
    void delete(Shelf hydraulicRotatorTest);
    
    void deleteById(Long id);
    
    
    Long countShelves();
}
