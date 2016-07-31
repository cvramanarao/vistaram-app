package com.vistaram.batch;

import org.springframework.batch.admin.annotation.EnableBatchAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vistaram.batch.config.MainConfiguration;

/*@SpringBootApplication
public class VistaramBatchWebApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VistaramBatchWebApplication.class);
    }
	
	

    public static void main(String[] args) throws Exception {
        SpringApplication.run(VistaramBatchWebApplication.class, args);
    }

}*/
//@SpringBootApplication(exclude = {HypermediaAutoConfiguration.class, MultipartAutoConfiguration.class})
@EnableBatchAdmin
@ComponentScan
@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableAutoConfiguration(exclude = { BatchAutoConfiguration.class, DataSourceAutoConfiguration.class,
		WebMvcAutoConfiguration.class,HypermediaAutoConfiguration.class, MultipartAutoConfiguration.class })
public class VistaramBatchWebApplication extends SpringBootServletInitializer {

	/**
	 * Run application.
	 * 
	 * @param args
	 *            Parameters to pass to SpringApplication.
	 */
	public static void main(String[] args) {
		SpringApplication.run(VistaramBatchWebApplication.class, args);
	}

	/**
	 * @see org.springframework.boot.context.web.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(VistaramBatchWebApplication.class);
	}

}