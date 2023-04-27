package com.gk_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class GkBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(GkBoardApplication.class, args);
    }

}
