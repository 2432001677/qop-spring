package cn.edu.zucc.filter;

import cn.edu.zucc.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bruce
 * @since 02-14-2021
 * <p>
 * 全局过滤请求
 */
@Slf4j
@Component
@RefreshScope
public class AuthFilter implements GlobalFilter {
    private static final List<String> whiteList = Arrays.asList("/user/login", "/user/register", "/user/password");

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String tokenSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var response = exchange.getResponse();
        var request = exchange.getRequest();
        var url = request.getURI().getPath();
        if (whiteList.contains(url)) {
            return chain.filter(exchange);
        }
        log.info(url);
        var authorizations = request.getHeaders().get("Authorization");
        if (CollectionUtils.isEmpty(authorizations) || !TokenUtils.verify(authorizations.get(0), tokenSecret, issuer)) {
            log.info("not authorized");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        log.info(authorizations.get(0));
        return chain.filter(exchange);
    }
}
