package com.zj.auction.common.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    private static final long serialVersionUID = 1L;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBePermissible() {
        return bePermissible;
    }

    public void setBePermissible(Integer bePermissible) {
        this.bePermissible = bePermissible;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getAddUserid() {
        return addUserid;
    }

    public void setAddUserid(Long addUserid) {
        this.addUserid = addUserid;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public String getPidStr() {
        return pidStr;
    }

    public void setPidStr(String pidStr) {
        this.pidStr = pidStr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleId=").append(roleId);
        sb.append(", roleName=").append(roleName);
        sb.append(", description=").append(description);
        sb.append(", bePermissible=").append(bePermissible);
        sb.append(", status=").append(status);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", addUserid=").append(addUserid);
        sb.append(", roleCode=").append(roleCode);
        sb.append(", addTime=").append(addTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", pid=").append(pid);
        sb.append(", levelNum=").append(levelNum);
        sb.append(", pidStr=").append(pidStr);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}