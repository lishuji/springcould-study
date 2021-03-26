package com.licky.elasticsearch.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppException extends Exception {
  static final Logger logger = LoggerFactory.getLogger(AppException.class);

  public AppException(String message) {
    super(message);
    logger.warn("AppException :" + message);
  }
}
