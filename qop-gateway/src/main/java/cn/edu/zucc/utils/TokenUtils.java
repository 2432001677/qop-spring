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
public class TokenUtils {
    private TokenUtils() {
    }

    public static boolean verify(String token, String tokenSecret, String issuer) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(issuer).build();
            DecodedJWT jwt = verifier.verify(token);
            log.info("verify access: " + jwt.getClaim("userId").asLong());
            log.info("expiration at " + jwt.getExpiresAt());
        } catch (IllegalArgumentException | JWTVerificationException e) {
            return false;
        }
        return true;
    }
}
