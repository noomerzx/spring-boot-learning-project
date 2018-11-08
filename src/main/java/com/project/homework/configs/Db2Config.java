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
  entityManagerFactoryRef = "db2EntityManagerFactory",
  transactionManagerRef = "db2TransactionManager",
  basePackages = { "com.project.homework.db2.repositories" }
)
public class Db2Config {
  @Autowired
  private Environment env;

  @Bean(name = "db2DataSource")
  @ConfigurationProperties(prefix = "db2.datasource")
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("db2.datasource.driver-class-name"));
    dataSource.setUrl(env.getProperty("db2.datasource.url"));
    dataSource.setUsername(env.getProperty("db2.databsource.username"));
    dataSource.setPassword(env.getProperty("db2.datasource.password"));
    return dataSource;
  }
  
  @Bean(name = "db2EntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("db2DataSource") DataSource dataSource) {
    HashMap<String, String> prop = new HashMap<>();
    prop.put("hibernate.dialect", "org.hibernate.dialect.MySQL55Dialect");
    // prop.put("hibernate.dialect.storage_engine", "innodb");
    prop.put("hibernate.hbm2ddl.auto", "create-drop");
    // prop.put("show-sql", "true");
    return builder
      .dataSource(dataSource)
      .packages("com.project.homework.db2.entities")
      .persistenceUnit("db2")
      .properties(prop)
      .build();
  }
    
  @Bean(name = "db2TransactionManager")
  public PlatformTransactionManager db2TransactionManager(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2EntityManagerFactory) {
    return new JpaTransactionManager(db2EntityManagerFactory);
  }
}