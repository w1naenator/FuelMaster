package lv.ami.fuelmaster.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.models.AppUser;

@Service
@Transactional
public interface AppUserService {
	List<AppUser> getUsers(int page, int pageSize);

	List<AppUser> findAllUsers();

	AppUser findUserById(Long id);

	AppUser findUserByUsername(String username);

	void saveUser(AppUser user);

	void deleteUser(Long id);

	Long countUsers();
}
