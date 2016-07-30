package com.vistaram.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class VistaramBatchWebApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VistaramBatchWebApplication.class);
    }
	
	

    public static void main(String[] args) throws Exception {
        SpringApplication.run(VistaramBatchWebApplication.class, args);
    }

}
