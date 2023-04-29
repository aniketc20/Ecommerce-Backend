package security;

import java.util.Map;

import beans.UserBean;
import jakarta.servlet.http.Cookie;

/*
 * This interface lays the foundation for
 * 1. Create New JWT token once user creds verified
 * 2. Intercept incoming requests by invoking validateAccessToken
 * 3. Get the subject from JWT token
 * 5. Generate the cookie in which the JWT token is sent
 */
public interface JwtGeneratorService {
	Map<String, String> generateToken(UserBean user);
	boolean validateAccessToken(String token);
	String getSubject(String token);
	Cookie generateCookie(UserBean user);
}
