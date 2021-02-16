package cn.edu.zucc.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 使非实体类支持成为jpa查询的结果
 */
@Documented
@Component
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JpaEntity {
}