package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import jakarta.servlet.http.Cookie;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	JwtAuthenticationFilter jwtTokenFilter;
	
	@Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.authorizeHttpRequests((authorize) -> authorize
					.requestMatchers("/authenticate", "/register", "/isAuthenticated").permitAll()
					.anyRequest().authenticated())
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint);

		/*
		 * The below code is for logging out users
		 * By default, Spring Security redirects users 
		 * to /login?logout after a successful logout
		 * We are disabling it by just returning 'HttpStatus.OK'
		 * the user cookies are also deleted containing the JWT
		 */
		http.cors().and().csrf().disable()
		.logout(logout -> logout
				.logoutUrl("/logout")
				.addLogoutHandler((request, response, auth) -> {
					for (Cookie cookie : request.getCookies()) {
						String cookieName = cookie.getName();
						Cookie cookieToDelete = new Cookie(cookieName, null);
						cookieToDelete.setMaxAge(0);
						response.addCookie(cookieToDelete);
              }
          }).logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)));

		/*
								** Important **
		Spring Security is a filter based framework. Either we are 
		enabling existing filter and configuring it or adding our custom filter
		
		The addFilterBefore is invoked to intercept the incoming request
		On successful creds verification, a UsernamePasswordAuthenticationFilter object is returned
		 */ 
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
