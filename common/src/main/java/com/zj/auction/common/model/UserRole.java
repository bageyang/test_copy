package com.zj.auction.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("zj_user_role")
public class UserRole implements Serializable {
    private Integer userRoleId;

    private Integer userId;

    private Integer roleId;

    private static final long serialVersionUID = 1L;


}