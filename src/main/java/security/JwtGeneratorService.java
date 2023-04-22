package security;

import java.util.Map;

import beans.UserBean;

public interface JwtGeneratorService {
	Map<String, String> generateToken(UserBean user);
	boolean validateAccessToken(String token);
	String getSubject(String token);
}
