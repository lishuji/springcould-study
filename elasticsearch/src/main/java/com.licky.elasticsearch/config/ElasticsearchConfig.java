package com.licky.elasticsearch.config;

import javax.annotation.Resource;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

  @Resource
  ESProps esProps;

  @Bean
  public RestHighLevelClient getClient() {
    String ip = esProps.getIp();
    Integer port = esProps.getPort();
    String scheme = esProps.getScheme();
    return new RestHighLevelClient(RestClient.builder(new HttpHost(ip, port, scheme)));
  }
}