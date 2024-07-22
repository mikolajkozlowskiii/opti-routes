package com.wroclawroutes.security.token;

import java.util.Date;

import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value(value = "${app.jwt.secret}")
    private String tokenSecret;
    @Value(value = "${app.jwt.expirationTimeInMs}")
    private int tokenExpirationTimeInMs;
    public String generateJwtToken(Authentication authentication) {
        final UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getId().toString()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + tokenExpirationTimeInMs))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public Long getUserIdFromJwtToken(String token) {
        return Long.valueOf(Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody().getSubject());
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}