package cn.edu.zucc.config;


import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Bruce
 * @since 02-11-2021
 */
@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig implements ApplicationListener<WebServerInitializedEvent> {
    public static final String API_DIRECTORY = "cn.edu.zucc.controller";

    @Value("${swagger.enable}")
    private boolean swaggerEnable;

    @Value("${spring.application.name}")
    private String name;

    @Bean
    public Docket docket() {
        Contact contact = new Contact("BruceYu", "https://github.com/2432001677", "2432001677@qq.com");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("qop")
                .contact(contact)
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .apiInfo(apiInfo)
                .groupName(name)
                .select()
                .apis(RequestHandlerSelectors.basePackage(API_DIRECTORY))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void onApplicationEvent(@NonNull WebServerInitializedEvent webServerInitializedEvent) {
        if (!swaggerEnable) {
            return;
        }
        var port = webServerInitializedEvent.getWebServer().getPort();
        var applicationName = webServerInitializedEvent.getApplicationContext().getApplicationName();
        try {
            log.info("swagger-ui已开启");
            log.info("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + applicationName + "/swagger-ui.html");
        } catch (UnknownHostException e) {
            log.error("error for finding swagger address!", e);
        }
    }
}
