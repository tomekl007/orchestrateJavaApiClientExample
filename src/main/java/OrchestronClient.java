import io.orchestrate.client.*;

/**
 * @author Tomasz Lelek
 * @since 2014-04-18
 */
public class OrchestronClient {

    public static void main(String[] args) {
        Client client = OrchestrateClient.builder("putMyApiKeyHere").build();

        Person tomek = new Person("Tomek", 22); // a POJO
        Person marcin = new Person("Marcin", 23);


        //put
        String collectionName = "Persons";
        String tomekKey = "Tomek_L";
        String marcinKey = "Marcin_K";

        final KvMetadata kvMetadata =
                client.kv(collectionName, tomekKey)
                        .put(tomek)
                        .get();
        System.out.println(kvMetadata);
        client.kv(collectionName, marcinKey)
                .put(marcin);

        //get
        KvObject<Person> object =
                client.kv(collectionName, tomekKey)
                        .get(Person.class)
                        .get();
        System.out.println("get : " + object);
        String ref = object.getRef();

        //conditional put, important for keeping data consistency
        // An If-Match PUT
        tomek.setAge(30);
        KvMetadata kvMetadata1 =
                client.kv(collectionName, tomekKey)
                        .ifMatch(ref)
                        .put(tomek)
                        .get();
        System.out.println("after ifMatch : " + kvMetadata1);

        // An If-None-Match PUT
       /* tomek.setAge(40);
        KvMetadata kvMetadata2 =
                client.kv(collectionName, tomekKey)
                        .ifAbsent()
                        .put(tomek)
                        .get();
        System.out.println("after ifAbsent : " + kvMetadata2);      */

        //list
        KvList<Person> kvList =
                client.listCollection(collectionName)
                        .limit(20)
                        .get(Person.class)
                        .get();
        System.out.println(kvList);

        //lucene query
        String luceneQuery = "*";
        SearchResults<Person> results =
                client.searchCollection(collectionName)
                        .limit(20)
                        .get(Person.class, luceneQuery)
                        .get();
        System.out.println("\nlucene result : ");
        System.out.println(results);

        //event
        Twitt twitt = new Twitt("he is really smart ! "); // a POJO
        boolean result =
                client.event(collectionName, tomekKey)
                        .type("twitter")
                        .put(twitt)
                        .get();
        System.out.println(result);

        //relations
        String relationName = "friends";
        boolean resultRelation =
                client.relation(collectionName, tomekKey)
                        .to(collectionName, marcinKey)
                        .put(relationName)
                        .get();
        System.out.println("relation created : " + resultRelation);

        // One hop
        Iterable<KvObject<Person>> resultsRelation =
                client.relation(collectionName, tomekKey)
                        .get(Person.class, relationName)
                        .get();
        resultsRelation.forEach(System.out::println);



    }
}
