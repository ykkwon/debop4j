package kr.debop4j.data.hibernate.forTesting.configs;

import kr.debop4j.data.hibernate.forTesting.LongEntityForTesting;
import kr.debop4j.data.hibernate.springconfiguration.PostgreSqlConfigBase;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * PostgreSql DB를 사용하는 Hibernate 설정 정보입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 22.
 */
@Configuration
@EnableTransactionManagement
public class PostgreSqlConfig extends PostgreSqlConfigBase {

    protected String[] getMappedPackageNames() {
        return new String[0];
    }

    protected void setupSessionFactory(LocalSessionFactoryBean factoryBean) {
        super.setupSessionFactory(factoryBean);
        factoryBean.setAnnotatedClasses(new Class[]{LongEntityForTesting.class});
    }
}
