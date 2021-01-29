package com.licky.elasticsearch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfig {
  /**
   * 协议
   */
  @Value("${elasticsearch.schema:http}")
  public String schema;

  /**
   * 集群地址，如果有多个用“,”隔开
   */
  @Value("${elasticsearch.address}")
  public String address;

  /**
   * 连接超时时间
   */
  @Value("${elasticsearch.connectTimeout}")
  public int connectTimeout;

  /**
   * Socket 连接超时时间
   */
  @Value("${elasticsearch.socketTimeout}")
  public int socketTimeout;

  /**
   * 获取连接的超时时间
   */
  @Value("${elasticsearch.connectionRequestTimeout}")
  public int connectionRequestTimeout;

  /**
   * 最大连接数
   */
  @Value("${elasticsearch.maxConnectNum}")
  public int maxConnectNum;

  /**
   * 最大路由连接数
   */
  @Value("${elasticsearch.maxConnectPerRoute}")
  public int maxConnectPerRoute;
}
