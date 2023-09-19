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
		/*
		 * if (file.isEmpty()) { model.addAttribute("errorMessage",
		 * "Fails nav izvēlēts."); return "upload"; } else {
		 * 
		 * try { Reader reader = new InputStreamReader(file.getInputStream()); CSVReader
		 * csvReader = new CSVReaderBuilder(reader) .withCSVParser(new
		 * CSVParserBuilder().withSeparator(';').build()).build(); if (csvReader.peek()
		 * == null) { model.addAttribute("errorMessage",
		 * "Nevar iegūt datus, vai faila formāts ir pareizs?"); return "upload"; }
		 * 
		 * List<List<String>> rows = new ArrayList<>();
		 * 
		 * String[] nextRecord; int lineNumber = 0; while ((nextRecord =
		 * csvReader.readNext()) != null) { lineNumber++; if (lineNumber == 1) { String
		 * fields[] = { "Date", "Card Number", "Card text line 2", "Driver", "Vehicle",
		 * "ReceiptNo", "Station", "Country", "Product", "Volume", "Price", "Amount",
		 * "VAT", "NET", "Discount", "Amount incl.  Discount", "Odometer",
		 * "Invoice Number", "Product ID" }; int i = 0; for (String value : nextRecord)
		 * { if (!value.equals(fields[i])) { model.addAttribute("errorMessage",
		 * "Nevar iegūt datus, vai faila formāts ir pareizs?<br>Pirmai rindai jāsatur:<br>\""
		 * + String.join(";", fields) + "\""); return "upload";
		 * 
		 * } i++; } // Skip the first line continue; }
		 * 
		 * List<String> row = new ArrayList<>(); for (String value : nextRecord) {
		 * row.add(value); } rows.add(row); }
		 * 
		 * csvReader.close(); reader.close(); if (rows.size() == 19) { // Process the
		 * rows (columns and values) as needed for (List<String> row : rows) { if
		 * (rows.size() != 19) { model.addAttribute("errorMessage",
		 * "Faila formāta kļūda."); return "upload"; // Redirect to a success page }
		 * String date = row.get(0); // String cardNumber = row.get(1); // String
		 * cardTextLine2 = row.get(2); // String driver = row.get(3); // String vehicle
		 * = row.get(4); String receiptNo = row.get(5); // String station = row.get(6);
		 * // String country = row.get(7); // String product = row.get(8); String volume
		 * = row.get(9); // String price = row.get(10); // String amount = row.get(11);
		 * // String vat = row.get(12); // String net = row.get(13); // String discount
		 * = row.get(14); String amountInclDiscount = row.get(15); // String odometer =
		 * row.get(16); String invoiceNumber = row.get(17); String productID =
		 * row.get(18);
		 * 
		 * Invoice invoice = invoiceRepository.findByNumber(invoiceNumber); if (invoice
		 * == null) { invoice = new Invoice(); invoice.setNumber(invoiceNumber);
		 * invoiceRepository.save(invoice); }
		 * 
		 * DateTimeFormatter formatter =
		 * DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"); LocalDateTime dateTime =
		 * LocalDateTime.parse(date, formatter); Receipt receipt = null; try { receipt =
		 * receiptRepository.findByNumberAndDate(receiptNo, dateTime); } catch
		 * (Exception e1) { model.addAttribute("errorMessage",
		 * "receiptRepository.findByNumberAndDate: " + e1.toString()); return "upload";
		 * } if (receipt == null) { receipt = new Receipt(); } else { if
		 * (receipt.getReceiptDateTime().compareTo(dateTime) != 0) { receipt = new
		 * Receipt(); } } receipt.setInvoice(invoice); receipt.setNumber(receiptNo);
		 * receipt.setVolume(Float.parseFloat(volume));
		 * receipt.setPrice(Float.parseFloat(amountInclDiscount) /
		 * Float.parseFloat(volume));
		 * 
		 * receipt.setReceiptDateTime(dateTime);
		 * 
		 * Fuel fuel = null; if (productID.equals("00010309")) { fuel =
		 * fuelRepository.findByName("LPG"); if (fuel == null) { fuel = new Fuel();
		 * fuel.setName("LPG"); fuelRepository.save(fuel); } }
		 * 
		 * if (productID.equals("00010101")) { fuel = fuelRepository.findByName("E98");
		 * if (fuel == null) { fuel = new Fuel(); fuel.setName("E98");
		 * fuelRepository.save(fuel); } }
		 * 
		 * if (productID.equals("00010104")) { fuel = fuelRepository.findByName("E95");
		 * if (fuel == null) { fuel = new Fuel(); fuel.setName("E95");
		 * fuelRepository.save(fuel); } }
		 * 
		 * if (productID.equals("00010211")) { fuel =
		 * fuelRepository.findByName("DIESEL"); if (fuel == null) { fuel = new Fuel();
		 * fuel.setName("DIESEL"); fuelRepository.save(fuel); } }
		 * 
		 * receipt.setFuel(fuel); receiptRepository.save(receipt); } } } catch
		 * (Exception e) { // Handle the exception appropriately e.printStackTrace();
		 * model.addAttribute("errorMessage", e.toString()); return "upload"; //
		 * Redirect to a success page } //model.addAttribute("errorMessage", "ok.");
		 * //return "upload"; // Redirect to a success page return
		 * "redirect:receipt-list"; // Redirect to a success page } }
		 */
		
		receiptImportService.importCircleKSemicomCSV(file);
		if (receiptImportService.hasError()) {
			model.addAttribute("errorMessage", receiptImportService.getErrorMessage());
			return "upload";
		} else {
			return "redirect:receipt-list";
		}
	}
}
