package lv.ami.fuelmaster.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.Warehouse;

public interface WarehouseRepository {
    public Warehouse save(Warehouse warehouse);

    void delete(Warehouse warehouse);

    Optional<Warehouse> findOptionalById(Long id);
    
    Warehouse findById(Long id);

    List<Warehouse> findAll();
    
    Page<Warehouse> findAll(Pageable pageable);
    
    Long countUsers();
    
    void deleteById(Long id);
}
