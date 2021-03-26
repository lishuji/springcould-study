package com.licky.elasticsearch.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataException extends Exception {

  static final Logger logger = LoggerFactory.getLogger(DataException.class);

  public DataException(String message, Throwable throwable) {
    super(message, throwable);
    logger.error("DataException : " + message, throwable);
  }
}