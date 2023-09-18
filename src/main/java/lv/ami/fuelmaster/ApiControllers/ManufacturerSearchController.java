package lv.ami.fuelmaster.ApiControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lv.ami.fuelmaster.models.Manufacturer;
import lv.ami.fuelmaster.repositories.ManufacturerRepository;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerSearchController {

    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerSearchController(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Manufacturer> searchManufacturers(@RequestParam("keyword") String keyword) {
        return manufacturerRepository.search(keyword);
    }
}