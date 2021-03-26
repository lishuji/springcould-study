package com.licky.elasticsearch.utils;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultUtils implements Serializable {

  private static final long serialVersionUID = -1802122468331526708L;
  private int code = -1;
  private String message = "待处理";
  private Object data = null;

  public ResultUtils(int code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }
}
