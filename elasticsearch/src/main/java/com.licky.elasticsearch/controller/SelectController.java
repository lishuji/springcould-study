package com.licky.elasticsearch.controller;

import com.licky.elasticsearch.config.ESProps;
import com.licky.elasticsearch.config.ElasticsearchConfig;
import com.licky.elasticsearch.utils.ResultUtils;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class SelectController {

  @Resource
  ElasticsearchConfig elasticsearchConfig;

  @Resource
  ESProps esProps;

  @ResponseBody
  @RequestMapping(value = "/es/select", method = RequestMethod.GET)
  public ResponseEntity<ResultUtils> select() {
    try {
      RestHighLevelClient client = elasticsearchConfig.getClient();
      SearchRequest searchRequest = new SearchRequest(esProps.getLocal_es_index());
      //SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

      //TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("sex_count").field("sex");
      //sourceBuilder.aggregation(aggregationBuilder);
      //
      //ValueCountAggregationBuilder countAggregationBuilder = AggregationBuilders.count("sex_total").field("sex");
      //sourceBuilder.aggregation(countAggregationBuilder);
      //searchRequest.source(sourceBuilder);
      SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

      //Terms byAgeAggregation = searchResponse.getAggregations().get("sex_count");
      HashMap<String, String> response = new HashMap<>();
      //for (Terms.Bucket bucket : byAgeAggregation.getBuckets()) {
      //  response.put("性别" + bucket.getKeyAsString(), "数目" + bucket.getDocCount());
      //}
      //
      //ValueCount valueCount = searchResponse.getAggregations().get("sex_total");
      //Long value = valueCount.getValue();
      //response.put("总条数：", String.valueOf(value));
      Map<String, Object> resource = new HashMap<>();
      SearchHits hits = searchResponse.getHits();
      for (SearchHit hit : hits) {
        resource = hit.getSourceAsMap();
      }
      return ResponseEntity.ok(
          ResultUtils.builder().code(200).message("ok").data(resource).build());
    } catch (Exception e) {
      return ResponseEntity.ok(
          ResultUtils.builder().code(522).message(e.getMessage()).data(null).build());
    }
  }
}
