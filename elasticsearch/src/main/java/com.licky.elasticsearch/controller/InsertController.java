package com.licky.elasticsearch.controller;

import com.licky.elasticsearch.config.ESProps;
import com.licky.elasticsearch.config.ElasticsearchConfig;
import com.licky.elasticsearch.exceptions.AppException;
import com.licky.elasticsearch.models.UserInfo;
import com.licky.elasticsearch.utils.ResultUtils;
import com.licky.elasticsearch.utils.ValidationUtils;
import java.io.IOException;
import javax.annotation.Resource;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<ResultUtils> insertData(@RequestBody UserInfo userInfo)
      throws AppException {
    try {
      ValidationUtils.validate(userInfo); //参数校验
      IndexRequest indexRequest =
          new IndexRequest(esProps.getLocal_es_index(), esProps.getLocal_es_type());
      indexRequest.source(userInfo, XContentType.JSON);
      elasticsearchConfig.getClient().index(indexRequest);
      return ResponseEntity.ok(
          ResultUtils.builder().code(200).message("success").data(null).build());
    } catch (AppException | IOException e) {
      return ResponseEntity.ok(
          ResultUtils.builder().code(522).message(e.getMessage()).data(null).build());
    }
  }
}
