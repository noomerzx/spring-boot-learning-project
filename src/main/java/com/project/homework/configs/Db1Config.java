package com.project.homework.configs;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  entityManagerFactoryRef = "db1EntityManagerFactory",
  basePackages = { "com.project.homework.db1.repositories" }
)
public class Db1Config {
  @Autowired
  private Environment env;

  @Primary
  @Bean(name = "db1DataSource")
  @ConfigurationProperties(prefix = "db1.datasource")
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("db1.datasource.driver-class-name"));
    dataSource.setUrl(env.getProperty("db1.datasource.url"));
    dataSource.setUsername(env.getProperty("db1.databsource.username"));
    dataSource.setPassword(env.getProperty("db1.datasource.password"));
    return dataSource;
  }
  
  @Primary
  @Bean(name = "db1EntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("db1DataSource") DataSource dataSource) {
    HashMap<String, String> prop = new HashMap<>();
    prop.put("hibernate.dialect", "org.hibernate.dialect.MySQL55Dialect");
    // prop.put("hibernate.dialect.storage_engine", "innodb");
    prop.put("hibernate.hbm2ddl.auto", "create-drop");
    // prop.put("show-sql", "true");
    return builder
      .dataSource(dataSource)
      .packages("com.project.homework.db1.entities")
      .persistenceUnit("db1")
      .properties(prop)
      .build();
  }
    
  @Primary
  @Bean(name = "transactionManager")
  public PlatformTransactionManager db1TransactionManager(@Qualifier("db1EntityManagerFactory") EntityManagerFactory db1EntityManagerFactory) {
    return new JpaTransactionManager(db1EntityManagerFactory);
  }
}