package cn.edu.zucc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Bruce
 * @since 02-14-2021
 * <p>
 * 用户登录成功后签发jwt-token
 */
@Slf4j
public class TokenUtils {
    private TokenUtils() {
    }

    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000L; // 有效期24小时

    /**
     * 签发
     *
     * @param userId 登录信息
     * @return token
     */
    public static String sign(Long userId, String tokenSecret, String issuer) {
        String token = null;
        try {
            var expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer(issuer)
                    .withClaim("userId", userId)
                    .withExpiresAt(expiresAt)
                    .sign(Algorithm.HMAC256(tokenSecret));
        } catch (IllegalArgumentException | JWTCreationException e) {
            log.error(e.getMessage(), e);
        }
        return token;
    }
}
