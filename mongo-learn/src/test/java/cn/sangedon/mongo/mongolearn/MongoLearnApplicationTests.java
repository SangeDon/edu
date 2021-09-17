package cn.sangedon.mongo.mongolearn;

import cn.sangedon.mongo.mongolearn.pojo.User;
import cn.sangedon.mongo.mongolearn.util.MongoUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.jws.soap.SOAPBinding.Use;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MongoLearnApplicationTests {

    private static final String DP = "dp";

    private static final String COL_FORM_DATA = "col_form_data";

    private MongoCollection<Document> collection;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void pre() {
        MongoDatabase mongoDatabase = MongoUtil.getDataBase(DP);
        collection = mongoDatabase.getCollection(COL_FORM_DATA);
    }

    @Test
    void testQuery() {
        FindIterable<Document> documents = collection
            .find(Filters.eq("form_id", "fm_60aca50f16a6104a7f676182"));

        MongoCursor<Document> iterator = documents.iterator();
        Integer count = 0;
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            count++;
        }
        System.out.println("count: " + count);
    }

    @Test
    void testAggregates() {

        BasicDBObject mongoFilter = new BasicDBObject("form_id", "fm_60aca50f16a6104a7f676182");
        BasicDBObject match = new BasicDBObject("$match", mongoFilter);

//        //2.有个project处理  如果需要可以实现
//        //2.group处理
        BasicDBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "$application")
            .append("count", new BasicDBObject("$sum", 1)));

        List<BasicDBObject> aggregateList = new ArrayList<>();
        aggregateList.add(match);
        aggregateList.add(group);

        BasicDBObject limit = new BasicDBObject("$limit", 2);
        BasicDBObject skip = new BasicDBObject("$skip", 0);
        aggregateList.add(skip);
        aggregateList.add(limit);

        AggregateIterable<Document> resultset = collection.aggregate(aggregateList);
        MongoCursor<Document> cursor = resultset.iterator();
        Set<Document> objects = new HashSet<>();
        try {
            while (cursor.hasNext()) {
                Document itemDoc = cursor.next();
                Object id = itemDoc.get("_id");
                objects.add(itemDoc);
            }
        } finally {
            cursor.close();
        }
    }

    @Test
    void testAggregate() {
        Document match = new Document();
        match.put("form_id", "fm_60aca50f16a6104a7f676182");

        Document id = new Document();
        id.put("application", "$application");

        Document sum = new Document();
        sum.put("$sum", 1);

        Document group = new Document();
        group.put("_id", id);
        group.put("number", sum);

        List<Document> list = new ArrayList<>();
        list.add(match);
        list.add(group);
        AggregateIterable documents = collection.aggregate(list);
        MongoCursor cursor = documents.iterator();
        int counts = 0;
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
            counts++;
        }
        System.out.println(counts);
    }

    @Test
    public void testObjectMapper() throws Exception {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        User user = new User(1, "Hello World", new Date());

        String jsonStr = objectMapper.writeValueAsString(user);

        User userDe = objectMapper.readValue(jsonStr, User.class);
    }
}
