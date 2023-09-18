package lv.ami.fuelmaster.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.ami.fuelmaster.dtos.ProductDto;
import lv.ami.fuelmaster.dtos.ShelfDto;
import lv.ami.fuelmaster.dtos.WarehouseDto;
import lv.ami.fuelmaster.models.Product;
import lv.ami.fuelmaster.models.ProductExemplar;
import lv.ami.fuelmaster.models.Shelf;
import lv.ami.fuelmaster.models.Warehouse;
import lv.ami.fuelmaster.service.ShelfService;
import lv.ami.fuelmaster.service.WarehouseService;

@Controller
public class WarehouseController extends AbstractBaseController {

	@Autowired
	WarehouseService warehouseService;
	
	@Autowired
	ShelfService shelfService;
	


	@GetMapping("/warehouse-list")
	public String list(Model model, @RequestParam(defaultValue = "1") int page) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		int pageSize = 10;
		Page<Warehouse> warehouses = warehouseService.findAll(PageRequest.of(page - 1, pageSize));
		Long totalTests = warehouseService.countUsers();
		Long pageCount = (long) Math.ceil((double) totalTests / pageSize);
		model.addAttribute("warehouses", warehouses);
		model.addAttribute("page", page);
		model.addAttribute("pageCount", pageCount);
		return "warehouseList";
	}
	
    @GetMapping("/warehouse-view/{id}")
    public String view(Model model, @PathVariable("id") Long id) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		String text = "-";
    	 Warehouse warehouse = warehouseService.findById(id);
    	 
    	if (warehouse != null) {

            model.addAttribute("warehouse", warehouse);
            model.addAttribute("text", text);
            
            return "warehouseView";
        } else {
            return "warehouseList";
        }
    	

    }
	
	@GetMapping("/warehouse-new")
	public String create(Model model) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Warehouse warehouse = new Warehouse();
		model.addAttribute("title", "Create new warehouse");
		model.addAttribute("warehouse", warehouse);
		
		return "warehouseNew";
	}
	
	@PostMapping("/warehouse-new")
	public String save(@ModelAttribute("warehouse") Warehouse warehouse) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		
		warehouseService.save(warehouse);
		return "redirect:/warehouse-list";
	}

	@GetMapping("/warehouse-edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Warehouse warehouse = warehouseService.findById(id);
		model.addAttribute("title", "Edit warehouse");
		model.addAttribute("edit", true);
		model.addAttribute("warehouse", warehouse);
		return "warehouseNew";
	}

	@PostMapping("/warehouse-edit/{id}")
	public String update(@ModelAttribute("warehouse") Warehouse warehouse, @PathVariable("id") Long id) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		warehouse.setId(id);
		warehouseService.save(warehouse);
		return "redirect:/warehouse-list";
	}

	@GetMapping("/warehouse-delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Warehouse warehouse = warehouseService.findById(id);
		warehouseService.delete(warehouse);
		return "redirect:/warehouse-list";
	}
}
