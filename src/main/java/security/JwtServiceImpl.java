package security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import beans.UserBean;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@Service
public class JwtServiceImpl implements JwtGeneratorService {
	
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtServiceImpl.class);

	@Override
	public Map<String, String> generateToken(UserBean user) {
		// TODO Auto-generated method stub
		String jwtToken="";
		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), 
                SignatureAlgorithm.HS256.getJcaName());
	    
		jwtToken = Jwts.builder()
	    		.setSubject(String.format("%s", user.getUsername()))
	    		.setIssuedAt(new Date())
	    		.signWith(hmacKey)
	    		.compact();
	    
		Map<String, String> jwtTokenGen = new HashMap<>();
	    jwtTokenGen.put("token", jwtToken);
	    jwtTokenGen.put("user", user.getUsername());
	    return jwtTokenGen;
	}
	
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
	
	public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
     
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
