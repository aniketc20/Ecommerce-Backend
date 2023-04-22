package controllers;

import beans.*;
import models.*;
import security.JwtGeneratorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import service.ProductService;
import service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class Auth {

	@Autowired
	private UserService userService;
	@Autowired
	private ProductService prodService;
	@Autowired
	private JwtGeneratorService jwtGenerator;

	/*
	 * It saves object into database into model object. The @ModelAttribute puts
	 * request data
	 */
	@PostMapping("/register")
	public String save(@RequestBody UserBean user) {
		userService.save(user);
		return "success";// will redirect to viewemp request mapping
	}

	@GetMapping("/dashboard")
	public List<ProductBean> dashboard(Model model) {
		model.addAttribute("products", prodService.getAll());
		model.addAttribute("product", new ProductEntity());
		return prodService.getAll();
	}
	
	/*
	 * Receives username and password from client,
	 * verifies whether creds are correct 
	 * -> if true(create JWT) 
	 * -> else return "invalid creds"
	 */
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody UserBean user) {
		if (userService.authenticate(user.getUsername(), user.getPassword())) {
			return new ResponseEntity<>(jwtGenerator.generateToken(user), HttpStatus.OK);
		}
		return new ResponseEntity<>("UserName or Password is Invalid", HttpStatus.CONFLICT);
	}
}
