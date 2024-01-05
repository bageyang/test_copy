package com.zj.auction.common.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户表
 */
@TableName("zj_user")
public class User {
    private Long userId;

    /**
     * 当前管理员使用角色id
     */
    private Integer roleId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * md5加密带盐
     */
    private String salt;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 状态,0,正常，1,冻结
     */
    private Integer status;

    /**
     * 身份认证审核状态：0默认，1待审核，2审核通过，3拒绝审核
     */
    private Integer audit;

    /**
     * 审核不通过原因
     */
    private String auditExplain;

    /**
     * 父级id
     */
    private Long pid;

    /**
     * 父级名称
     */
    private String pUserName;

    /**
     * 所有父级
     */
    private String pidStr;

    /**
     * 推荐等级
     */
    private Integer levelNum;

    /**
     * 电话
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 当前用户类型
     */
    private Integer currentUserType;

    /**
     * 交易密码
     */
    private String payPassword;

    /**
     * 部门id
     */
    private Integer departmentId;

    /**
     * 添加时间
     */
    private LocalDateTime addTime;

    /**
     * 创建人
     */
    private Long addUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private Long updateUserId;

    /**
     * 冻结说明
     */
    private String frozenExplain;

    /**
     * 删除状态，0默认，1已删除
     */
    private Integer deleteFlag;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 分享图片
     */
    private String shareImg;

    /**
     * 用户头像
     */
    private String userImg;

    /**
     * 背景图片
     */
    private String backgroundImg;

    /**
     * 是否首次登陆，0不是，1是
     */
    private Integer isFirstLogin;

    /**
     * 推荐方式，0默认,1通过二维码分享推荐
     */
    private Integer shareType;

    /**
     * 关联商铺id
     */
    private Long shopId;

    /**
     * 用户在微信开发平台的唯一标识openid
     */
    private String wxUnionId;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    private String transactionId;

    /**
     * 生日
     */
    private Date birthTime;

    /**
     * 设备号
     */
    private String deviceNumber;

    /**
     * 唯一标识
     */
    private String uniqueNum;

    /**
     * 对应的店铺角色  0就是平台
     */
    private Long roleShopId;

    /**
     * 用户角色管理的范围
     */
    private Integer roleRange;

    /**
     * 用户在线状态 0 默认 1在线 2离线
     */
    private Integer lineStatus;

    /**
     * 收费标准
     */
    private BigDecimal consultingFee;

    /**
     * 评论总数
     */
    private Integer commentTotal;

    /**
     * 点赞总数
     */
    private Integer likeTotal;

    /**
     * 抽奖次数
     */
    private Integer luckyDrawNum;

    /**
     * 支付宝账户
     */
    private String alipayNum;

    /**
     * 微信手机号
     */
    private String wxTel;

    /**
     * 微信号
     */
    private String wxAccount;

    /**
     * 支付宝账户姓名
     */
    private String aliName;

    /**
     * 支付宝拍品收款账户
     */
    private String aliAccount;

    private Date sealExpirationTime;

    /**
     * 每场抢单限制
     */
    private Integer robOrderLimit;

    /**
     * 提现额度
     */
    private BigDecimal withdrawalLimit;

    /**
     * 完成拍品数量
     */
    private Integer finishAuction;

    /**
     * 老客户几次上浮
     */
    private Integer comeUpNum;

    /**
     * 是否团长 0 普通 1 团长
     */
    private Integer vipType;

    /**
     * 分馆编号
     */
    private Long tagId;

    private Integer subordinateLevel;

    private Long subordinateNum;

    /**
     * 封禁次数
     */
    private Integer sealNum;

    /**
     * 钻石每场抢单限定
     */
    private Integer diamondOrderLimit;

    private String commonName;

    private String commonTel;

    /**
     * 备注
     */
    private String memo;

    /**
     * 0 app用户，1系统用户
     */
    private Integer userType;

    private String cardNumber;

    private String memo1;

    private String memo2;

    private String memo3;

    /**
     * 直推人数
     */
    private Integer subUserNum;

    /**
     * 直推总人数
     */
    private Integer subUserNumAll;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public String getAuditExplain() {
        return auditExplain;
    }

    public void setAuditExplain(String auditExplain) {
        this.auditExplain = auditExplain;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getpUserName() {
        return pUserName;
    }

    public void setpUserName(String pUserName) {
        this.pUserName = pUserName;
    }

    public String getPidStr() {
        return pidStr;
    }

    public void setPidStr(String pidStr) {
        this.pidStr = pidStr;
    }

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getCurrentUserType() {
        return currentUserType;
    }

    public void setCurrentUserType(Integer currentUserType) {
        this.currentUserType = currentUserType;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public Long getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getFrozenExplain() {
        return frozenExplain;
    }

    public void setFrozenExplain(String frozenExplain) {
        this.frozenExplain = frozenExplain;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public Integer getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(Integer isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getWxUnionId() {
        return wxUnionId;
    }

    public void setWxUnionId(String wxUnionId) {
        this.wxUnionId = wxUnionId;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(Date birthTime) {
        this.birthTime = birthTime;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getUniqueNum() {
        return uniqueNum;
    }

    public void setUniqueNum(String uniqueNum) {
        this.uniqueNum = uniqueNum;
    }

    public Long getRoleShopId() {
        return roleShopId;
    }

    public void setRoleShopId(Long roleShopId) {
        this.roleShopId = roleShopId;
    }

    public Integer getRoleRange() {
        return roleRange;
    }

    public void setRoleRange(Integer roleRange) {
        this.roleRange = roleRange;
    }

    public Integer getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(Integer lineStatus) {
        this.lineStatus = lineStatus;
    }

    public BigDecimal getConsultingFee() {
        return consultingFee;
    }

    public void setConsultingFee(BigDecimal consultingFee) {
        this.consultingFee = consultingFee;
    }

    public Integer getCommentTotal() {
        return commentTotal;
    }

    public void setCommentTotal(Integer commentTotal) {
        this.commentTotal = commentTotal;
    }

    public Integer getLikeTotal() {
        return likeTotal;
    }

    public void setLikeTotal(Integer likeTotal) {
        this.likeTotal = likeTotal;
    }

    public Integer getLuckyDrawNum() {
        return luckyDrawNum;
    }

    public void setLuckyDrawNum(Integer luckyDrawNum) {
        this.luckyDrawNum = luckyDrawNum;
    }

    public String getAlipayNum() {
        return alipayNum;
    }

    public void setAlipayNum(String alipayNum) {
        this.alipayNum = alipayNum;
    }

    public String getWxTel() {
        return wxTel;
    }

    public void setWxTel(String wxTel) {
        this.wxTel = wxTel;
    }

    public String getWxAccount() {
        return wxAccount;
    }

    public void setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount;
    }

    public String getAliName() {
        return aliName;
    }

    public void setAliName(String aliName) {
        this.aliName = aliName;
    }

    public String getAliAccount() {
        return aliAccount;
    }

    public void setAliAccount(String aliAccount) {
        this.aliAccount = aliAccount;
    }

    public Date getSealExpirationTime() {
        return sealExpirationTime;
    }

    public void setSealExpirationTime(Date sealExpirationTime) {
        this.sealExpirationTime = sealExpirationTime;
    }

    public Integer getRobOrderLimit() {
        return robOrderLimit;
    }

    public void setRobOrderLimit(Integer robOrderLimit) {
        this.robOrderLimit = robOrderLimit;
    }

    public BigDecimal getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(BigDecimal withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }

    public Integer getFinishAuction() {
        return finishAuction;
    }

    public void setFinishAuction(Integer finishAuction) {
        this.finishAuction = finishAuction;
    }

    public Integer getComeUpNum() {
        return comeUpNum;
    }

    public void setComeUpNum(Integer comeUpNum) {
        this.comeUpNum = comeUpNum;
    }

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Integer getSubordinateLevel() {
        return subordinateLevel;
    }

    public void setSubordinateLevel(Integer subordinateLevel) {
        this.subordinateLevel = subordinateLevel;
    }

    public Long getSubordinateNum() {
        return subordinateNum;
    }

    public void setSubordinateNum(Long subordinateNum) {
        this.subordinateNum = subordinateNum;
    }

    public Integer getSealNum() {
        return sealNum;
    }

    public void setSealNum(Integer sealNum) {
        this.sealNum = sealNum;
    }

    public Integer getDiamondOrderLimit() {
        return diamondOrderLimit;
    }

    public void setDiamondOrderLimit(Integer diamondOrderLimit) {
        this.diamondOrderLimit = diamondOrderLimit;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getCommonTel() {
        return commonTel;
    }

    public void setCommonTel(String commonTel) {
        this.commonTel = commonTel;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }

    public String getMemo3() {
        return memo3;
    }

    public void setMemo3(String memo3) {
        this.memo3 = memo3;
    }

    public Integer getSubUserNum() {
        return subUserNum;
    }

    public void setSubUserNum(Integer subUserNum) {
        this.subUserNum = subUserNum;
    }

    public Integer getSubUserNumAll() {
        return subUserNumAll;
    }

    public void setSubUserNumAll(Integer subUserNumAll) {
        this.subUserNumAll = subUserNumAll;
    }
}