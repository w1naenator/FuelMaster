package lv.ami.fuelmaster.ApiControllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.ami.fuelmaster.models.Warehouse;
import lv.ami.fuelmaster.dtos.WarehouseDto;
import lv.ami.fuelmaster.repositories.WarehouseRepository;

@RestController
@RequestMapping("/api/tests")
public class HydraulicRotatorTestApiController {

   @Autowired
    private HydraulicRotatorTestRepository hydraulicRotatorTestRepository;
   
   @GetMapping("/")
   public ResponseEntity<List<HydraulicRotatorTestDTO>> listTests() {
       List<HydraulicRotatorTest> hydraulicRotatorTests = hydraulicRotatorTestRepository.findAll();
       List<HydraulicRotatorTestDTO> dtos = new ArrayList<>();
       for (HydraulicRotatorTest test : hydraulicRotatorTests) {
           HydraulicRotatorTestDTO dto = new HydraulicRotatorTestDTO(test);
           dtos.add(dto);
       }
       return ResponseEntity.ok().body(dtos);
   }

   @GetMapping("/{id}")
   public ResponseEntity<HydraulicRotatorTestDTO> getTestById(@PathVariable(value = "id") Long id) {
       HydraulicRotatorTest hydraulicRotatorTest = hydraulicRotatorTestRepository.findById(id);
       if (hydraulicRotatorTest != null) {
           HydraulicRotatorTestDTO dto = new HydraulicRotatorTestDTO(hydraulicRotatorTest);
           return ResponseEntity.ok().body(dto);
       } else {
           //return ResponseEntity.notFound().build();
    	   return ResponseEntity.ok().body(new HydraulicRotatorTestDTO());
       }
   }

    @PostMapping
    public HydraulicRotatorTest createTest(@RequestBody HydraulicRotatorTest hydraulicRotatorTest) {
        hydraulicRotatorTest.setDateCreated(LocalDateTime.now());
        return hydraulicRotatorTestRepository.save(hydraulicRotatorTest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HydraulicRotatorTest> updateTest(@PathVariable(value = "id") Long id,
                                                            @RequestBody HydraulicRotatorTest hydraulicRotatorTest) {
        Optional<HydraulicRotatorTest> existingTest = hydraulicRotatorTestRepository.findOptionalById(id);
        if (existingTest.isPresent()) {
            HydraulicRotatorTest updatedTest = existingTest.get();
            updatedTest.setSerialNumber(hydraulicRotatorTest.getSerialNumber());
            updatedTest.setRotatorModel(hydraulicRotatorTest.getRotatorModel());
            updatedTest.setCreatedBy(hydraulicRotatorTest.getCreatedBy());
            updatedTest.setDateCreated(LocalDateTime.now());
            updatedTest.setMeasurementsSets(hydraulicRotatorTest.getMeasurementsSets());
            return ResponseEntity.ok().body(hydraulicRotatorTestRepository.save(updatedTest));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable(value = "id") Long id) {
        Optional<HydraulicRotatorTest> hydraulicRotatorTest = hydraulicRotatorTestRepository.findOptionalById(id);
        if (hydraulicRotatorTest.isPresent()) {
            hydraulicRotatorTestRepository.delete(hydraulicRotatorTest.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<HydraulicRotatorTest> getAllTests() {
        return hydraulicRotatorTestRepository.findAll();
    }
}
