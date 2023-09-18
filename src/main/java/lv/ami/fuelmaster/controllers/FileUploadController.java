package lv.ami.fuelmaster.controllers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import lv.ami.fuelmaster.models.Fuel;
import lv.ami.fuelmaster.models.Invoice;
import lv.ami.fuelmaster.models.Receipt;
import lv.ami.fuelmaster.repositories.FuelRepository;
import lv.ami.fuelmaster.repositories.InvoiceRepository;
import lv.ami.fuelmaster.repositories.ReceiptRepository;

@Controller
public class FileUploadController {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private FuelRepository fuelRepository;

	@Autowired
	private ReceiptRepository receiptRepository;

	@GetMapping("/upload")
	public String showUploadPage() {
		return "upload"; // Assuming "upload.html" is located in the templates directory
	}

	@PostMapping("/upload")
	public String uploadCSVFile(@RequestParam("file") MultipartFile file) {
		try {
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader)
					.withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();

			List<List<String>> rows = new ArrayList<>();

			String[] nextRecord;
			int lineNumber = 0;
			while ((nextRecord = csvReader.readNext()) != null) {
				lineNumber++;
				if (lineNumber == 1) {
					// Skip the first line
					continue;
				}

				List<String> row = new ArrayList<>();
				for (String value : nextRecord) {
					row.add(value);
				}
				rows.add(row);
			}

			csvReader.close();
			reader.close();

			// Process the rows (columns and values) as needed
			for (List<String> row : rows) {
				String date = row.get(0);
				String cardNumber = row.get(1);
				String cardTextLine2 = row.get(2);
				String driver = row.get(3);
				String vehicle = row.get(4);
				String receiptNo = row.get(5);
				String station = row.get(6);
				String country = row.get(7);
				String product = row.get(8);
				String volume = row.get(9);
				String price = row.get(10);
				String amount = row.get(11);
				String vat = row.get(12);
				String net = row.get(13);
				String discount = row.get(14);
				String amountInclDiscount = row.get(15);
				String odometer = row.get(16);
				String invoiceNumber = row.get(17);
				String productID = row.get(18);

				Invoice invoice = invoiceRepository.findByNumber(invoiceNumber);
				if (invoice == null) {
					invoice = new Invoice();
					invoice.setNumber(invoiceNumber);
					invoiceRepository.save(invoice);
				}

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
				LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

				Receipt receipt = receiptRepository.findByNumberAndDate(receiptNo, dateTime);
				if (receipt == null) {
					receipt = new Receipt();
				} else {
					if (receipt.getReceiptDateTime().compareTo(dateTime) != 0) {
						receipt = new Receipt();
					}
				}
				receipt.setInvoice(invoice);
				receipt.setNumber(receiptNo);
				receipt.setVolume(Float.parseFloat(volume));
				receipt.setPrice(Float.parseFloat(amountInclDiscount) / Float.parseFloat(volume));

				receipt.setReceiptDateTime(dateTime);

				Fuel fuel = null;
				if (productID.equals("00010309")) {
					fuel = fuelRepository.findByName("LPG");
					if (fuel == null) {
						fuel = new Fuel();
						fuel.setName("LPG");
						fuelRepository.save(fuel);
					}
				}

				if (productID.equals("00010101")) {
					fuel = fuelRepository.findByName("E98");
					if (fuel == null) {
						fuel = new Fuel();
						fuel.setName("E98");
						fuelRepository.save(fuel);
					}
				}

				if (productID.equals("00010104")) {
					fuel = fuelRepository.findByName("E95");
					if (fuel == null) {
						fuel = new Fuel();
						fuel.setName("E95");
						fuelRepository.save(fuel);
					}
				}

				if (productID.equals("00010211")) {
					fuel = fuelRepository.findByName("DIESEL");
					if (fuel == null) {
						fuel = new Fuel();
						fuel.setName("DIESEL");
						fuelRepository.save(fuel);
					}
				}

				receipt.setFuel(fuel);
				receiptRepository.save(receipt);

			}

		} catch (IOException | CsvValidationException e) {
			// Handle the exception appropriately
			e.printStackTrace();
		}

		return "redirect:/upload"; // Redirect to a success page
	}
}
