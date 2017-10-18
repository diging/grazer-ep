package edu.asu.diging.grazer.web;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class LoginController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String validUserHandle(ModelMap model, Principal principal,
            Authentication authentication) {

        // Get the LDAP-authenticated userid
        String sUserId = principal.getName();       
        model.addAttribute("username", sUserId);
        return "redirect:home";

    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "login";
        
    }
    
    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {

        model.addAttribute("error", "true");
        return "login";

    }
}
