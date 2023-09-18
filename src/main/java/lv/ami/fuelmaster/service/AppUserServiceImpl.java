package lv.ami.fuelmaster.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.ami.fuelmaster.models.AppUser;
import lv.ami.fuelmaster.repositories.AppUserRepository;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private AppUserRepository appUserRepository;

	@Override
	public List<AppUser> findAllUsers() {
		return appUserRepository.findAll();
	}

	@Override
	public AppUser findUserById(Long id) {
		return appUserRepository.findById(id);
	}

	@Override
	public void saveUser(AppUser user) {
		appUserRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		appUserRepository.deleteById(id);
	}

	@Override
	public List<AppUser> getUsers(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<AppUser> userPage = appUserRepository.findAll(pageable);
        return userPage.getContent();
	}

	@Override
	public Long countUsers() {
		return appUserRepository.countUsers();
	}

	@Override
	public AppUser findUserByUsername(String username) {
		return appUserRepository.findByUsername(username);
	}

}
