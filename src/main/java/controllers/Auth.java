package controllers;

import beans.UserBean;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import org.json.JSONException;
import org.json.JSONObject;

import security.JwtGeneratorService;

import service.ProductService;
import service.UserService;


@RestController
@CrossOrigin(origins = {"${spring.datasource.apiUrl}", "http://localhost:3000", "http://127.0.0.1:3000"}, allowCredentials = "true", allowedHeaders = "*")
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
    public ResponseEntity<?> dashboard(@CurrentSecurityContext SecurityContext context) throws JSONException {
        return ResponseEntity.ok().body(prodService.getAll().toString());
    }

    /*
     * Receives username and password from client,
     * verifies whether creds are correct
     * -> if true(create JWT)
     * -> else return "invalid creds"
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody UserBean user, HttpServletResponse response) throws JSONException {
    	JSONObject jsonResponse = new JSONObject();
        if (userService.authenticate(user.getUsername(), user.getPassword())) {
            Cookie jwtCookie = jwtGenerator.generateCookie(user);
            response.addCookie(jwtCookie);
            jsonResponse.put("isAuthenticated", true);
            jsonResponse.put("userName", user.getUsername());
            return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
        }
        jsonResponse.put("message", "UserName or Password is Invalid");
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) throws JSONException {
    	JSONObject jsonResponse = new JSONObject();
    	jsonResponse.put("isAuthenticated", false);
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }
    
    @PostMapping("/isAuthenticated")
    public ResponseEntity<?> isAuthenticated(@CurrentSecurityContext SecurityContext context) throws Exception {
    	JSONObject response = new JSONObject();
    	response.put("isAuthenticated", 
    			context.getAuthentication().getName() == "anonymousUser" ? false:true);
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }
}
