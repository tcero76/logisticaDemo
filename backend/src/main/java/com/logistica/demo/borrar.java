package com.logistica.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class borrar {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("demo"));

    }
}
