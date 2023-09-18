package lv.ami.fuelmaster.controllers;

import java.util.List;

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

import lv.ami.fuelmaster.models.Manufacturer;
import lv.ami.fuelmaster.models.Product;
import lv.ami.fuelmaster.repositories.ManufacturerRepository;
import lv.ami.fuelmaster.service.ProductService;

@Controller
public class ProductController extends AbstractBaseController {

	@Autowired
	ProductService productService;
	
	@Autowired
	ManufacturerRepository manufacturerRepository;

	@GetMapping("/product-list")
	public String list(Model model, @RequestParam(defaultValue = "1") int page) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		int pageSize = 10;
		Page<Product> products = productService.findAll(PageRequest.of(page - 1, pageSize));
		Long totalShelves = productService.countProducts();
		Long pageCount = (long) Math.ceil((double) totalShelves / pageSize);
		model.addAttribute("products", products);
		model.addAttribute("page", page);
		model.addAttribute("pageCount", pageCount);
		return "productList";
	}
	
	@PostMapping("/product-list")
	public String search(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam("keyword") String keyword) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		int pageSize = 10;
		Page<Product> products = productService.search(keyword, PageRequest.of(page - 1, pageSize));
		Long total = productService.countProducts();
		Long pageCount = (long) Math.ceil((double) total / pageSize);
		model.addAttribute("products", products);
		model.addAttribute("page", page);
		model.addAttribute("pageCount", pageCount);
		return "productList";
	}

	@GetMapping("/product-new")
	public String create(Model model) {

		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Product product = new Product();
		product.setManufacturer(null);
		model.addAttribute("product", product);
		model.addAttribute("manufacturers", manufacturerRepository.findAll());
		model.addAttribute("title", "Create new product");
		return "productNew";

	}

	@PostMapping("/product-new")
	public String save(@ModelAttribute("product") @Validated Product product,  BindingResult bindingResult) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		
		if (!productService.notUsed(product.getManufacturer().getName(), product.getOrderCode())) {
			bindingResult.reject("Dublicates not allowed", "Product exists already");
		} 
		
		//bindingResult.rejectValue("manufacturer", "Needed fields", "Fill manufacturer");
		if (product.getManufacturer() == null) {
			bindingResult.rejectValue("manufacturer", "Needed fields", "Fill manufacturer");
		} else {
			if (product.getManufacturer().getName() == "") {
				bindingResult.rejectValue("manufacturer", "Needed fields", "Fill manufacturer");
			}
		}
		if (product.getOrderCode() == null) {
			bindingResult.rejectValue("orderCode", "Needed fields", "Fill order code");
		} else {
			if (product.getOrderCode() == "") {
				bindingResult.rejectValue("orderCode", "Needed fields", "Fill order code");
			}
		}
		
		if (bindingResult.hasErrors()) {
            return "productNew";
        }
		
		productService.save(product);
		return "redirect:/product-list/";
	}

	@GetMapping("/product-edit-{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Product product = productService.findProductById(id);
		model.addAttribute("title", "Update product");
		model.addAttribute("product", product);
		model.addAttribute("manufacturers", manufacturerRepository.findAll());
		model.addAttribute("edit", true);
		return "productNew";
	}

	@PostMapping({"/product-edit-{id}"})
	public String update(@ModelAttribute("product") @Validated Product product, @PathVariable("id") Long id, BindingResult bindingResult) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		if (product.getManufacturer() == null) {
			bindingResult.rejectValue("manufacturer", "Needed fields", "Fill manufacturer");
		} else {
			if (product.getManufacturer().getName() == "") {
				bindingResult.rejectValue("manufacturer", "Needed fields", "Fill manufacturer");
			}
		}
		if (product.getOrderCode() == null) {
			bindingResult.rejectValue("orderCode", "Needed fields", "Fill order code");
		} else {
			if (product.getOrderCode() == "") {
				bindingResult.rejectValue("orderCode", "Needed fields", "Fill order code");
			}
		}
		
		if (bindingResult.hasErrors()) {
            return "productNew";
        }
		product.setId(id);
		productService.save(product);
		return "redirect:/product-list";
	}

	@GetMapping("/product-delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		Product product = productService.findProductById(id);
		productService.delete(product);
		return "redirect:/product-list";
	}
}
