package com.logistica.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.logistica.demo.repository",
        entityManagerFactoryRef = "entityManagerFactory")
public class JpaConfig {

    private static final Logger log = LoggerFactory.getLogger(JpaConfig.class);

    @Value("${MYSQL_HOST}")
    private String host;

    @Value("${MYSQL_PORT}")
    private String port;

    @Value("${MYSQL_DATABASE}")
    private String database;

    @Value("${MYSQL_USER}")
    private String user;

    @Value("${MYSQL_PASS}")
    private String pass;

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
        dataSource.setUrl("jdbc:mysql://"+host+":"+port+"/"+database);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);
        return dataSource;
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.setProperty("spring.datasource.driver-class-name","com.mysql.jdbc.Driver");
//        properties.setProperty("hibernate.show_sql","true");
//        properties.setProperty("hibernate.generate_statistics","true");
//        properties.setProperty("org.hibernate.engine.internal.StatisticalLoggingSessionEventListener","info");
        return properties;
    }
}
