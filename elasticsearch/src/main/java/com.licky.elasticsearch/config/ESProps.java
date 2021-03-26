package com.licky.elasticsearch.config;

import java.text.SimpleDateFormat;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "spring.data.elasticsearch")
@Data
public class ESProps {

  private String ip;

  private Integer port;

  private String scheme;

  private String local_es_index = "user";

  private String local_es_type = "doc";
}
