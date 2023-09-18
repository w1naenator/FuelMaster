package lv.ami.fuelmaster.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController extends AbstractBaseController {

	@GetMapping(value = { "/", "/home" })
	public String home(Model model, HttpServletRequest request) {

		/*if (!isAuthenticated()) {
            return getRedirectToLoginUrl();
        }*/

		 return "home";

	}

}
