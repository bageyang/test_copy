package com.zj.auction.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("zj_role")
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