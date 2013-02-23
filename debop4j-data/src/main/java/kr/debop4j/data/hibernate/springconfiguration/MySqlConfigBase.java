package kr.debop4j.data.hibernate.springconfiguration;

import kr.debop4j.data.hibernate.forTesting.DatabaseEngine;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * kr.debop4j.data.hibernate.springconfiguration.MySqlDbConfiguration
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 21.
 */
@Configuration
@EnableTransactionManagement
public abstract class MySqlConfigBase extends HibernateConfigBase {

    @Override
    public DatabaseEngine getDatabaseEngine() {
        return DatabaseEngine.MySQL;
    }

    @Override
    public String getDatabaseName() {
        return "hibernate";
    }

    @Bean
    @Override
    public DataSource dataSource() {
        return buildDataSource("com.mysql.jdbc.Driver",
                               "jdbc:mysql://localhost/" + getDatabaseName(),
                               "root",
                               "root");
    }

    @Override
    @Bean
    public Properties hibernateProperties() {
        Properties props = super.hibernateProperties();

        props.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");

        return props;
    }
}