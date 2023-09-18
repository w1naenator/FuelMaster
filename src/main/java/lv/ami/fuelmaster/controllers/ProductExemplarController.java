package lv.ami.fuelmaster.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.ami.fuelmaster.models.Product;
import lv.ami.fuelmaster.models.ProductExemplar;
import lv.ami.fuelmaster.service.ProductExemplarService;
import lv.ami.fuelmaster.service.ProductService;

@Controller
public class ProductExemplarController extends AbstractBaseController {

	@Autowired
	ProductExemplarService productExemplarService;

	@GetMapping("/productexemplar-list")
	public String list(Model model, @RequestParam(defaultValue = "1") int page) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		int pageSize = 10;
		Page<ProductExemplar> productexemplars = productExemplarService.findAll(PageRequest.of(page - 1, pageSize));
		Long totalShelves = productExemplarService.count();
		Long pageCount = (long) Math.ceil((double) totalShelves / pageSize);
		model.addAttribute("productexemplars", productexemplars);
		model.addAttribute("page", page);
		model.addAttribute("pageCount", pageCount);
		return "productExemplarList";
	}
	
	@PostMapping("/productexemplar-list")
	public String search(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam("keyword") String keyword) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		int pageSize = 10;
		Page<ProductExemplar> productexemplars = productExemplarService.search(keyword, PageRequest.of(page - 1, pageSize));
		Long total = productExemplarService.count();
		Long pageCount = (long) Math.ceil((double) total / pageSize);
		model.addAttribute("productexemplars", productexemplars);
		model.addAttribute("page", page);
		model.addAttribute("pageCount", pageCount);
		return "productExemplarList";
	}

	@GetMapping("/productexemplar-new")
	public String create(Model model) {

		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}

		model.addAttribute("productexemplar", new Product());
		model.addAttribute("title", "Create new product");
		return "productExemplarNew";

	}

	@PostMapping("/productexemplar-new")
	public String save(@ModelAttribute("productexemplar") @Validated ProductExemplar product, BindingResult bindingResult) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		
		/*if (!productExemplarService.notUsed(product.getManufacturer(), product.getOrderCode())) {
			bindingResult.reject("Dublicates not allowed", "Product exists already");
		} 
		if (product.getManufacturer() == null) {
			bindingResult.rejectValue("manufacturer", "Needed fields", "Fill manufacturer");
		} else {
			if (product.getManufacturer() == "") {
				bindingResult.rejectValue("manufacturer", "Needed fields", "Fill manufacturer");
			}
		}
		if (product.getOrderCode() == null) {
			bindingResult.rejectValue("orderCode", "Needed fields", "Fill order code");
		} else {
			if (product.getOrderCode() == "") {
				bindingResult.rejectValue("orderCode", "Needed fields", "Fill order code");
			}
		}*/
		
		if (bindingResult.hasErrors()) {
            return "productExemplarNew";
        }
		
		productExemplarService.save(product);
		return "redirect:/productexemplar-list/";
	}

	@GetMapping("/productexemplar-edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		ProductExemplar productexemplar = productExemplarService.findById(id);
		model.addAttribute("title", "Update product");
		model.addAttribute("productexemplar", productexemplar);
		model.addAttribute("edit", true);
		return "productExemplarNew";
	}

	@PostMapping({"/productexemplar-edit/{id}"})
	public String update(@ModelAttribute("productexemplar") @Validated ProductExemplar product, @PathVariable("id") Long id, BindingResult bindingResult) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		/*if (product.getManufacturer() == null) {
			bindingResult.rejectValue("manufacturer", "Needed fields", "Fill manufacturer");
		} else {
			if (product.getManufacturer() == "") {
				bindingResult.rejectValue("manufacturer", "Needed fields", "Fill manufacturer");
			}
		}
		if (product.getOrderCode() == null) {
			bindingResult.rejectValue("orderCode", "Needed fields", "Fill order code");
		} else {
			if (product.getOrderCode() == "") {
				bindingResult.rejectValue("orderCode", "Needed fields", "Fill order code");
			}
		}*/
		
		if (bindingResult.hasErrors()) {
            return "productexemplarNew";
        }
		product.setId(id);
		productExemplarService.save(product);
		return "redirect:/productexemplar-list";
	}

	@GetMapping("/productexemplar-delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		ProductExemplar productexemplar = productExemplarService.findById(id);
		productExemplarService.delete(productexemplar);
		return "redirect:/productexemplar-list";
	}
}
