package org.gradle;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ApplicationEurekaServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ApplicationEurekaServer.class)
        	.web(true)
        	.run(args);
    }
}
