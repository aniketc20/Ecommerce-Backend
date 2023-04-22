package security;

import models.UserEntity;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private final JwtGeneratorService jwtProvider;

    public JwtAuthenticationFilter(JwtGeneratorService jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader==null) {
            filterChain.doFilter(request, response);
            return;
        }
		String token = getAccessToken(request);
		if (!jwtProvider.validateAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }
		setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
	}
	
	private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }
	
	private void setAuthenticationContext(String token, HttpServletRequest request) {
		UserEntity userEntity = getUserDetails(token);
 
        UsernamePasswordAuthenticationToken
            authentication = new UsernamePasswordAuthenticationToken(userEntity, null, null);
 
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
 
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
 
    private UserEntity getUserDetails(String token) {
        UserEntity userEntity = new UserEntity();
        String[] jwtSubject = jwtProvider.getSubject(token).split(",");
        
        userEntity.setUsername(jwtSubject[0]);
 
        return userEntity;
    }

}
