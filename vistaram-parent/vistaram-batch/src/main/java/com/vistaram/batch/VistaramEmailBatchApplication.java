/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vistaram.batch;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.Message;

import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.admin.service.SimpleJobServiceFactoryBean;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.bind.PropertySourceUtils;
import org.springframework.boot.env.PropertySourcesLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.vistaram.batch.listener.VistaramEmailMessageHandler;
import com.vistaram.batch.processor.VistaramEmailMessageProcessor;
import com.vistaram.batch.tasklet.VistaramEmailDataExtractor;
import com.vistaram.batch.writer.VistaramDetailsWriter;
import com.vistaram.data.config.DataSourceConfiguration;
import com.vistaram.data.domain.VoucherDetails;

@Configuration
@Import(DataSourceConfiguration.class)
@EnableTransactionManagement(proxyTargetClass=true)
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class VistaramEmailBatchApplication{

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	/*@Bean
	@JobScope
	public ExampleItemReader itemReader() {
		return new ExampleItemReader();
	}

	@Bean
	@StepScope
	public ExampleItemWriter itemWriter(@Value("#{jobParameters[fail]}") Boolean fail) {
		ExampleItemWriter itemWriter = new ExampleItemWriter();
		itemWriter.setFail(fail);
		return itemWriter;
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<String, Object>chunk(5)
				.reader(itemReader())
				.writer(itemWriter(null))
				.build();
	}

	@Bean
	public Job javaJob() {
		return jobBuilderFactory.get("javaJob")
				.start(step1())
				.build();
	}*/


	
	

	@Bean
	public Tasklet vistaramEmailDataExtractorTask() {
		/*return new Tasklet () {
			@Override
			public RepeatStatus execute(StepContribution contribution,
					ChunkContext context) {
				return RepeatStatus.FINISHED;
			}
		}; */
		return new VistaramEmailDataExtractor();
	}
	
	@Bean
	@StepScope
	protected Step vistaramDataExtractorStep() throws Exception {
		return stepBuilderFactory.get("vistaramDataExtractorStep").tasklet(vistaramEmailDataExtractorTask()).build();
	}
	

	@Bean
	@JobScope
	public Job vistaramDataExtractorJob(Step vistaramDataExtractorStep) throws Exception {
		return jobBuilderFactory.get("vistaramDataExtractorJob").incrementer(new RunIdIncrementer()).start(vistaramDataExtractorStep).build();
	}
	
	
	
	@Bean
	public VistaramEmailMessageProcessor vistaramEmailMessageProcessor(){
		return new VistaramEmailMessageProcessor();
	}
	
	@Bean
	public VistaramDetailsWriter vistaramDetailsWriter(){
		return new VistaramDetailsWriter();
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
