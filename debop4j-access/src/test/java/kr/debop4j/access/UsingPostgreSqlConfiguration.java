package kr.debop4j.access;

import kr.debop4j.access.model.organization.Company;
import kr.debop4j.access.model.organization.CompanyCode;
import kr.debop4j.access.model.product.Product;
import kr.debop4j.data.hibernate.springconfiguration.PostgreSqlConfigBase;
import kr.debop4j.data.hibernate.tools.OracleNamingStrategy;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * kr.debop4j.access.UsingPostgreSqlConfiguration
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 3. 7.
 */
@Configuration
@EnableTransactionManagement
public class UsingPostgreSqlConfiguration extends PostgreSqlConfigBase {

    @Override
    public String getDatabaseName() {
        return "HAccess";
    }

    @Override
    protected String[] getMappedPackageNames() {
        return new String[]{
                CompanyCode.class.getPackage().getName(),
                Company.class.getPackage().getName(),
                Product.class.getPackage().getName(),
                kr.debop4j.access.model.workcalendar.WorkCalendar.class.getPackage().getName(),
        };
    }

    @Override
    public Properties hibernateProperties() {
        Properties props = super.hibernateProperties();

        props.put(Environment.USE_SECOND_LEVEL_CACHE, true);
        props.put(Environment.USE_QUERY_CACHE, true);
        props.put(Environment.CACHE_REGION_FACTORY, SingletonEhCacheRegionFactory.class.getName());
        props.put(Environment.CACHE_PROVIDER_CONFIG, "classpath:ehcache.xml");

        return props;
    }

    @Override
    protected void setupSessionFactory(LocalSessionFactoryBean factoryBean) {
        super.setupSessionFactory(factoryBean);
        factoryBean.setNamingStrategy(new OracleNamingStrategy());
    }
}
