package com.vistaram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class VistaramWebApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VistaramWebApplication.class);
    }
	
	

    public static void main(String[] args) throws Exception {
        SpringApplication.run(VistaramWebApplication.class, args);
    }

}
