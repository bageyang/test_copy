package com.zj.auction.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class User implements Serializable {
    private Long userId;

    private Integer roleId;

    private Integer roleType;

    private String userName;

    private String passWord;

    private String realName;

    private String salt;

    private Integer sex;

    private Integer status;

    private Integer audit;

    private String auditExplain;

    private Long pid;

    private String pUserName;

    private String pidStr;

    private Integer levelNum;

    private String tel;

    private String email;

    private String loginIp;

    private LocalDateTime loginTime;

    private Integer userType;

    private Integer currentUserType;

    private String addr;

    private String cardNumber;

    private String domainName;

    private Integer subUserNum;

    private Integer subUserNumAll;

    private String payPassword;

    private Integer departmentId;

    private LocalDateTime addTime;

    private Long addUserId;

    private LocalDateTime updateTime;

    private Long updateUserId;

    private String frozenExplain;

    private Integer deleteFlag;

    private String nickName;

    private String shareImg;

    private String userImg;

    private String backgroundImg;

    private String province;

    private String city;

    private String county;

    private Long provinceId;

    private Long cityId;

    private Long countyId;

    private Integer isFirstLogin;

    private Integer shareType;

    private Long shopId;

    private String wxUnionId;

    private String frontImage;

    private String reverseImage;

    private LocalDateTime applyTime;

    private LocalDateTime lastLoginTime;

    private String transactionId;

    private String faceImg;

    private Integer enteringStatus;

    private LocalDateTime birthTime;

    private String deviceNumber;

    private String simNumber;

    private String primaryTel;

    private String uniqueNum;

    private Long roleShopId;

    private Integer roleRange;

    private Integer lineStatus;

    private BigDecimal consultingFee;

    private Integer commentTotal;

    private Integer likeTotal;

    private Integer luckyDrawNum;

    private Long agentUserId;

    private String pcPassword;

    private String pcSalt;

    private String alipayNum;

    private String wxTel;

    private String wxAccount;

    private String aliName;

    private String aliAccount;

    private LocalDateTime sealExpirationTime;

    private Integer robOrderLimit;

    private BigDecimal withdrawalLimit;

    private Integer finishAuction;

    private Integer comeUpNum;

    private Integer vipType;

    private Long tagId;

    private Integer subordinateLevel;

    private Long subordinateNum;

    private Integer sealNum;

    private Integer diamondOrderLimit;

    private String commonName;

    private String commonTel;

    private static final long serialVersionUID = 1L;

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

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getCurrentUserType() {
        return currentUserType;
    }

    public void setCurrentUserType(Integer currentUserType) {
        this.currentUserType = currentUserType;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
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

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getReverseImage() {
        return reverseImage;
    }

    public void setReverseImage(String reverseImage) {
        this.reverseImage = reverseImage;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFaceImg() {
        return faceImg;
    }

    public void setFaceImg(String faceImg) {
        this.faceImg = faceImg;
    }

    public Integer getEnteringStatus() {
        return enteringStatus;
    }

    public void setEnteringStatus(Integer enteringStatus) {
        this.enteringStatus = enteringStatus;
    }

    public LocalDateTime getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(LocalDateTime birthTime) {
        this.birthTime = birthTime;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getSimNumber() {
        return simNumber;
    }

    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }

    public String getPrimaryTel() {
        return primaryTel;
    }

    public void setPrimaryTel(String primaryTel) {
        this.primaryTel = primaryTel;
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

    public Long getAgentUserId() {
        return agentUserId;
    }

    public void setAgentUserId(Long agentUserId) {
        this.agentUserId = agentUserId;
    }

    public String getPcPassword() {
        return pcPassword;
    }

    public void setPcPassword(String pcPassword) {
        this.pcPassword = pcPassword;
    }

    public String getPcSalt() {
        return pcSalt;
    }

    public void setPcSalt(String pcSalt) {
        this.pcSalt = pcSalt;
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

    public LocalDateTime getSealExpirationTime() {
        return sealExpirationTime;
    }

    public void setSealExpirationTime(LocalDateTime sealExpirationTime) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", roleId=").append(roleId);
        sb.append(", roleType=").append(roleType);
        sb.append(", userName=").append(userName);
        sb.append(", passWord=").append(passWord);
        sb.append(", realName=").append(realName);
        sb.append(", salt=").append(salt);
        sb.append(", sex=").append(sex);
        sb.append(", status=").append(status);
        sb.append(", audit=").append(audit);
        sb.append(", auditExplain=").append(auditExplain);
        sb.append(", pid=").append(pid);
        sb.append(", pUserName=").append(pUserName);
        sb.append(", pidStr=").append(pidStr);
        sb.append(", levelNum=").append(levelNum);
        sb.append(", tel=").append(tel);
        sb.append(", email=").append(email);
        sb.append(", loginIp=").append(loginIp);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", userType=").append(userType);
        sb.append(", currentUserType=").append(currentUserType);
        sb.append(", addr=").append(addr);
        sb.append(", cardNumber=").append(cardNumber);
        sb.append(", domainName=").append(domainName);
        sb.append(", subUserNum=").append(subUserNum);
        sb.append(", subUserNumAll=").append(subUserNumAll);
        sb.append(", payPassword=").append(payPassword);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", addTime=").append(addTime);
        sb.append(", addUserId=").append(addUserId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUserId=").append(updateUserId);
        sb.append(", frozenExplain=").append(frozenExplain);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", nickName=").append(nickName);
        sb.append(", shareImg=").append(shareImg);
        sb.append(", userImg=").append(userImg);
        sb.append(", backgroundImg=").append(backgroundImg);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", county=").append(county);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", cityId=").append(cityId);
        sb.append(", countyId=").append(countyId);
        sb.append(", isFirstLogin=").append(isFirstLogin);
        sb.append(", shareType=").append(shareType);
        sb.append(", shopId=").append(shopId);
        sb.append(", wxUnionId=").append(wxUnionId);
        sb.append(", frontImage=").append(frontImage);
        sb.append(", reverseImage=").append(reverseImage);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", transactionId=").append(transactionId);
        sb.append(", faceImg=").append(faceImg);
        sb.append(", enteringStatus=").append(enteringStatus);
        sb.append(", birthTime=").append(birthTime);
        sb.append(", deviceNumber=").append(deviceNumber);
        sb.append(", simNumber=").append(simNumber);
        sb.append(", primaryTel=").append(primaryTel);
        sb.append(", uniqueNum=").append(uniqueNum);
        sb.append(", roleShopId=").append(roleShopId);
        sb.append(", roleRange=").append(roleRange);
        sb.append(", lineStatus=").append(lineStatus);
        sb.append(", consultingFee=").append(consultingFee);
        sb.append(", commentTotal=").append(commentTotal);
        sb.append(", likeTotal=").append(likeTotal);
        sb.append(", luckyDrawNum=").append(luckyDrawNum);
        sb.append(", agentUserId=").append(agentUserId);
        sb.append(", pcPassword=").append(pcPassword);
        sb.append(", pcSalt=").append(pcSalt);
        sb.append(", alipayNum=").append(alipayNum);
        sb.append(", wxTel=").append(wxTel);
        sb.append(", wxAccount=").append(wxAccount);
        sb.append(", aliName=").append(aliName);
        sb.append(", aliAccount=").append(aliAccount);
        sb.append(", sealExpirationTime=").append(sealExpirationTime);
        sb.append(", robOrderLimit=").append(robOrderLimit);
        sb.append(", withdrawalLimit=").append(withdrawalLimit);
        sb.append(", finishAuction=").append(finishAuction);
        sb.append(", comeUpNum=").append(comeUpNum);
        sb.append(", vipType=").append(vipType);
        sb.append(", tagId=").append(tagId);
        sb.append(", subordinateLevel=").append(subordinateLevel);
        sb.append(", subordinateNum=").append(subordinateNum);
        sb.append(", sealNum=").append(sealNum);
        sb.append(", diamondOrderLimit=").append(diamondOrderLimit);
        sb.append(", commonName=").append(commonName);
        sb.append(", commonTel=").append(commonTel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}