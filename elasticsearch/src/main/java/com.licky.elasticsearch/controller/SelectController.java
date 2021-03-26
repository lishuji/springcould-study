package com.licky.elasticsearch.controller;

import com.licky.elasticsearch.config.ESProps;
import com.licky.elasticsearch.config.ElasticsearchConfig;
import com.licky.elasticsearch.utils.ResultUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
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
  @RequestMapping(value = "/es/list", method = RequestMethod.GET)
  public ResponseEntity<ResultUtils> select() {
    try {
      RestHighLevelClient client = elasticsearchConfig.getClient();
      SearchRequest searchRequest = new SearchRequest(esProps.getLocal_es_index());
      SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
      SearchHits hits = searchResponse.getHits();
      Map list = Arrays.stream(hits.getHits()).collect(Collectors.groupingBy(SearchHit::getIndex));
      return ResponseEntity.ok(
          ResultUtils.builder().code(200).message("ok").data(list).build());
    } catch (Exception e) {
      return ResponseEntity.ok(
          ResultUtils.builder().code(522).message(e.getMessage()).data(null).build());
    }
  }
}
