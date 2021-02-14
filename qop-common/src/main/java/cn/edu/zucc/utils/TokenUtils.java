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

    private static final String TOKEN_SECRET = "naive";

    public static DecodedJWT getJwt(String token) {
        return JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("angry").build().verify(token);
    }

    public static Long getUserId(String token) {
        return getJwt(token).getClaim("userId").asLong();
    }
}
