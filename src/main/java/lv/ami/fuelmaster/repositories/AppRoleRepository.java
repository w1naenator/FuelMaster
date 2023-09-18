package lv.ami.fuelmaster.repositories;

import java.util.List;

import lv.ami.fuelmaster.models.AppRole;

public interface AppRoleRepository {
	AppRole findById(Long id);

	AppRole findByIdInitialized(Long id);

	AppRole findByName(String name);

	List<AppRole> findAll();

	List<AppRole> findAllInitialized();

	void save(AppRole role);

	void deleteById(Long id);
}