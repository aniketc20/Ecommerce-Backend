package security;

import beans.UserBean;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtGeneratorService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${spring.datasource.cookieURL}")
    private String URL;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtServiceImpl.class);

    private static final String COOKIE_NAME = "token";

    /*
     * This method generates the JWT token using the secret key
     */
    @Override
    public Map<String, String> generateToken(UserBean user) {
        // TODO Auto-generated method stub
        String jwtToken = "";
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY),
                SignatureAlgorithm.HS256.getJcaName());

        jwtToken = Jwts.builder()
                .setSubject(String.format("%s", user.getUsername()))
                .setIssuedAt(new Date())
	    		.setExpiration(new Date(System.currentTimeMillis() + 200000))
                .signWith(hmacKey)
                .compact();

        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        jwtTokenGen.put("user", user.getUsername());
        System.out.print(jwtTokenGen);
        return jwtTokenGen;
    }

    /*
     * This method validates the JWT by invoking parseClaims method
     */
    public boolean validateAccessToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("SignatureException ", ex);
        }
        return false;
    }

    /*
     * Gets the subject from the client's JWT
     */
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /*
     * This method generates the cookie and adds the JWT to it
     */
    public Cookie generateCookie(UserBean user) {
        String token = generateToken(user).get("token");
        // Create a cookie with the value set as the token string
        try {
            Cookie jwtCookie = new Cookie(COOKIE_NAME, token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setDomain(URL);
            jwtCookie.setSecure(false);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(50000);
            return jwtCookie;
        } catch (Exception e) {
            return null;
        }
    }
}
