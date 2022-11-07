package br.com.alura.comex.security;

import br.com.alura.comex.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${comex.jwt.expiration}")
    private String expiration;
    @Value("${comex.jwt.secret}")
    private String secret;
    public String getToken(Authentication auth) {
        Usuario usuario = (Usuario) auth.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API do Comex da Alura")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Long getIdUsuario(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
        return Long.parseLong(claims.getBody().getSubject());
    }
}
