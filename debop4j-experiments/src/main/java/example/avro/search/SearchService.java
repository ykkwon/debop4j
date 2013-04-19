/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package example.avro.search;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public interface SearchService {
    public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"SearchService\",\"namespace\":\"example.avro.search\",\"types\":[{\"type\":\"enum\",\"name\":\"SearchMethod\",\"symbols\":[\"SIMPLE\",\"EXACT\",\"FUZZY\"]},{\"type\":\"record\",\"name\":\"Entity\",\"fields\":[{\"name\":\"rowId\",\"type\":\"string\"},{\"name\":\"createdAt\",\"type\":\"string\"},{\"name\":\"text\",\"type\":\"string\"},{\"name\":\"attrs\",\"type\":{\"type\":\"map\",\"values\":\"string\"}}]},{\"type\":\"record\",\"name\":\"SearchResult\",\"fields\":[{\"name\":\"pageNo\",\"type\":\"int\"},{\"name\":\"pageSize\",\"type\":\"int\"},{\"name\":\"pageCount\",\"type\":\"int\"},{\"name\":\"totalItemCount\",\"type\":\"int\"},{\"name\":\"entities\",\"type\":{\"type\":\"array\",\"items\":\"Entity\",\"java_class\":\"java.util.ArrayList\"}}]},{\"type\":\"error\",\"name\":\"PersistException\",\"fields\":[{\"name\":\"message\",\"type\":\"string\"},{\"name\":\"code\",\"type\":\"int\",\"default\":-1}]}],\"messages\":{\"ping\":{\"request\":[],\"response\":\"string\"},\"persist\":{\"request\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"entity\",\"type\":\"Entity\"}],\"response\":\"int\",\"errors\":[\"PersistException\"]},\"persistAll\":{\"request\":[{\"name\":\"entities\",\"type\":{\"type\":\"array\",\"items\":\"Entity\"}}],\"response\":\"int\",\"errors\":[\"PersistException\"]},\"search\":{\"request\":[{\"name\":\"queryString\",\"type\":\"string\"},{\"name\":\"pageNo\",\"type\":\"int\",\"default\":1},{\"name\":\"pageSize\",\"type\":\"int\",\"default\":10}],\"response\":\"SearchResult\"},\"searchByMethod\":{\"request\":[{\"name\":\"queryString\",\"type\":\"string\"},{\"name\":\"searchMethod\",\"type\":\"SearchMethod\"},{\"name\":\"pageNo\",\"type\":\"int\",\"default\":1},{\"name\":\"pageSize\",\"type\":\"int\",\"default\":10}],\"response\":\"SearchResult\"}}}");

    java.lang.CharSequence ping() throws org.apache.avro.AvroRemoteException;

    int persist(java.lang.CharSequence id, example.avro.search.Entity entity) throws org.apache.avro.AvroRemoteException, example.avro.search.PersistException;

    int persistAll(java.util.List<example.avro.search.Entity> entities) throws org.apache.avro.AvroRemoteException, example.avro.search.PersistException;

    example.avro.search.SearchResult search(java.lang.CharSequence queryString, int pageNo, int pageSize) throws org.apache.avro.AvroRemoteException;

    example.avro.search.SearchResult searchByMethod(java.lang.CharSequence queryString, example.avro.search.SearchMethod searchMethod, int pageNo, int pageSize) throws org.apache.avro.AvroRemoteException;

    @SuppressWarnings("all")
    public interface Callback extends SearchService {
        public static final org.apache.avro.Protocol PROTOCOL = example.avro.search.SearchService.PROTOCOL;

        void ping(org.apache.avro.ipc.Callback<java.lang.CharSequence> callback) throws java.io.IOException;

        void persist(java.lang.CharSequence id, example.avro.search.Entity entity, org.apache.avro.ipc.Callback<java.lang.Integer> callback) throws java.io.IOException;

        void persistAll(java.util.List<example.avro.search.Entity> entities, org.apache.avro.ipc.Callback<java.lang.Integer> callback) throws java.io.IOException;

        void search(java.lang.CharSequence queryString, int pageNo, int pageSize, org.apache.avro.ipc.Callback<example.avro.search.SearchResult> callback) throws java.io.IOException;

        void searchByMethod(java.lang.CharSequence queryString, example.avro.search.SearchMethod searchMethod, int pageNo, int pageSize, org.apache.avro.ipc.Callback<example.avro.search.SearchResult> callback) throws java.io.IOException;
    }
}