package com.kaan.barcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.kaan.barcode.feign")
public class BarcodeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarcodeServiceApplication.class, args);
    }

}
