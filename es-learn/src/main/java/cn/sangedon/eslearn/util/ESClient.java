package cn.sangedon.eslearn.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author dongliangqiong
 */
public class ESClient {
    public static RestHighLevelClient getClient() {
        HttpHost httpHost = new HttpHost("localhost", 9200);
        RestClientBuilder clientBuilder = RestClient.builder(httpHost);
        return new RestHighLevelClient(clientBuilder);
    }
}
