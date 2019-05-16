package io.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringEurekaApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringEurekaApplication.class, args);
    }

}
