package server.estore.Service_Implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import server.estore.Model.User.User;
import server.estore.Service.JwtService;

import java.util.Date;

@Service
@Getter
@Setter
public class JwtServiceImpl implements JwtService {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.issuer}")
    private String issuer;

    private Algorithm algorithm;

    @PostConstruct
    protected void init(){
        algorithm = Algorithm.HMAC256(secretKey);
    }

    // create an access token
    public String createAccessToken(User user){
        return JWT.create()
                .withIssuer(issuer)
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24))
                .sign(algorithm);
    }

    // create a refresh token
    public String createRefreshToken(User user){
        return JWT.create()
                .withIssuer(issuer)
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24*7))
                .sign(algorithm);
    }

    // decode token with assigned algorithm
    public DecodedJWT decodeToken(String token){
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);

    }

    // extract username from token
    public String extractUsername(String token){
        return decodeToken(token).getClaim("username").asString();
    }

    // extract role from token
    public String extractRole(String token){
        return decodeToken(token).getClaim("role").asString();
    }

    // extract expiration from token
    public Date extractExpiration(String token){
        return decodeToken(token).getExpiresAt();
    }

    // check if token already expired
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // check if the token is valid for current user
    public boolean isTokenValid(String token, User user){
        String username = extractUsername(token);
        return username.equals(user.getUsername());
    }
}
