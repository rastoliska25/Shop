package com.learn2code.Shop;


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
@ComponentScan(basePackages = {"com.learn2code.Shop.*"})
@Configuration
@EnableTransactionManagement
@PropertySource({"application.properties"})
@EnableJpaRepositories(
        basePackages = {"com.learn2code.Shop"},
        entityManagerFactoryRef = "EntityManagerFactory",
        transactionManagerRef = "TransactionManager"
)
public class secondaryDbConfig {

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
        return builder.dataSource(dataSource).packages("com.learn2code.Shop")
                .build();
    }

    @Primary
    @Bean(name = "TransactionManager")
    public PlatformTransactionManager barTransactionManager(
            @Qualifier("EntityManagerFactory") EntityManagerFactory EntityManagerFactory) {
        return new JpaTransactionManager(EntityManagerFactory);
    }
}




