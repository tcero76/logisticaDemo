package com.logistica.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.logistica.demo.repository",
        entityManagerFactoryRef = "entityManagerFactory")
@EnableCaching
public class JpaConfig {

    private static final Logger log = LoggerFactory.getLogger(JpaConfig.class);

    @Value("${HOST}")
    private String host;

    @Value("${PORT}")
    private String port;

    @Value("${DATABASE}")
    private String database;

    @Value("${DRIVER}")
    private String driver;

    @Value("${PROTOCOL}")
    private String protocol;

    @Value("${USER}")
    private String user;

    @Value("${PASS}")
    private String pass;

    @Value("${TEST}")
    private boolean test;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.logistica.demo.model");
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(adapter);
        emf.setJpaProperties(jpaProperties());
        log.debug("Entity Manager creado");
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tx = new JpaTransactionManager();
        tx.setEntityManagerFactory(entityManagerFactory().getObject());
        return tx;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String urlDatasource = protocol.equals("h2")?"jdbc:h2:mem:"+database:"jdbc:" + protocol + "://"+host+":"+port+"/"+database;
        dataSource.setUrl(urlDatasource);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);
        return dataSource;
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.setProperty("spring.datasource.driver-class-name",driver);
        if(test==true)  {
            properties.setProperty("hibernate.show_sql","true");
            properties.setProperty("hibernate.generate_statistics","true");
            properties.setProperty("org.hibernate.engine.internal.StatisticalLoggingSessionEventListener","info");
        }
        return properties;
    }
}
