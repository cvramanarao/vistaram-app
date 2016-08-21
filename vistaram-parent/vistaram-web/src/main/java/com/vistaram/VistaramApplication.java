package com.vistaram;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
//import org.springframework.integration.jmx.config.EnableIntegrationMBeanExport;
//import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.vistaram.batch.processor.VistaramEmailMessageProcessor;
import com.vistaram.batch.writer.VistaramDetailsWriter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan
@EnableAsync
public class VistaramApplication  extends WebMvcConfigurerAdapter {
	
	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/login").setViewName("login");
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer 
				= new PropertySourcesPlaceholderConfigurer();
		System.out.println("user.home = "+System.getProperty("user.home"));
		propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource("application.properties"), 
				new FileSystemResource(System.getProperty("user.home")+"/configuration/application.properties")
				);
		propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
		return propertySourcesPlaceholderConfigurer;
	}
	

	
	 @Bean
	 public VistaramDetailsWriter vistaramDetailsWriter(){
		 return new VistaramDetailsWriter();
	 }
	 
	 @Bean
	 public VistaramEmailMessageProcessor vistaramEmailMessageProcessor(){
		 return new VistaramEmailMessageProcessor();
	 }

}
