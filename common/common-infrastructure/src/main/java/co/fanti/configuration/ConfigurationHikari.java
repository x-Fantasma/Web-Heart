package co.fanti.configuration;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class ConfigurationHikari extends HikariConfig {

    @Value("${spring.datasource.hikari.poolName}")
    private String poolName;

    @Bean
    public DataSource dataSource() {
        int poolSize = (Runtime.getRuntime().availableProcessors() * 2) + 1;
        setMaximumPoolSize(poolSize);
        setPoolName(poolName);
        return new HikariDataSource(this);
    }

}
