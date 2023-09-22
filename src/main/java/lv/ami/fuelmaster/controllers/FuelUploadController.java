package lv.ami.fuelmaster.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lv.ami.fuelmaster.repositories.FuelRepository;
import lv.ami.fuelmaster.repositories.InvoiceRepository;
import lv.ami.fuelmaster.repositories.ReceiptRepository;
import lv.ami.fuelmaster.service.ReceiptImportService;
import lv.ami.fuelmaster.service.ReceiptImportServiceImpl;

@Controller
public class FuelUploadController {
	
	@Autowired
	ReceiptImportService receiptImportService;

	/*@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private FuelRepository fuelRepository;

	@Autowired
	private ReceiptRepository receiptRepository;*/

	@GetMapping("/upload-circlek-csv-semi")
	public String showUploadPage(/* Model model */) {
		return "upload"; // Assuming "upload.html" is located in the templates directory
	}

	@PostMapping("/upload-circlek-csv-semi")
	public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {

		receiptImportService.importCircleKSemicomCSV(file);
		if (receiptImportService.hasError()) {
			model.addAttribute("errorMessage", receiptImportService.getErrorMessage());
			return "upload";
		} else {
			return "redirect:receipt-list";
		}
	}
}
