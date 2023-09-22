package cmms.mme.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {

    public static final String SECRET="guessIt_;;23";
    public static final long ACCESS_TOKEN_EXPIRATION= 60L *60*24*30*12*1000;
    //public static final long ACCESS_TOKEN_EXPIRATION= 60*1000;

    public static final long REFRESH_TOKEN_EXPIRATION= 60L *60*24*30*12*1000;
    public static final String REFRESH_TOKEN_PATH="/refresh-token";
    public static final String AUTH_HEADER="Authorization";
    public static final String AUTH_PREFIX="Bearer ";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(ACCESS_TOKEN_EXPIRATION));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return create(claims, userDetails);
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder().setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    private String create(Map<String, Object> claims, UserDetails user) {

        return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).claim("roles", user.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.toList())).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    public Boolean validate(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}