package com.segurosbolivar.rvt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the RVT (Rentas Voluntarias) backend application.
 * Supports 'mock' and 'real' Spring profiles for development flexibility.
 */
@SpringBootApplication
public class RvtApplication {

    public static void main(String[] args) {
        SpringApplication.run(RvtApplication.class, args);
    }
}
