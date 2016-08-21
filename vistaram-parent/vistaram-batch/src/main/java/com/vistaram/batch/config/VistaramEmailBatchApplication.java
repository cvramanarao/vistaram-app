package com.vistaram.batch.config;


import java.util.Properties;

import javax.mail.Message;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.vistaram.batch.listener.VistaramEmailMessageHandler;
import com.vistaram.batch.processor.VistaramEmailMessageBookingDetailProcessor;
import com.vistaram.batch.processor.VistaramGmailMessageBookingDetailProcessor;
import com.vistaram.batch.processor.VistaramGmailMessageProcessor;
import com.vistaram.batch.reader.VistaramEmailMessageReader;
import com.vistaram.batch.reader.VistaramGmailMessageReader;
import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.relational.domain.BookingDetail;

@Configuration
@Component
//@EnableBatchProcessing
//@EnableTransactionManagement
//@Import(DataSourceConfiguration.class)
//@EnableJpaRepositories(basePackages="com.vistaram.data.relational.repositories")
@ImportResource("file:///${user.home}/configuration/vistaram-email-integration.xml")
public class VistaramEmailBatchApplication{

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;	
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	//@Qualifier("jpaTrx")
	public PlatformTransactionManager jpaTransactionManager() {
	       return new JpaTransactionManager(emf().getObject());
	}
	
	@Bean
    public LocalContainerEntityManagerFactoryBean emf() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.vistaram.data.relational.domain");
        emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        jpaProperties.put("spring.jpa.database-platform", "org.hibernate.dialect.MySQLDialect");
       
        //jpaProperties.put("spring.jpa.generate-dd","true");
        jpaProperties.put("spring.jpa.show-sql","true");
        //jpaProperties.put("spring.jpa.hibernate.ddl-auto","create");
//        jpaProterties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
//        jpaProterties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
//        jpaProterties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));
//        jpaProterties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));

        emf.setJpaProperties(jpaProperties);
        return emf;
	}
	
	@Bean
	public ItemReader<Message> vistaramEmailMessageReader(){
		
		return new VistaramEmailMessageReader();
	}
	
	@Bean
	public ItemReader<com.google.api.services.gmail.model.Message> vistaramGmailMessageReader(){
		
		return new VistaramGmailMessageReader();
	}
	
	@Bean
	public ItemProcessor<com.google.api.services.gmail.model.Message, VoucherDetail> vistaramGmailMessageProcessor(){
		return new VistaramGmailMessageProcessor();
	}
	
	@Bean
	public ItemProcessor<com.google.api.services.gmail.model.Message, BookingDetail> vistaramGmailMessageBookingDetailProcessor(){
		return new VistaramGmailMessageBookingDetailProcessor();
	}
	
	@Bean
	public ItemProcessor<Message, BookingDetail> vistaramEmailMessageBookingDetailProcessor(){
		return new VistaramEmailMessageBookingDetailProcessor();
	}
	
	
	@Bean
	public ItemWriter<BookingDetail> bookingDetailWriter() {
	    JpaItemWriter<BookingDetail> itemWriter = new JpaItemWriter<BookingDetail>();
	    itemWriter.setEntityManagerFactory(emf().getObject());
	    return itemWriter;
	}
	
	/*@Bean
	public Step vistaramGmailDataExtractorStep() throws Exception {
		TaskletStep step = stepBuilderFactory.get("vistaramGmailDataExtractorStep").
				<com.google.api.services.gmail.model.Message, VoucherDetail> chunk(10).reader(vistaramGmailMessageReader()).processor(vistaramGmailMessageProcessor()).writer(vistaramDetailsWriter).build();
		return step;
	}*/
	
	@Bean
	public Step vistaramGmailDataExtractorStep() throws Exception {
		TaskletStep step = stepBuilderFactory.get("vistaramGmailDataExtractorStep").transactionManager(jpaTransactionManager()).
				<com.google.api.services.gmail.model.Message, BookingDetail> chunk(10).reader(vistaramGmailMessageReader()).
				processor(vistaramGmailMessageBookingDetailProcessor()).writer(bookingDetailWriter()).
				exceptionHandler(new ExceptionHandler() {
					
					@Override
					public void handleException(RepeatContext ctx, Throwable t)
							throws Throwable {
						t.printStackTrace(System.out);
					}
				}).build();
		return step;
	}
	
	
	@Bean
	public Job vistaramGmailDataExtractorJob(Step vistaramGmailDataExtractorStep) throws Exception {
		return jobBuilderFactory.get("vistaramGmailDataExtractorJob").incrementer(new RunIdIncrementer()).start(vistaramGmailDataExtractorStep).build();
	}
	

	@Bean
	public Step vistaramEmailDataExtractorStep() throws Exception {
		TaskletStep step = stepBuilderFactory.get("vistaramEmailDataExtractorStep").transactionManager(jpaTransactionManager()).
				<Message, BookingDetail> chunk(10).reader(vistaramEmailMessageReader()).
				processor(vistaramEmailMessageBookingDetailProcessor()).writer(bookingDetailWriter()).
				exceptionHandler(new ExceptionHandler() {
					
					@Override
					public void handleException(RepeatContext ctx, Throwable t)
							throws Throwable {
						t.printStackTrace(System.out);
					}
				}).build();
		return step;
	}
	
	
	@Bean
	public Job vistaramEmailDataExtractorJob(Step vistaramEmailDataExtractorStep) throws Exception {
		return jobBuilderFactory.get("vistaramEmailDataExtractorJob").incrementer(new RunIdIncrementer()).start(vistaramEmailDataExtractorStep).build();
	}
	
	@Autowired
	@Qualifier("receiveChannel")
	private DirectChannel directChannel;
	
	@Bean
	public VistaramEmailMessageHandler messageHandler(){
		VistaramEmailMessageHandler messageHandler = new VistaramEmailMessageHandler();
		// messageHandler.setVistaramDetailsWriter(vistaramDetailsWriter());
		directChannel.subscribe(messageHandler);
		return messageHandler;
	}
	    
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer 
				= new PropertySourcesPlaceholderConfigurer();
		
		propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource("application.properties"),new ClassPathResource("batch-mysql.properties"));
		propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
		return propertySourcesPlaceholderConfigurer;
	}
}
