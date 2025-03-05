package org.cqrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Mukesh Verma
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.cqrs.repository")
public class OrderApp {
    public static void main(String[] args) {

        SpringApplication.run(OrderApp.class, args);
    }
}