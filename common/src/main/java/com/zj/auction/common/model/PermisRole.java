package com.zj.auction.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("zj_permis_role")
public class PermisRole implements Serializable {
    private Long rolePermisId;

    private Long roleId;

    private Long permisId;

    private static final long serialVersionUID = 1L;

}