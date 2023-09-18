package lv.ami.fuelmaster.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.ami.fuelmaster.models.AppUser;
import lv.ami.fuelmaster.models.Product;
import lv.ami.fuelmaster.models.Receipt;
import lv.ami.fuelmaster.repositories.ReceiptRepository;
import lv.ami.fuelmaster.service.AppUserService;

@Controller
public class ReceiptListController extends AbstractBaseController {
	
	@Autowired
	private ReceiptRepository receiptRepository;

	@GetMapping("/receipt-list")
	public String listUsers(@RequestParam(defaultValue = "1") int page, Model model) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		int pageSize = 10;
		Page<Receipt> receipts = receiptRepository.findAll(PageRequest.of(page - 1, pageSize));
		Long totalShelves = receiptRepository.count();
		Long pageCount = (long) Math.ceil((double) totalShelves / pageSize);
		model.addAttribute("receipts", receipts);
		model.addAttribute("page", page);
		model.addAttribute("pageCount", pageCount);
		return "receiptList";
	}
}