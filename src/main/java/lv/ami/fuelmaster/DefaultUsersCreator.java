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
import lv.ami.fuelmaster.repositories.AppRoleRepository;
import lv.ami.fuelmaster.repositories.AppUserRepository;

@Component
public class DefaultUsersCreator implements ApplicationRunner {

	@Autowired
	private AppRoleRepository appRoleRepository;
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public DefaultUsersCreator(AppUserRepository appUserRepository) {
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

			// Create default user with user role
			/*List<AppRole> userRoles = new ArrayList<AppRole>();// (Collections.singletonList(appRoleRepository.findByName("ROLE_USER")));
			userRoles.add(appRoleRepository.findByName("ROLE_USER"));
			AppUser regularUser = new AppUser("user", "user", "user", passwordEncoder.encode("user"), userRoles);
			appUserRepository.save(regularUser);*/


			//appUserRepository.save(new AppUser("user", "user", "user", passwordEncoder.encode("user"), new ArrayList<>()));
			//AppUser regularUser = appUserRepository.findByUsername("user");
			AppUser regularUser = new AppUser("user", "user", "user", passwordEncoder.encode("user"), new ArrayList<>());
			regularUser.getRoles().add(appRoleRepository.findByName("ROLE_USER"));
			appUserRepository.save(regularUser);
		}
	}
}
