package org.cqrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Mukesh Verma
 */
@SpringBootApplication
public class InboundProcessor {
    public static void main(String[] args) {
        SpringApplication.run(InboundProcessor.class, args);
    }
}