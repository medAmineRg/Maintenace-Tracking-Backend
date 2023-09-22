package cmms.mme.utils;

import cmms.mme.entity.AppUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.stream.Collectors;

public class JWTUtils {

    public static final String SECRET="guessIt_;;23";
    public static final long ACCESS_TOKEN_EXPIRATION= 60L *60*24*30*12*1000;
    public static final long REFRESH_TOKEN_EXPIRATION=60*60*24*30*12*1000;
    public static final String REFRESH_TOKEN_PATH="/refresh-token";
    public static final String AUTH_HEADER="Authorization";
    public static final String AUTH_PREFIX="Bearer ";


    public static String generateAccessToken(AppUser user, String issuer, String type) {
        Algorithm algorithm = generateAlgorithm();
        long calType = (type.equals("access") )? ACCESS_TOKEN_EXPIRATION : REFRESH_TOKEN_EXPIRATION;

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + calType))
                .withIssuer(issuer)
                .withClaim("roles", user.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(generateAlgorithm()).build();
        return jwtVerifier.verify(token);
    }

    public static Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(SECRET);
    }



}
