package server.estore.Service;

import com.auth0.jwt.interfaces.DecodedJWT;
import server.estore.Model.User.User;

import java.util.Date;


public interface JwtService {

    String createAccessToken(User user);

    String createRefreshToken(User user);

    DecodedJWT decodeToken(String token);

    String extractUsername(String token);

    String extractRole(String token);

    Date extractExpiration(String token);

    boolean isTokenExpired(String token);

    boolean isTokenValid(String token, User user);
}
