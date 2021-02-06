package com.licky.elasticsearch.utils;

import com.licky.elasticsearch.config.ESProps;
import java.io.IOException;
import javax.annotation.Resource;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class ESUtil {
  private static RestHighLevelClient restHighLevelClient;

  @Resource
  public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
    ESUtil.restHighLevelClient = restHighLevelClient;
  }

  @Resource
  ESProps esProps;

  /**
   * 创建ES映射关系
   *
   * @return
   * @throws IOException
   */
  private static XContentBuilder creatDataBuilder() throws IOException {
    return JsonXContent.contentBuilder()
        .startObject()
        .startObject("mappings")
        .startObject("doc")
        .startObject("properties")
        .startObject("id").field("type", "keyword").endObject()
        .startObject("userName").field("type", "text").endObject()
        .startObject("age").field("type", "integer").endObject()
        .startObject("sex").field("type", "integer").endObject()
        .startObject("createdDate").field("type", "long").endObject()
        .startObject("updatedData").field("type", "long").endObject()
        .endObject()
        .endObject()
        .endObject();
  }

  /**
   * 创建ES索引
   *
   * @throws IOException
   */
  private void createIndex() throws IOException {
    CreateIndexRequest createIndexRequest = new CreateIndexRequest(esProps.getLocal_es_index());
    //创建的每个索引都可以有与之关联的特定设置。
    createIndexRequest.settings(Settings.builder()
        .put("index.number_of_shards", 3)
        .put("index.number_of_replicas", 2)
    );
    //为索引设置一个别名
    createIndexRequest.alias(new Alias("user_info"));

    //超时,等待所有节点被确认
    createIndexRequest.timeout(TimeValue.timeValueMinutes(2));
    //createIndexRequest.timeout("2m");

    //连接master节点的超时时间
    createIndexRequest.masterNodeTimeout(TimeValue.timeValueMinutes(1));

    //同步执行
    CreateIndexResponse response = restHighLevelClient.indices().create(createIndexRequest);
  }
}
