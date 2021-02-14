package cn.edu.zucc.utils;

import cn.edu.zucc.account.vo.LoginVo;
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
    private static final String TOKEN_SECRET = "naive";

    /**
     * 签发
     *
     * @param loginVo 登录信息
     * @return token
     */
    public static String sign(LoginVo loginVo) {
        String token = null;
        try {
            var expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("angry")
//                    .withClaim("email", loginVo.getEmail())
//                    .withClaim("phoneNumber", loginVo.getPhoneNumber())
                    .withExpiresAt(expiresAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException | JWTCreationException e) {
            log.error(e.getMessage(), e);
        }
        return token;
    }
}
