package com.zj.auction.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
@Data
@TableName("zj_permis")
public class Permis implements Serializable {
    
    private Long permisId;

    private String permisName;

    private String permis;

    private Integer levelNum;

    private Integer status;

    private Long pid;

    private String resUrl;

    private Integer typeId;

    private Integer deleteFlag;

    private Integer orderIndex;

    private String imgPath;

    private String memo;

}