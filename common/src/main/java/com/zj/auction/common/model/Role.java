package com.zj.auction.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zj.auction.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("zj_role")
public class Role extends BaseEntity implements Serializable {
    @ApiModelProperty(value = "角色id")
    private Long roleId;


    @ApiModelProperty(value = "角色名称")
    private String roleName;


    @ApiModelProperty(value = "描述")
    private String description;


    @ApiModelProperty(value = "是否允许授权给用户,0不允许1允许")
    private Integer bePermissible;



    @ApiModelProperty(value = "0正常1禁用")
    private Integer status;


    @ApiModelProperty(value = "角色id")
    private String roleCode;

    @ApiModelProperty(value = "父id")
    private Integer pid;

    @ApiModelProperty(value = "等级")
    private Integer levelNum;

    @ApiModelProperty(value = "所有父级")
    private String pidStr;

    @ManyToMany(cascade = CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinTable(name = "zj_permis_role", joinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName="role_id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "permis_id", referencedColumnName = "permis_id")
            })
    @TableField(exist = false)
    private List<Permis> permisList;


    private static final long serialVersionUID = 1L;


}