package cn.edu.zucc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

@Configuration
public class SwaggerCommonConfig {
    public static final String apiDirectory = "cn.edu.zucc.controller";

    @Bean
    public ApiInfo getApiInfo() {
        Contact contact = new Contact("BruceYu", "https://github.com/2432001677", "2432001677@qq.com");
        return new ApiInfoBuilder()
                .title("qop")
                .contact(contact)
                .build();
    }
}
