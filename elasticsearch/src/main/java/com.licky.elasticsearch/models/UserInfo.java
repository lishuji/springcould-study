package com.licky.elasticsearch.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserInfo {

  @NotBlank(message = "用户唯一ID不能为空")
  private String uuid;

  @NotBlank(message = "用户名称不能为空")
  private String userName;

  @NotBlank(message = "用户年龄不能为空")
  @Min(value = 0,message = "年龄不能小于0")
  private float age;

  @NotNull(message = "性别不能为空")
  @Pattern(regexp = "1,2",message = "性别只能为1或2，1：男；2：女")
  private int sex;

  /**
   * 创建时间
   */
  private Long createdDate;

  /**
   * 更新时间
   */
  private Long updatedData;
}
