package lv.ami.fuelmaster.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public interface ReceiptImportUtility {

	public String getErrorMessage();

	public Boolean hasError();

	public void importCircleKSemicomCSV(MultipartFile file);

}
