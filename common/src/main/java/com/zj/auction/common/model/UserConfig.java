package com.zj.auction.common.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserConfig implements Serializable {

    private Long userId;

    private Integer userConfigId;

    private String addr;

    private String domainName;

    private String province;

    private String city;

    private String county;

    private Long provinceId;

    private Long cityId;

    private Long countyId;

    private String frontImage;

    private String reverseImage;

    private LocalDateTime applyTime;

    private String faceImg;

    private String enteringStatus;

    private Date createTime;
    private Date updateTime;

    private static final long serialVersionUID = 1L;


}