package com.licky.elasticsearch.models;

import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
public class UserInfo {

  @NotBlank(message = "用户唯一ID不能为空")
  private String uuid;

  @NotBlank(message = "用户名称不能为空")
  private String userName;

  @NotNull(message = "用户年龄不能为空")
  @Min(value = 0, message = "年龄不能小于0")
  private float age;

  @NotNull(message = "性别不能为空")
  @Range(min = 1, max = 2,message = "性别只能为1或2，1：男；2：女")
  private int sex;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createdDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updatedData;
}
