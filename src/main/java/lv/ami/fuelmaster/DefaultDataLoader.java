package lv.ami.fuelmaster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lv.ami.fuelmaster.models.AppRole;
import lv.ami.fuelmaster.models.AppUser;
import lv.ami.fuelmaster.models.Fuel;
import lv.ami.fuelmaster.models.FuelTank;
import lv.ami.fuelmaster.models.PaymentCard;
import lv.ami.fuelmaster.models.Vehicle;
import lv.ami.fuelmaster.repositories.AppRoleRepository;
import lv.ami.fuelmaster.repositories.AppUserRepository;
import lv.ami.fuelmaster.repositories.FuelRepository;
import lv.ami.fuelmaster.repositories.FuelTankRepository;
import lv.ami.fuelmaster.repositories.PaymentCardRepository;
import lv.ami.fuelmaster.repositories.VehicleRepository;

@Component
public class DefaultDataLoader implements ApplicationRunner {

	@Autowired
	private AppRoleRepository appRoleRepository;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private FuelRepository fuelRepository;
	
	@Autowired
	private PaymentCardRepository paymentCardRepository;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private FuelTankRepository fuelTankRepository;
	
	

	public DefaultDataLoader(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Check if the roles table is empty
		if (appRoleRepository.findAll().size() == 0) {
			// Create default roles
			appRoleRepository.save(new AppRole("ROLE_SUPERVISOR"));
			appRoleRepository.save(new AppRole("ROLE_ADMIN"));
			appRoleRepository.save(new AppRole("ROLE_USER"));
		}

		// Check if the users table is empty
		if (appUserRepository.findAll().size() == 0) {


			// Create default user with admin role
			List<AppRole> adminRoles = appRoleRepository.findAll();
			AppUser adminUser = new AppUser("admin", "admin", "admin", passwordEncoder.encode("admin"), adminRoles);
			appUserRepository.save(adminUser);


			AppUser regularUser = new AppUser("user", "user", "user", passwordEncoder.encode("user"), new ArrayList<>());
			regularUser.getRoles().add(appRoleRepository.findByName("ROLE_USER"));
			appUserRepository.save(regularUser);
		}
		
		if (fuelRepository.count() == 0) {
			fuelRepository.save(new Fuel("LPG"));
			fuelRepository.save(new Fuel("E95"));
			fuelRepository.save(new Fuel("E98"));
			fuelRepository.save(new Fuel("DIESEL"));
		}
		

		
		if (vehicleRepository.count() == 0) {
			Vehicle vehicle = null;
			
			
			//LT4966
			vehicle = new Vehicle();
			vehicle.setNumber("LT4966");
			vehicle.setPaymentCard(paymentCardRepository.findByName("1"));
			vehicleRepository.save(vehicle);
			
			//List of fuel tanks
			
			//Prepare tank 1
			FuelTank fuelTank = new FuelTank();
			fuelTank.setCapacity(55.0f);
			fuelTank.setConsumption(16.0f);
			List<Fuel> fuels = new ArrayList<>();
			fuels.add(fuelRepository.findByName("LPG"));
			fuelTank.setFuels(fuels);
			fuelTank.setVehicle(vehicle);
			fuelTankRepository.save(fuelTank);
			
			
			
			//Prepare tank 2
			fuelTank = new FuelTank();
			fuelTank.setCapacity(70.0f);
			fuelTank.setConsumption(16.0f);
			fuels = new ArrayList<>();
			fuels.add(fuelRepository.findByName("E95"));
			fuels.add(fuelRepository.findByName("E98"));
			fuelTank.setFuels(fuels);
			fuelTank.setVehicle(vehicle);
			fuelTankRepository.save(fuelTank);
			
			
			
			
			//HJ6070
			vehicle = new Vehicle();
			vehicle.setNumber("HJ6070");
			vehicle.setPaymentCard(paymentCardRepository.findByName("1"));
			vehicleRepository.save(vehicle);

			
			//Prepare tank 1
			fuelTank = new FuelTank();
			fuelTank.setCapacity(45.0f);
			fuelTank.setConsumption(16.0f);
			fuels = new ArrayList<>();
			fuels.add(fuelRepository.findByName("LPG"));
			fuelTank.setFuels(fuels);
			fuelTank.setVehicle(vehicle);
			fuelTankRepository.save(fuelTank);
			
			
			//Prepare tank 2
			fuelTank = new FuelTank();
			fuelTank.setCapacity(60.0f);
			fuelTank.setConsumption(16.0f);
			fuels = new ArrayList<>();
			fuels.add(fuelRepository.findByName("E95"));
			fuels.add(fuelRepository.findByName("E98"));
			fuelTank.setFuels(fuels);
			fuelTank.setVehicle(vehicle);
			fuelTankRepository.save(fuelTank);
			
			
			
		}	
		
		if (paymentCardRepository.count() == 0) {
			PaymentCard paymentCard = null;
			paymentCard = new PaymentCard("1");
			paymentCard.setDefaultVehicle(vehicleRepository.findByNumber("LT4966"));
			paymentCardRepository.save(paymentCard);
			
			paymentCard = new PaymentCard("2");
			paymentCard.setDefaultVehicle(vehicleRepository.findByNumber("HJ6070"));
			paymentCardRepository.save(paymentCard);
			
			paymentCardRepository.save(new PaymentCard("3"));
		}
	}
}
