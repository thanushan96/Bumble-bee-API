//package net.bumblebee.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import net.bumblebee.service.UserService;
//import net.bumblebee.dto.UserRegistrationDto;
//
//@Controller
//@RequestMapping("/registration")
//public class UserRegistrationController {
//
//	private UserService userService;
//
//	public UserRegistrationController(UserService userService) {
//		super();
//		this.userService = userService;
//	}
//
//	@ModelAttribute("user")
//    public UserRegistrationDto userRegistrationDto() {
//        return new UserRegistrationDto();
//    }
//
//	@GetMapping
//	public String showRegistrationForm() {
//		return "registration";
//	}
//
//	@PostMapping
//	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
//		userService.save(registrationDto);
//		return "redirect:/registration?success";
//	}
//}

package net.bumblebee.controller;

import org.springframework.web.bind.annotation.*;

import net.bumblebee.service.UserService;
import net.bumblebee.dto.UserRegistrationDto;

@RestController
@RequestMapping("/api/registration")
@CrossOrigin("http://localhost:3000")
public class UserRegistrationController {

	private UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping
	public String registerUserAccount(@RequestBody UserRegistrationDto registrationDto) {
		userService.save(registrationDto);
		return "Success";
	}
}
