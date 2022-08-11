package com.learn2code.shop;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Profile("test")
@ComponentScan(basePackages = {"com.learn2code.shop.*"})
@Configuration
@EnableTransactionManagement
@PropertySource({"application.yml"})
@EnableJpaRepositories(
        basePackages = {"com.learn2code.shop"},
        entityManagerFactoryRef = "EntityManagerFactory",
        transactionManagerRef = "TransactionManager"
)
public class H2Config {

    @Primary
    @Profile("test")
    @Bean(name = "DataSource")
    @ConfigurationProperties(prefix = "spring.datasource2")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean EntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("DataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.learn2code.shop")
                .build();
    }

    @Primary
    @Bean(name = "TransactionManager")
    public PlatformTransactionManager barTransactionManager(
            @Qualifier("EntityManagerFactory") EntityManagerFactory EntityManagerFactory) {
        return new JpaTransactionManager(EntityManagerFactory);
    }
}




