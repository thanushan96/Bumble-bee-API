//package net.bumblebee.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//
//@Controller
//public class LoginController {
//
//	@GetMapping("/login")
//	public String login() {
//		return "login";
//	}
//
//}

//package net.bumblebee.controller;
//
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@CrossOrigin("http://localhost:3000")
//public class LoginController {
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//}


package net.bumblebee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@CrossOrigin("http://localhost:3000")
public class LoginController {

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam(required = false) String error, Authentication auth) {
        if (error != null) {
            String errorMessage = "Invalid username and/or password.";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
        } else if (auth != null && auth.isAuthenticated()) {
            return new ResponseEntity<>("Login Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}

