package com.zj.auction.common.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Role implements Serializable {
    private Long roleId;

    private String roleName;

    private String description;

    private Integer bePermissible;

    private Integer status;

    private Integer deleteFlag;

    private Long addUserid;

    private String roleCode;

    private LocalDateTime addTime;

    private LocalDateTime updateTime;

    private Integer pid;

    private Integer levelNum;

    private String pidStr;

    private List<Permis> permisList;

    private static final long serialVersionUID = 1L;


}