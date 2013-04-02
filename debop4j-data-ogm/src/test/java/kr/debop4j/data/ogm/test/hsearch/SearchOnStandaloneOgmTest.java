package kr.debop4j.data.ogm.test.hsearch;

import kr.debop4j.data.ogm.test.simpleentity.OgmTestBase;
import org.apache.lucene.search.Query;
import org.fest.assertions.Assertions;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.junit.Test;

import java.util.List;

/**
 * kr.debop4j.data.ogm.test.hsearch.SearchOnStandaloneOgmTest
 *
 * @author sunghyouk.bae@gmail.com
 * @since 13. 4. 1
 */
public class SearchOnStandaloneOgmTest extends OgmTestBase {

    @Override
    protected void configure(Configuration cfg) {
        super.configure(cfg);
        cfg.setProperty("hibernate.search.default.directory_provider", "ram");
    }

    @Override
    protected Class<?>[] getAnnotatedClasses() {
        return new Class<?>[] { Insurance.class };
    }

    @Test
    public void hibernateSearchUsage() throws Exception {
        final Session session = openSession();
        final FullTextSession fts = Search.getFullTextSession(session);
        Transaction transaction = fts.beginTransaction();

        final Insurance insurance = new Insurance();
        insurance.setName("Macif");
        fts.persist(insurance);

        transaction.commit();
        fts.clear();

        transaction = fts.beginTransaction();
        final QueryBuilder b =
                fts.getSearchFactory()
                   .buildQueryBuilder()
                   .forEntity(Insurance.class)
                   .get();
        final Query lq = b.keyword().onField("name").matching("Macif").createQuery();
        final FullTextQuery ftq = fts.createFullTextQuery(lq, Insurance.class);
        ftq.initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID);
        final List<Insurance> results = ftq.list();

        Assertions.assertThat(results).hasSize(1);
        for (Insurance o : results)
            fts.delete(o);

        transaction.commit();
        fts.close();
    }
}