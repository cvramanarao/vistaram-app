package com.vistaram.batch.config;


import javax.mail.Message;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vistaram.batch.processor.VistaramEmailMessageProcessor;
import com.vistaram.batch.processor.VistaramGmailMessageProcessor;
import com.vistaram.batch.reader.VistaramEmailMessageReader;
import com.vistaram.batch.reader.VistaramGmailMessageReader;
import com.vistaram.batch.tasklet.VistaramEmailDataExtractor;
import com.vistaram.batch.writer.VistaramDetailsWriter;
import com.vistaram.data.config.DataSourceConfiguration;
import com.vistaram.data.domain.VoucherDetail;

@Configuration
@ComponentScan
@Component
//@EnableBatchProcessing
@Import(DataSourceConfiguration.class)
public class VistaramEmailBatchApplication{

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;	
	
	@Autowired
	public VistaramEmailMessageProcessor vistaramEmailMessageProcessor;
	
	@Autowired
	public VistaramDetailsWriter vistaramDetailsWriter;
	
	@Bean
	public ItemReader<Message> vistaramEmailMessageReader(){
		
		return new VistaramEmailMessageReader();
	}
	
	@Bean
	public Step vistaramEmailDataExtractorStep() throws Exception {
		TaskletStep step = stepBuilderFactory.get("vistaramEmailDataExtractorStep").
				<Message, VoucherDetail> chunk(10).reader(vistaramEmailMessageReader()).processor(vistaramEmailMessageProcessor).writer(vistaramDetailsWriter).build();
		return step;
	}
	
	
	@Bean
	public Job vistaramEmailDataExtractorJob(Step vistaramEmailDataExtractorStep) throws Exception {
		return jobBuilderFactory.get("vistaramEmailDataExtractorJob").incrementer(new RunIdIncrementer()).start(vistaramEmailDataExtractorStep).build();
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
	public Step vistaramGmailDataExtractorStep() throws Exception {
		TaskletStep step = stepBuilderFactory.get("vistaramGmailDataExtractorStep").
				<com.google.api.services.gmail.model.Message, VoucherDetail> chunk(10).reader(vistaramGmailMessageReader()).processor(vistaramGmailMessageProcessor()).writer(vistaramDetailsWriter).build();
		return step;
	}
	
	
	@Bean
	public Job vistaramGmailDataExtractorJob(Step vistaramGmailDataExtractorStep) throws Exception {
		return jobBuilderFactory.get("vistaramGmailDataExtractorJob").incrementer(new RunIdIncrementer()).start(vistaramGmailDataExtractorStep).build();
	}
	
	
	
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer 
				= new PropertySourcesPlaceholderConfigurer();
		
		propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource("application.properties"),new ClassPathResource("batch-mysql.properties"), new FileSystemResource(System.getProperty("user.home")+"/configuration/application.properties"));
		propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
		return propertySourcesPlaceholderConfigurer;
	}
}
