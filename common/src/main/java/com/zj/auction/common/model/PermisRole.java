package com.zj.auction.common.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
@TableName("zj_permis_role")
public class PermisRole implements Serializable {
    private Long rolePermisId;

    private Long roleId;

    private Long permisId;

    private static final long serialVersionUID = 1L;

    public Long getRolePermisId() {
        return rolePermisId;
    }

    public void setRolePermisId(Long rolePermisId) {
        this.rolePermisId = rolePermisId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermisId() {
        return permisId;
    }

    public void setPermisId(Long permisId) {
        this.permisId = permisId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rolePermisId=").append(rolePermisId);
        sb.append(", roleId=").append(roleId);
        sb.append(", permisId=").append(permisId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}