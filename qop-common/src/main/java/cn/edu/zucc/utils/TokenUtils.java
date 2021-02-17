package cn.edu.zucc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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

    public static DecodedJWT getJwt(String token, String tokenSecret, String issuer) {
        return JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(issuer).build().verify(token);
    }

    public static Long getUserId(String token, String tokenSecret, String issuer) {
        return getJwt(token, tokenSecret, issuer).getClaim("userId").asLong();
    }
}
