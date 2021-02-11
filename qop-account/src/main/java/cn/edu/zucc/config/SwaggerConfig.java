package cn.edu.zucc.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private boolean swaggerEnable;

    @Value("${spring.application.name}")
    private String name;

    @Resource
    private ApiInfo apiInfo;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .apiInfo(apiInfo)
                .groupName(name)
                .select()
                .apis(RequestHandlerSelectors.basePackage(SwaggerCommonConfig.apiDirectory))
                .paths(PathSelectors.any())
                .build();
    }
}
