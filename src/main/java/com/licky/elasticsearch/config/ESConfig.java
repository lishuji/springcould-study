package com.licky.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfig {
  @Value("${elasticsearch.host}")
  private String host; // 127.0.0.1:9200

  @Bean // 高版本客户端
  public RestHighLevelClient restHighLevelClient() {
    // 解析 hostlist 配置信息。假如以后有多个，则需要用 ， 分开
    String[] split = host.split(",");

    // 创建 HttpHost 数组，其中存放es主机和端口的配置信息
    HttpHost[] httpHostArray = new HttpHost[split.length];
    for (int i = 0; i < split.length; i++) {
      String item = split[i];
      httpHostArray[i] =
          new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
    }

    return new RestHighLevelClient(RestClient.builder(httpHostArray));// 创建RestHighLevelClient客户端
  }

  @Bean
  public RestClient restClient() {
    String[] split = host.split(",");
    // 创建HttpHost数组，其中存放es主机和端口的配置信息
    HttpHost[] httpHostArray = new HttpHost[split.length];
    for (int i = 0; i < split.length; i++) {
      String item = split[i];
      httpHostArray[i] =
          new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
    }
    return RestClient.builder(httpHostArray).build();
  }
}
