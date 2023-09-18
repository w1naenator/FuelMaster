package lv.ami.fuelmaster.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import lv.ami.fuelmaster.models.AppUser;
import lv.ami.fuelmaster.repositories.AppUserRepository;

@Controller
public abstract class AbstractBaseController  extends AbstractController {
	@Autowired
	AppUserRepository appUserRepository;

	@ModelAttribute("loggedInUser")
	public AppUser getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			if ( auth.isAuthenticated()) {
			return appUserRepository.findByUsername(auth.getName());
			}
		}
		return null;
	}

	@ModelAttribute("isAuthenticated")
	public boolean isAuthenticated() {
		return getLoggedInUser() != null;
	}
	
	@ModelAttribute("contextPath")
	public String getContextPath(HttpServletRequest request) {
		return request.getContextPath();
	}

	protected String getRedirectToLoginUrl() {
		return "redirect:/login";
	}

	// Add default attributes
    protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        String contextPath = request.getContextPath();
        model.put("contextPath", contextPath);
        model.put("loggedInUser", getLoggedInUser());
        return model;
    }
 
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }


}
