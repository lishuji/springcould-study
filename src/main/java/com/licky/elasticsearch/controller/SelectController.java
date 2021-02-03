package com.licky.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.licky.elasticsearch.config.ElasticsearchConfig;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class SelectController {

  private static final String INDEX_PAGEACCESS = "my_index";

  @Resource
  ElasticsearchConfig elasticsearchConfig;

  @ResponseBody
  @RequestMapping(value = "/es/select", method = RequestMethod.GET)
  public ResponseEntity select() {
    try {
      RestHighLevelClient client = elasticsearchConfig.getClient();
      SearchRequest searchRequest = new SearchRequest(INDEX_PAGEACCESS);
      SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
      sourceBuilder.size(0);

      //字段值项分组聚合
      //TermsAggregationBuilder aggregation = AggregationBuilders.terms("sex")
      //    .field("method.keyword")
      //    .order(BucketOrder.aggregation("count", true));
      //aggregation.subAggregation(AggregationBuilders.count("count").field("_sessionid"));
      //sourceBuilder.aggregation(aggregation);
      //searchRequest.source(sourceBuilder);

      SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
      if (searchResponse.status() != RestStatus.OK) throw new SerialException("ES search Error");
      if (searchResponse.getHits().getTotalHits() <= 0) {
        throw new SerialException("data not found");
      }
      SearchHits hits = searchResponse.getHits();
      ArrayList response = new ArrayList();
      for (SearchHit hit : hits) {
        Map<String,Object> source = hit.getSourceAsMap();
        response.add(source);
      }

      return ResponseEntity.ok(response);

      //if (RestStatus.OK.equals(searchResponse.status())) {
      //  // 获取聚合结果
      //  Aggregations aggregations = searchResponse.getAggregations();
      //  Terms byAgeAggregation = aggregations.get("by_method");
      //  for (Terms.Bucket buck : byAgeAggregation.getBuckets()) {
      //    System.out.println("key: " + buck.getKeyAsString());
      //    System.out.println("docCount: " + buck.getDocCount());
      //    System.out.println("docCountError: " + buck.getDocCountError());
      //    //取子聚合
      //    ParsedValueCount averageBalance = buck.getAggregations().get("count");
      //    System.out.println("average_balance: " + averageBalance.getValue());
      //  }
        //直接用key 来去分组
        /*Bucket elasticBucket = byCompanyAggregation.getBucketByKey("24");
        Avg averageAge = elasticBucket.getAggregations().get("average_age");
        double avg = averageAge.getValue();*/
      //}
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
