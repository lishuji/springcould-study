package com.licky.elasticsearch.common;

import com.licky.elasticsearch.config.ESConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESClient {

  @Resource
  ESConfig esConfig;

  public ESConfig getEsConfig() {
    return esConfig;
  }

  @Bean(name = "restHighLevelClient")
  public RestHighLevelClient restHighLevelClient() {
    List<HttpHost> hostLists = new ArrayList<>();// 拆分地址
    String[] hostList = esConfig.address.split(",");
    for (String addr : hostList) {
      String host = addr.split(":")[0];
      String port = addr.split(":")[1];
      hostLists.add(new HttpHost(host, Integer.parseInt(port), esConfig.schema));
    }
    // 转换成 HttpHost 数组
    HttpHost[] httpHost = hostLists.toArray(new HttpHost[] {});
    // 构建连接对象
    RestClientBuilder builder = RestClient.builder(httpHost);
    // 异步连接延时配置
    builder.setRequestConfigCallback(requestConfigBuilder -> {
      requestConfigBuilder.setConnectTimeout(esConfig.connectTimeout);
      requestConfigBuilder.setSocketTimeout(esConfig.socketTimeout);
      requestConfigBuilder.setConnectionRequestTimeout(esConfig.connectionRequestTimeout);
      return requestConfigBuilder;
    });
    // 异步连接数配置
    builder.setHttpClientConfigCallback(httpClientBuilder -> {
      httpClientBuilder.setMaxConnTotal(esConfig.maxConnectNum);
      httpClientBuilder.setMaxConnPerRoute(esConfig.maxConnectPerRoute);
      return httpClientBuilder;
    });
    return new RestHighLevelClient(builder);
  }

  /**
   * 创建索引
   */
  public void createIndex() {
    try {
      // 创建 Mapping
      XContentBuilder mapping = XContentFactory.jsonBuilder()
          .startObject()
          .field("dynamic", true)
          .startObject("properties")
          .startObject("name")
          .field("type", "text")
          .startObject("fields")
          .startObject("keyword")
          .field("type", "keyword")
          .endObject()
          .endObject()
          .endObject()
          .startObject("address")
          .field("type", "text")
          .startObject("fields")
          .startObject("keyword")
          .field("type", "keyword")
          .endObject()
          .endObject()
          .endObject()
          .startObject("remark")
          .field("type", "text")
          .startObject("fields")
          .startObject("keyword")
          .field("type", "keyword")
          .endObject()
          .endObject()
          .endObject()
          .startObject("age")
          .field("type", "integer")
          .endObject()
          .startObject("salary")
          .field("type", "float")
          .endObject()
          .startObject("birthDate")
          .field("type", "date")
          .field("format", "yyyy-MM-dd")
          .endObject()
          .startObject("createTime")
          .field("type", "date")
          .endObject()
          .endObject()
          .endObject();
      // 创建索引配置信息，配置
      Settings settings = Settings.builder()
          .put("index.number_of_shards", 1)
          .put("index.number_of_replicas", 0)
          .build();
      // 新建创建索引请求对象，然后设置索引类型（ES 7.0 将不存在索引类型）和 mapping 与 index 配置
      CreateIndexRequest request = new CreateIndexRequest("mydlq-user", settings);
      request.mapping("doc", mapping);
      // RestHighLevelClient 执行创建索引
      CreateIndexResponse createIndexResponse =
          restHighLevelClient().indices().create(request);
      // 判断是否创建成功
      boolean isCreated = createIndexResponse.isAcknowledged();
    } catch (IOException e) {
    }
  }
}
