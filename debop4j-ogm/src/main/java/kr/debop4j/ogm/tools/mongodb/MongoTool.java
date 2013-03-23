package kr.debop4j.ogm.tools.mongodb;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.ogm.datastore.mongodb.impl.MongoDBDatastoreProvider;
import org.hibernate.ogm.datastore.spi.DatastoreProvider;
import org.hibernate.ogm.datastore.spi.Tuple;
import org.hibernate.ogm.datastore.spi.TupleContext;
import org.hibernate.ogm.dialect.GridDialect;
import org.hibernate.ogm.dialect.mongodb.MongoDBDialect;
import org.hibernate.ogm.grid.EntityKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * kr.debop4j.ogm.tools.mongodb.MongoTool
 *
 * @author sunghyouk.bae@gmail.com
 *         13. 3. 23. 오후 6:13
 */
@Component
@Slf4j
public class MongoTool {

    @Autowired
    GridDialect gridDialect;

    @Autowired
    DatastoreProvider datastoreProvider;


    public Tuple getTuple(String collectionName, String id, List<String> selectedColumns) {
        EntityKey key = new EntityKey(collectionName,
                                      new String[]{MongoDBDialect.ID_FIELDNAME},
                                      new Object[]{id});
        TupleContext tupleContext = new TupleContext(selectedColumns);
        return gridDialect.getTuple(key, tupleContext);
    }

    public MongoDBDatastoreProvider getProvider() {
        return (MongoDBDatastoreProvider) datastoreProvider;
    }
}