package com.licky.elasticsearch.common;

import com.licky.elasticsearch.config.ESConfig;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.xml.crypto.Data;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class ESClient {

  private RestHighLevelClient restHighLevelClient;

  /**
   * 删除索引库
   */
  public void delIndex() throws IOException {
    // 操作索引的对象
    IndicesClient indices = restHighLevelClient.indices();
    // 删除索引的请求
    DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("ysx_course");
    // 删除索引
    DeleteIndexResponse response = indices.delete(deleteIndexRequest);
    // 得到响应
    boolean b = response.isAcknowledged();
    System.out.println(b);
  }

  public void addIndex() throws IOException {
    // 操作索引的对象
    IndicesClient indices = restHighLevelClient.indices();
    // 创建索引的请求
    CreateIndexRequest request = new CreateIndexRequest("ysx_course");
    request.settings(
        Settings.builder().put("number_of_shards", "1").put("number_of_replicas", "0"));
    // 创建映射
    request.mapping("doc", "{\n" +
        "                \"properties\": {\n" +
        "                    \"description\": {\n" +
        "                        \"type\": \"text\",\n" +
        "                        \"analyzer\": \"ik_max_word\",\n" +
        "                        \"search_analyzer\": \"ik_smart\"\n" +
        "                    },\n" +
        "                    \"name\": {\n" +
        "                        \"type\": \"text\",\n" +
        "                        \"analyzer\": \"ik_max_word\",\n" +
        "                        \"search_analyzer\": \"ik_smart\"\n" +
        "                    },\n" +
        "\"pic\":{                    \n" +
        "\"type\":\"text\",                        \n" +
        "\"index\":false                        \n" +
        "},                    \n" +
        "                    \"price\": {\n" +
        "                        \"type\": \"float\"\n" +
        "                    },\n" +
        "                    \"studymodel\": {\n" +
        "                        \"type\": \"keyword\"\n" +
        "                    },\n" +
        "                    \"timestamp\": {\n" +
        "                        \"type\": \"date\",\n" +
        "                        \"format\": \"yyyy-MM‐dd HH:mm:ss||yyyy‐MM‐dd||epoch_millis\"\n" +
        "                    }\n" +
        "                }\n" +
        "            }", XContentType.JSON);
    // 执行创建操作
    CreateIndexResponse response = indices.create(request);
    // 得到响应
    boolean b = response.isAcknowledged();
    System.out.println(b);
  }

  /**
   *
   * @throws IOException
   * @throws ParseException
   */
  public void SearchAll() throws IOException, ParseException {
    // 搜索请求对象
    SearchRequest searchRequest = new SearchRequest("ysx_course");
    // 指定类型
    searchRequest.types("doc");
    // 搜索源构建对象
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    // 搜索方式
    // matchAllQuery搜索全部
    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
    // 设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
    searchSourceBuilder.fetchSource(new String[] {"name", "studymodel", "price", "timestamp"},
        new String[] {});
    // 向搜索请求对象中设置搜索源
    searchRequest.source(searchSourceBuilder);
    // 执行搜索,向ES发起http请求
    SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
    // 搜索结果
    SearchHits hits = searchResponse.getHits();
    // 匹配到的总记录数
    long totalHits = hits.getTotalHits();
    // 得到匹配度高的文档
    SearchHit[] searchHits = hits.getHits();
    // 日期格式化对象
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    for (SearchHit hit : searchHits) {
      // 文档的主键
      String id = hit.getId();
      // 源文档内容
      Map<String, Object> sourceAsMap = hit.getSourceAsMap();
      String name = (String) sourceAsMap.get("name");
      // 由于前边设置了源文档字段过虑，这时description是取不到的
      String description = (String) sourceAsMap.get("description");
      // 学习模式
      String studymodel = (String) sourceAsMap.get("studymodel");
      // 价格
      Double price = (Double) sourceAsMap.get("price");
      // 日期
      Date timestamp = dateFormat.parse((String) sourceAsMap.get("timestamp"));
      System.out.println(name);
      System.out.println(studymodel);
      System.out.println("你看不见我，看不见我~" + description);
      System.out.println(price);
    }
  }
}
