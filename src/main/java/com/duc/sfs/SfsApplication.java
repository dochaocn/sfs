package com.dochao.sfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class SfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SfsApplication.class, args);
    }

}
