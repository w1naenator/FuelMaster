package lv.ami.fuelmaster.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.ami.fuelmaster.models.AppUser;

public interface AppUserRepository  {
	AppUser findByUsername(String username);

	AppUser findById(Long id);

	List<AppUser> findAll();

	Page<AppUser> findAll(Pageable pageable);

	Long countUsers ();

	void save(AppUser user);

	void deleteById(Long id);
}