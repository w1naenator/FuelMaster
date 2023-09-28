package lv.ami.fuelmaster.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lv.ami.fuelmaster.models.Receipt;
import lv.ami.fuelmaster.models.Vehicle;
import lv.ami.fuelmaster.repositories.ReceiptRepository;
import lv.ami.fuelmaster.repositories.VehicleRepository;


@Controller
public class ReceiptListController extends AbstractBaseController {
	
	@Autowired
	private ReceiptRepository receiptRepository;

	@Autowired
    private VehicleRepository vehicleRepository;
	
	/** LIST */
	@GetMapping("/receipt-list")
	public String listReceipts(@RequestParam(defaultValue = "1") int page, Model model) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		int pageSize = 10;
		Page<Receipt> receipts = receiptRepository.findAll(PageRequest.of(page - 1, pageSize));
		Long totalReceipts = receiptRepository.count();
		Long pageCount = (long) Math.ceil((double) totalReceipts / pageSize);
		model.addAttribute("receipts", receipts);
		model.addAttribute("page", page);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("keyword", "");
		//model.addAttribute("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		model.addAttribute("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")));
		return "receiptList";
	}
	
    @PostMapping("/receipt-list")
    public String findReceipts(Model model, @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "date", required = false) String dates) {
    	//LocalDate date = LocalDate.parse(dates);
    	LocalDate date = LocalDate.parse(dates + "-01");
    	
    	int pageSize = 10;
		Page<Receipt> receipts = receiptRepository.findByNumberAndMonth(keyword, date, PageRequest.of(page - 1, pageSize));
		Long totalReceipts = receipts.getTotalElements();
		Long pageCount = (long) Math.ceil((double) totalReceipts / pageSize);
    	
		model.addAttribute("receipts", receipts);
		model.addAttribute("page", page);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("keyword", keyword);
		//model.addAttribute("date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		model.addAttribute("date", date.format(DateTimeFormatter.ofPattern("yyyy-MM")));

        return "receiptList";
    }
	
	
	
	/** EDIT */
	// Display the edit form for a specific receipt
    @GetMapping("/receipt-edit-{id}")
    public String editReceiptForm(@PathVariable("id") Long id, Model model) {
        Receipt receipt = receiptRepository.findById(id);
        if (receipt == null) {
            // Handle not found scenario
            return "redirect:/"; // Redirect to the home page or an error page
        }
        
        List<Vehicle> vehicles = vehicleRepository.findAll();
        if (vehicles == null) {
            vehicles = new ArrayList<Vehicle>(); // lai nekrešo thymeleaf
        }
        model.addAttribute("receipt", receipt);
        model.addAttribute("vehicles", vehicles);
        return "receiptEdit";
    }

    // Handle form submission to update the receipt
    @PostMapping("/receipt-edit-{id}")
    public String editReceiptSubmit(@PathVariable("id") Long id, 
                                    @ModelAttribute("receipt") Receipt updatedReceipt) {
        // Retrieve the existing receipt from the database
        Receipt existingReceipt = receiptRepository.findById(id);

        if (existingReceipt == null) {
            // Handle not found scenario
            return "redirect:/"; // Redirect to the home page or an error page
        }

        // Update the fields of the existing receipt with the values from the form
        existingReceipt.setNumber(updatedReceipt.getNumber());
        existingReceipt.setVolume(updatedReceipt.getVolume());
        existingReceipt.setPrice(updatedReceipt.getPrice());
        existingReceipt.setReceiptDateTime(updatedReceipt.getReceiptDateTime());
        // Update other fields (Invoice, Fuel, and Vehicle) as needed

        // Save the updated receipt to the database
        receiptRepository.save(existingReceipt);

        // Redirect to a success page or a list of receipts
        return "redirect:/"; // Redirect to the home page or a receipts list page
    }
    
    
    /**DELETE*/
   @GetMapping("/receipt-delete-{id}")
    public String deleteItem(@PathVariable("id") Long itemId, 
    		@RequestParam(name = "page", defaultValue = "1") int page, 
    		RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("page", page);
        
        receiptRepository.deleteById(itemId);

        return "redirect:receipt-list";
    }
}