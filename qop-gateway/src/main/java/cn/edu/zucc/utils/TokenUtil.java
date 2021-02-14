package cn.edu.zucc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bruce
 * @since 02-14-2021
 */
@Slf4j
public class TokenUtil {
    private TokenUtil() {
    }

    private static final String TOKEN_SECRET = "naive";

    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("angry").build();
            DecodedJWT jwt = verifier.verify(token);
            log.debug("verify access");
            log.debug(jwt.getClaim("email").asString());
            log.debug(jwt.getClaim("phoneNumber").asString());
            log.debug("expiration at " + jwt.getExpiresAt());
        } catch (IllegalArgumentException | JWTVerificationException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}
