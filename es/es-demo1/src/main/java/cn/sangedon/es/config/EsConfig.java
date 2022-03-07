package cn.sangedon.es.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("es")
public class EsConfig {
    private String hostlist;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        String[] hosts = hostlist.split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        int index = 0;
        for (String host : hosts) {
            HttpHost http = new HttpHost(host.split(":")[0], Integer.parseInt(host.split(":")[1]), "http");
            httpHosts[index++] = http;
        }
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}
