package com.licky.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.licky.elasticsearch.config.ESProps;
import com.licky.elasticsearch.config.ElasticsearchConfig;
import com.licky.elasticsearch.models.UserInfo;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Objects;
import javax.annotation.Resource;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class InsertController {

  @Resource
  ElasticsearchConfig elasticsearchConfig;

  @Resource
  ESProps esProps;

  @RequestMapping(value = "/es/insert", method = RequestMethod.POST)
  public boolean insertData(@RequestBody String content) throws ServerException {
    //UserInfo userInfo = JSON.parseObject(content, UserInfo.class);
    try {
      IndexRequest indexRequest =
          new IndexRequest(esProps.getLocal_es_index(), esProps.getLocal_es_type());
      indexRequest.source(content, XContentType.JSON);
      elasticsearchConfig.getClient().index(indexRequest);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}
