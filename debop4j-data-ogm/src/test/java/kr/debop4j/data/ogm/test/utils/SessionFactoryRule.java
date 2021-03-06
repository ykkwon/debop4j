package kr.debop4j.data.ogm.test.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.ogm.cfg.OgmConfiguration;
import org.junit.rules.TemporaryFolder;

import java.util.Map;

/**
 * kr.debop4j.data.ogm.test.utils.SessionFactoryRule
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 13. 4. 1
 */
public class SessionFactoryRule extends TemporaryFolder {

    static {
        TestHelper.initializeHelpers();
    }

    private final Class<?>[] entities;
    private final OgmConfiguration cfg = new OgmConfiguration();
    private SessionFactory sessions;
    private Session session;

    public SessionFactoryRule(Class<?>... entities) {
        if (entities == null || entities.length == 0) {
            throw new IllegalArgumentException("Define at least a single annotated entity");
        }
        this.entities = entities;
        cfg.setProperty(Environment.HBM2DDL_AUTO, "none");
        // by default use the new id generator scheme...
        cfg.setProperty(Configuration.USE_NEW_ID_GENERATOR_MAPPINGS, "true");
        // volatile indexes for Hibernate Search (if used)
        cfg.setProperty("hibernate.search.default.directory_provider", "ram");
        // disable warnings about unspecified Lucene version
        cfg.setProperty("hibernate.search.lucene_version", "LUCENE_36");
        for (Map.Entry<String, String> entry : TestHelper.getEnvironmentProperties().entrySet()) {
            cfg.setProperty(entry.getKey(), entry.getValue());
        }
    }

    public SessionFactoryRule setProperty(String key, String value) {
        if (sessions != null) {
            throw new IllegalStateException("SessionFactory already created");
        }
        cfg.setProperty(key, value);
        return this;
    }

    @Override
    public void before() throws Throwable {
        super.before();
        //start the SessionFactory eagerly so that it's part of the test fixture
        getSessionFactory();
    }

    @Override
    public void after() {
        if (session != null && session.isOpen()) {
            session.close();
            System.err.println("Had to close your lingering Session");
        }
        if (sessions != null) {
            sessions.close();
        }
        super.after();
    }

    public Session openSession() {
        if (session == null || !session.isOpen()) {
            return getSessionFactory().openSession();
        } else {
            throw new IllegalStateException("Previous session not closed! Manage your own if you need multiple sessions.");
        }
    }

    public SessionFactory getSessionFactory() {
        if (sessions == null) {
            for (Class<?> annotatedClass : entities) {
                cfg.addAnnotatedClass(annotatedClass);
            }
            sessions = cfg.buildSessionFactory();
        }
        return sessions;
    }
}
