package lv.ami.fuelmaster.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.ami.fuelmaster.models.AppRole;
import lv.ami.fuelmaster.models.AppUser;
import lv.ami.fuelmaster.repositories.AppRoleRepository;

@Controller
public class AppUsersByRolesController {

	@Autowired
	private AppRoleRepository appRoleRepository;

	/*
	 * @GetMapping("/app-users-by-role") public String getAllUsersByRole(Model
	 * model) { List<AppRole> roles = appRoleRepository.findAllInitialized();
	 * model.addAttribute("roles", roles); model.addAttribute("users", new
	 * ArrayList<AppUser>()); // add this line
	 *
	 * return "appUsersByRoles"; }
	 *
	 * @GetMapping("/app-users-by-role/{roleId}")
	 *
	 * @ResponseBody public List<AppUser> getUsersByRoleId(@PathVariable Long
	 * roleId) { AppRole role = appRoleRepository.findByIdInitialized(roleId);
	 * List<AppUser> users = role.getAppUsers(); if (users != null) {
	 * Hibernate.initialize(users); } return users; }
	 */

	@GetMapping("/app-users-by-role")
	// @ResponseBody
	public String getUsersByRoleId(@RequestParam(name = "roleId", required = false) Long roleId, Model model) {
		List<AppRole> roles = appRoleRepository.findAllInitialized();
		AppRole role = null;
		List<AppUser> users = null;
		if (roleId != null) {
			role = appRoleRepository.findByIdInitialized(roleId);
			users = role.getAppUsers();
			/*
			 * if (users != null) { Hibernate.initialize(users); }
			 */
		}
		model.addAttribute("roles", roles);
		model.addAttribute("users", users); // add this line
		model.addAttribute("roleId", roleId);
		return "appUsersByRoles";

	}
}