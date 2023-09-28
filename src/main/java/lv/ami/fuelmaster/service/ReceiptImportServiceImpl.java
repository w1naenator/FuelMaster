package lv.ami.fuelmaster.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import lv.ami.fuelmaster.models.Fuel;
import lv.ami.fuelmaster.models.Invoice;
import lv.ami.fuelmaster.models.PaymentCard;
import lv.ami.fuelmaster.models.Receipt;
import lv.ami.fuelmaster.models.Vehicle;
import lv.ami.fuelmaster.repositories.FuelRepository;
import lv.ami.fuelmaster.repositories.InvoiceRepository;
import lv.ami.fuelmaster.repositories.InvoiceRepositoryImpl;
import lv.ami.fuelmaster.repositories.PaymentCardRepository;
import lv.ami.fuelmaster.repositories.ReceiptRepository;

@Service
@Transactional
public class ReceiptImportServiceImpl implements ReceiptImportService {
	
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private FuelRepository fuelRepository;
	
	@Autowired
	private PaymentCardRepository paymentCardRepository;

	@Autowired
	private ReceiptRepository receiptRepository;
	
	private String errorMessage = new String();
	private Boolean error = false;

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public Boolean hasError() {
		return error;
	}



	@Override
	public void importCircleKSemicomCSV(MultipartFile file) {
		
			errorMessage = new String();
			error = false;
			// Check file present
			if (file.isEmpty()) {
				errorMessage += "Nav izvēlēts fails";
				error = true;
			}
			else {
				errorMessage += "Fails ir";
			}

			
			// Parse file
			Reader reader = null;
			CSVReader csvReader = null;
			if (!error) {
				errorMessage += " -> Sāk faila apstrādi";
				try {
					reader = new InputStreamReader(file.getInputStream());
					csvReader = new CSVReaderBuilder(reader).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();
					if (csvReader.peek() == null) {
						errorMessage += " -> Failā nav datu";
						error = true;
					}
					else {
						errorMessage += " -> Failā ir dati";
					}
					
				} catch (Exception e) {
					errorMessage += " -> Datu iegūšanas kļūda, pirmais etaps: " + e.toString();
					error = true;
				}
			}
			else {
				errorMessage += " -> Kļūda";
			}

			//
			List<List<String>> rows = new ArrayList<>();
			if (!error) {

				String[] nextRecord;
				int lineNumber = 0;
				try {
					while ((nextRecord = csvReader.readNext()) != null) {
						lineNumber++;
						if (lineNumber == 1) {
							String fields[] = { "Date", "Card Number", "Card text line 2", "Driver", "Vehicle",
									"ReceiptNo", "Station", "Country", "Product", "Volume", "Price", "Amount", "VAT",
									"NET", "Discount", "Amount incl.  Discount", "Odometer", "Invoice Number",
									"Product ID" };
							int i = 0;
							for (String value : nextRecord) {
								if (!value.equals(fields[i])) {
									errorMessage = "Pirmai rindai jāsatur: " + String.join(";", fields) + "\"";
									error = true;
									break;
								}
								i++;
							}
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
				} catch (Exception e) {
					errorMessage = "Datu iegūšanas kļūda, otrais etaps: " + e.toString();
					error = true;
				}
			}

			if (!error) {
				errorMessage += ", Faulā kļūdu nav ";
				// Process the rows (columns and values) as needed
				for (List<String> row : rows) {
					/*
					 * if (rows.size() != 19) { model.addAttribute("errorMessage",
					 * "Faila formāta kļūda."); return "upload"; // Redirect to a success page }
					 */
					String date = row.get(0);
					// String cardNumber = row.get(1);
					 String cardTextLine2 = row.get(2);
					// String driver = row.get(3);
					// String vehicle = row.get(4);
					String receiptNo = row.get(5);
					// String station = row.get(6);
					// String country = row.get(7);
					// String product = row.get(8);
					String volume = row.get(9);
					// String price = row.get(10);
					// String amount = row.get(11);
					// String vat = row.get(12);
					// String net = row.get(13);
					// String discount = row.get(14);
					String amountInclDiscount = row.get(15);
					// String odometer = row.get(16);
					String invoiceNumber = row.get(17);
					String productID = row.get(18);
					

					
					Invoice invoice = null;
					try {
						errorMessage += "Meklē pavadzīmi:";
						invoice = invoiceRepository.findByNumber(invoiceNumber);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e = new Exception("Error: invoiceRepository.findByNumber(invoiceNumber) " + e.getMessage());
						e.printStackTrace();
					}
					if (invoice == null) {
						errorMessage += "Neatrada:";
						invoice = new Invoice();
						invoice.setNumber(invoiceNumber);
						try {
							invoiceRepository.save(invoice);
							errorMessage += "Izveido jaunu. ";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							errorMessage += "Neizdevās izveidot jaunu. ";
						}
					}
					else
					{
						errorMessage += "Atrada:";
					}
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
					LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
					Receipt receipt = null;
					try {
						receipt = receiptRepository.getByNumberAndDate(receiptNo, dateTime);

						if (receipt == null) {
							receipt = new Receipt();
						}
						receipt.setInvoice(invoice);
						receipt.setNumber(receiptNo);
						receipt.setVolume(Float.parseFloat(volume));
						receipt.setPrice(Float.parseFloat(amountInclDiscount) / Float.parseFloat(volume));
	
						receipt.setReceiptDateTime(dateTime);
					} catch (Exception e) {
						errorMessage = "Čeku repozitorija kļūda: " + e.toString();
						error = true;
						break;
					}
					
					PaymentCard paymentCard = paymentCardRepository.findByName(cardTextLine2);
					Vehicle vehicle = null;
					if (paymentCard != null) vehicle = paymentCard.getDefaultVehicle();
					receipt.setUsedByVehicle(vehicle);
					
					Fuel fuel = null;
					if (productID.equals("00010309")) {
						try {
							fuel = fuelRepository.findByName("LPG");
							if (fuel == null) {
								fuel = new Fuel();
								fuel.setName("LPG");
								fuelRepository.save(fuel);
							}
						} catch (Exception e) {
							errorMessage = "Degvielas repozitorija kļūda: " + e.toString();
							error = true;
							break;
						}
					}

					if (productID.equals("00010101")) {
						try {
							fuel = fuelRepository.findByName("E98");
							if (fuel == null) {
								fuel = new Fuel();
								fuel.setName("E98");
								fuelRepository.save(fuel);
							}
						} catch (Exception e) {
							errorMessage = "Degvielas repozitorija kļūda: " + e.toString();
							error = true;
							break;
						}
					}

					if (productID.equals("00010104")) {
						try {
							fuel = fuelRepository.findByName("E95");
							if (fuel == null) {
								fuelRepository.save(new Fuel("E95"));
							}
						} catch (Exception e) {
							errorMessage = "Degvielas repozitorija kļūda: " + e.toString();
							error = true;
							break;
						}
					}

					if (productID.equals("00010211")) {
						try {
							fuel = fuelRepository.findByName("DIESEL");
							if (fuel == null) {
								fuel = new Fuel();
								fuel.setName("DIESEL");
								fuelRepository.save(fuel);
							}
						} catch (Exception e) {
							errorMessage = "Degvielas repozitorija kļūda: " + e.toString();
							error = true;
							break;
						}
					}

					try {
						receipt.setFuel(fuel);
						receiptRepository.save(receipt);
					} catch (Exception e) {
						errorMessage = "Čeku repozitorija kļūda: " + e.toString();
						error = true;
						break;
					}
				}
			}
	}

}
