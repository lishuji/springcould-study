package com.licky.elasticsearch.common;

import com.licky.elasticsearch.config.ESConfig;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ESClient {

    @Resource
    ESConfig esConfig;

    public ESConfig ESConfig() {
        return esConfig;
    }

    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient restHighLevelClient() {
        List<HttpHost> hostLists = new ArrayList<>();// 拆分地址
        String[] hostList = esConfig.address.split(",");
        for (String addr : hostList) {
            String host = addr.split(":")[0];
            String port = addr.split(":")[1];
            hostLists.add(new HttpHost(host, Integer.parseInt(port), esConfig.schema));
        }
        // 转换成 HttpHost 数组
        HttpHost[] httpHost = hostLists.toArray(new HttpHost[]{});
        // 构建连接对象
        RestClientBuilder builder = RestClient.builder(httpHost);
        // 异步连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(esConfig.connectTimeout);
            requestConfigBuilder.setSocketTimeout(esConfig.socketTimeout);
            requestConfigBuilder.setConnectionRequestTimeout(esConfig.connectionRequestTimeout);
            return requestConfigBuilder;
        });
        // 异步连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(esConfig.maxConnectNum);
            httpClientBuilder.setMaxConnPerRoute(esConfig.maxConnectPerRoute);
            return httpClientBuilder;
        });
        return new RestHighLevelClient(builder);
    }


}
