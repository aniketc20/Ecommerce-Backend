package security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtGeneratorService jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String token = getAccessToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!jwtProvider.validateAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(HttpServletRequest request) {
        try {
            String token = WebUtils.getCookie(request, "token").getValue();
            return token;
        } catch (Exception e) {
            return null;
        }
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
