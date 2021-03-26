package com.licky.elasticsearch.utils;

import com.licky.elasticsearch.exceptions.AppException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.hibernate.validator.HibernateValidator;

public class ValidationUtils {

  /**
   * 使用hibernate的注解来进行验证
   */
  private static Validator validator = Validation
      .byProvider(HibernateValidator.class)
      .configure()
      .failFast(true)
      .buildValidatorFactory()
      .getValidator();

  public static <T> void validate(T obj) throws AppException {
    Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
    // 抛出检验异常
    if (constraintViolations.size() > 0) {
      throw new AppException(
          String.format("参数校验失败:%s", constraintViolations.iterator().next().getMessage()));
    }
  }
}
