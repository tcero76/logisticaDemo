package com.logistica.demo.security;

import java.util.Date;

import com.logistica.demo.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtToken}")
    private String jwtSecret;

    @Value("${app.jwtTimems}")
    private int jwtExpirationInMs;
    
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    public String generateToken(Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Integer.toString(usuario.getIdusuario()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Integer getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Integer.valueOf(claims.getSubject());
    }
    
    public Boolean validateToken(String token) {
    	try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
		} catch (SignatureException ex) {
			logger.error(ex.getMessage());
		} catch (MalformedJwtException ex) {
			logger.error(ex.getMessage());
        } catch (ExpiredJwtException ex) {
			logger.error(ex.getMessage());
        } catch (UnsupportedJwtException ex) {
			logger.error(ex.getMessage());
        } catch (IllegalArgumentException ex) {
			logger.error(ex.getMessage());
        }
        return false;
    }
    
}
