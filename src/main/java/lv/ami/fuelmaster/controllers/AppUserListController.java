package lv.ami.fuelmaster.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.ami.fuelmaster.models.AppUser;
import lv.ami.fuelmaster.service.AppUserService;

@Controller
public class AppUserListController extends AbstractBaseController {
	
	@Autowired
	private AppUserService appUserService;

	@GetMapping("/user-list")
	public String listUsers(@RequestParam(defaultValue = "1") int page, Model model) {
		if (!isAuthenticated()) {
			return getRedirectToLoginUrl();
		}
		int pageSize = 10;
		List<AppUser> users = appUserService.getUsers(page, pageSize);
		Long totalUsers = appUserService.countUsers();
		Long pageCount = (long) Math.ceil((double) totalUsers / pageSize);
		model.addAttribute("users", users);
		model.addAttribute("page", page);
		model.addAttribute("pageCount", pageCount);
		return "userList";
	}
}