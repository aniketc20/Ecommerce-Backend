package security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * This class deals with custom unauthorized error message
 * Basically this is the Entry Point for our JWT Authentication
 * Once it passed this you are good to go 👍
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
	    response.setContentType("application/json;charset=UTF-8");
	    try {
			response.getWriter().write(new JSONObject()
					.put("timestamp", LocalDateTime.now())
					.put("message", "Access denied").toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
}
