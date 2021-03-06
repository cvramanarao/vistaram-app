
package com.vistaram.data.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;



@Configuration
@ComponentScan(basePackages={"com.vistaram.data.service", "com.vistaram.data.relational.dao"})
@EnableJpaRepositories(basePackages={"com.vistaram.data.relational.repositories"}, considerNestedRepositories=true)
@EntityScan(basePackages={"com.vistaram.data.relational.domain"})
@EnableConfigurationProperties
public class DataSourceConfiguration {
	
	
	
	//@Autowired
	//private DataSource dataSource;

	@Value("${spring.datasource.driverClassName}")
    private String databaseDriverClassName; 
 
    @Value("${spring.datasource.url}")
    private String datasourceUrl;
 
    @Value("${spring.datasource.username}")
    private String databaseUsername;
 
    @Value("${spring.datasource.password}")
    private String databasePassword;

    /*
    <bean id="dataSource" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">
    <property name="initialSize" value="10" />
    <property name="maxActive" value="25" />
    <property name="maxIdle" value="20" />
    <property name="minIdle" value="10" />
     ...
    <property name="testOnBorrow" value="true" />
    <property name="validationQuery" value="SELECT 1" />
    	</bean>
    	
    */
    
    @Bean
    @Primary
    @Qualifier("tomcatDataSource")
    public DataSource tomcatDataSource()  {
    	
        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        System.out.println("databaseDriverClassName: "+databaseDriverClassName);
        ds.setDriverClassName(databaseDriverClassName);
        ds.setUrl(datasourceUrl);
        ds.setUsername(databaseUsername);
        ds.setPassword(databasePassword);
        ds.setInitialSize(10);
        ds.setMaxActive(25);
        ds.setMaxIdle(20);
        ds.setMinIdle(10);
        ds.setTestOnBorrow(true);
        ds.setValidationQuery("SELECT 1");
        
        return ds;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() throws ClassNotFoundException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws ClassNotFoundException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

   
			entityManagerFactoryBean.setDataSource(tomcatDataSource());
		
        //entityManagerFactoryBean.setPackagesToScan("com.vistaram.data.relational.domain");
        //HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
       // entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        jpaProperties.put("spring.jpa.database-platform", "org.hibernate.dialect.MySQLDialect");
        jpaProperties.put("", "");
       
        //jpaProperties.put("spring.jpa.generate-dd","true");
        jpaProperties.put("spring.jpa.show-sql","true");
        //jpaProperties.put("spring.jpa.hibernate.ddl-auto","create");
//        jpaProterties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
//        jpaProterties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
//        jpaProterties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));
//        jpaProterties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }
    
   /* @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
      return new PersistenceExceptionTranslationPostProcessor();
    }
    */
 
    
}
