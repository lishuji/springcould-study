package com.licky.elasticsearch.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.data.elasticsearch")
@Data
public class ElasticsearchConfig {

  private String ip;
  private Integer port;
  private String scheme;

  public RestHighLevelClient getClient() {
    return new RestHighLevelClient(
        RestClient.builder(new HttpHost(ip, port, scheme)));
  }
}