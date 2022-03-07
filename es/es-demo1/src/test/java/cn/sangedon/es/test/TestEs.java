package cn.sangedon.es.test;

import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEs {
    @Autowired
    RestHighLevelClient client;

    @Test
    public void createIndex() throws Exception {
        CreateIndexRequest indexRequest = new CreateIndexRequest("es_test");
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder().startObject().field("properties").startObject().field("name")
                                                         .startObject().field("type", "text").field("analyzer", "ik_smart").endObject()
                                                         .field("age").startObject().field("type", "keyword").endObject().endObject()
                                                         .endObject();
        indexRequest.mapping(xContentBuilder);
        IndicesClient indices = client.indices();
        CreateIndexResponse indexResponse = indices.create(indexRequest, RequestOptions.DEFAULT);
        boolean acknowledged = indexResponse.isAcknowledged();
        System.out.println("index result: " + acknowledged);
    }

    @Test
    public void deleteIndex() throws Exception {
        DeleteIndexRequest indexRequest = new DeleteIndexRequest("es_test");
        IndicesClient indices = client.indices();
        AcknowledgedResponse response = indices.delete(indexRequest, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        System.out.println("index result: " + acknowledged);
    }

    @Test
    public void addDoc() throws Exception {
        IndexRequest indexRequest = new IndexRequest("es_test");
        indexRequest.id("3");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "sangedon2 test");
        map.put("age", 15);
        indexRequest.source(map);

        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

        Result result = response.getResult();
        System.out.println("index result: " + result);
    }

    @Test
    public void query() throws Exception {
        GetRequest request = new GetRequest("es_test", "1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        Map<String, Object> sourceAsMap = response.getSourceAsMap();
        System.out.println(sourceAsMap);
    }

    @Test
    public void queryMatch() throws Exception {
        SearchRequest searchRequest = new SearchRequest("es_test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchSourceBuilder query = searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.fetchSource(new String[] {"name"}, new String[] {});
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        TotalHits totalHits = hits.getTotalHits();
        SearchHit[] hitsHits = hits.getHits();
        for (SearchHit hitsHit : hitsHits) {
            Map<String, Object> sourceAsMap = hitsHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    @Test
    public void queryTerm() throws Exception {
        SearchRequest searchRequest = new SearchRequest("es_test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchSourceBuilder query = searchSourceBuilder.query(QueryBuilders.termQuery("name", "test"));

        searchSourceBuilder.fetchSource(new String[] {"name"}, new String[] {});
        searchSourceBuilder.from(1);
        searchSourceBuilder.size(1);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        TotalHits totalHits = hits.getTotalHits();
        SearchHit[] hitsHits = hits.getHits();
        for (SearchHit hitsHit : hitsHits) {
            Map<String, Object> sourceAsMap = hitsHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }
}
