package cn.edu.zucc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Bruce
 * @since 02-11-2021
 */
@SpringBootApplication
@EnableDiscoveryClient
public class QopAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(QopAccountApplication.class, args);
    }
}
