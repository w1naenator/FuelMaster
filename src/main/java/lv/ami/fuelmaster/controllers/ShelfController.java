package lv.ami.fuelmaster.controllers;

import java.util.List;

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

import lv.ami.fuelmaster.dtos.ShelfDto;
import lv.ami.fuelmaster.models.Shelf;
import lv.ami.fuelmaster.models.Warehouse;
import lv.ami.fuelmaster.service.ShelfService;
import lv.ami.fuelmaster.service.WarehouseService;

@Controller
public class ShelfController extends AbstractBaseController {

	@Autowired
	ShelfService shelfService;
	
	@Autowired
	WarehouseService warehouseService;

	@GetMapping("/shelf-list")
	public String listTests(Model model, @RequestParam(defaultValue = "1") int page) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		int pageSize = 10;
		Page<Shelf> shelves = shelfService.findAll(PageRequest.of(page - 1, pageSize));
		Long totalShelves = shelfService.countShelves();
		Long pageCount = (long) Math.ceil((double) totalShelves / pageSize);
		model.addAttribute("shelves", shelves);
		model.addAttribute("page", page);
		model.addAttribute("pageCount", pageCount);
		return "shelfList";
	}
	
    @GetMapping("/shelf-view/{id}")
    public String getStock(@PathVariable("id") Long id, Model model) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
    	 Shelf shelf = shelfService.findShelfById(id);
    	if (shelf != null) {
    		boolean showtable = false;
    		ShelfDto shelfdto = shelfService.convertShelftoDto(shelf);
           // List<ProductDto> productsdto = productExemplarService.convertProductsToDTOs(sfelf.getProducts());  
            model.addAttribute("showtable", showtable);
            model.addAttribute("shelf", shelfdto);
            //model.addAttribute("products", productsdto);
            
            return "shelfView";
        } else {
            return "shelfList";
        }
    	

    }
    
	/*@GetMapping("/shelf-new")
	public String newWarehouse(Model model) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Shelf shelf = new Shelf();
		List<Warehouse> warehouses = warehouseService.findAll();
	    
		model.addAttribute("shelf", shelf);
		model.addAttribute("warehouses", warehouses);
		model.addAttribute("title", "Create new shelf");
		return "shelfNew";
	}*/
    
	@GetMapping({"/shelf-new"})
	public String newShelf(Model model) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Long warehouseId = null;
		Shelf shelf = new Shelf();
		List<Warehouse> warehouses = warehouseService.findAll();
	    
		model.addAttribute("shelf", shelf);
		model.addAttribute("warehouseId", warehouseId);
		model.addAttribute("warehouses", warehouses);
		model.addAttribute("title", "Create new shelf");
		return "shelfNew";
	}
	
	@GetMapping({"/shelf-new/{warehouse_id}"})
	public String newShelf(Model model, @PathVariable("warehouse_id") Long warehouseId) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Shelf shelf = new Shelf();
		List<Warehouse> warehouses = warehouseService.findAll();
	    
		model.addAttribute("shelf", shelf);
		model.addAttribute("warehouseId", warehouseId);
		model.addAttribute("warehouses", warehouses);
		model.addAttribute("title", "Create new shelf");
		return "shelfNew";
	}
	
	@PostMapping("/shelf-new")
	public String saveWarehouse1(@ModelAttribute("shelf") Shelf shelf, @ModelAttribute("warehouseId") Long warehouseId) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Warehouse warehouse = warehouseService.findById(warehouseId);
		shelf.setWarehouse(warehouse);
		shelfService.save(shelf);
		//return "redirect:/shelf-list";
		return "redirect:/warehouse-view/" + warehouseId.toString();
	}
	

	
	
	@PostMapping("/shelf-new/{warehouse_id}")
	public String saveWarehouse2(@ModelAttribute("shelf") Shelf shelf, @PathVariable("warehouse_id") Long warehouseId) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Warehouse warehouse = warehouseService.findById(warehouseId);
		shelf.setWarehouse(warehouse);
		shelfService.save(shelf);
		//return "redirect:/shelf-list";
		return "redirect:/warehouse-view/" + warehouseId.toString();
	}
	


	@GetMapping("/shelf-edit/{id}")
	public String editShelf(Model model, @PathVariable("id") Long id) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Shelf shelf = shelfService.findOptionalById(id).orElseThrow(() -> new IllegalArgumentException("Invalid test id: " + id));
		model.addAttribute("shelf", shelf);
		return "shelfEdit";
	}

	@PostMapping("/shelf-edit")
	public String updateStock(@ModelAttribute("shelf") Shelf shelf) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		shelfService.save(shelf);
		return "redirect:/shelf-list";
	}

	@GetMapping("/shelf-delete/{id}")
	public String deleteTest(@PathVariable("id") Long id) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Shelf shelf = shelfService.findOptionalById(id).orElseThrow(() -> new IllegalArgumentException("Invalid test id: " + id));
		shelfService.delete(shelf);
		return "redirect:/shelf-list";
	}
}
