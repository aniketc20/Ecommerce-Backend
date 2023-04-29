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
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
