package dev.gerardomarquez.mail_whatsapp_send.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class SecondaryDataBaseConfig {
    
    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties("app.datasource.secondary")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create()
            .type(com.zaxxer.hikari.HikariDataSource.class)
            .build();
    }

    @Bean(name = "secondaryJdbcTemplate")
    public NamedParameterJdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
        return new NamedParameterJdbcTemplate(secondaryDataSource);
    }
}
