package com.licky.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.licky.elasticsearch.config.ESProps;
import com.licky.elasticsearch.config.ElasticsearchConfig;
import com.licky.elasticsearch.exceptions.AppException;
import com.licky.elasticsearch.models.UserInfo;
import com.licky.elasticsearch.utils.ResultUtils;
import com.licky.elasticsearch.utils.ValidationUtils;
import java.io.IOException;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
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

  @ResponseBody
  @RequestMapping(value = "/es/insert", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<ResultUtils> insertData(@NotNull(message = "请求参数不能为空") @RequestBody UserInfo userInfo) {
    try {
      ValidationUtils.validate(userInfo); //参数校验
      IndexRequest indexRequest =
          new IndexRequest(esProps.getLocal_es_index(), esProps.getLocal_es_type());
      indexRequest.source(userInfo, XContentType.JSON);
      elasticsearchConfig.getClient().index(indexRequest);
      return ResponseEntity.ok(
          ResultUtils.builder().code(200).message("success").data(null).build());
    } catch (IOException | AppException e) {
      return ResponseEntity.ok(
          ResultUtils.builder().code(522).message(e.getMessage()).data(null).build());
    }
  }
}
