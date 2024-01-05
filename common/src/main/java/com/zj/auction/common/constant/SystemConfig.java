package com.zj.auction.common.constant;

import lombok.Data;

import java.io.Serializable;

/**
 * ************************************************
 * SystemConfig实体
 *
 * @author MengDaNai
 * @version 1.0
 * @date 2019年2月13日 创建文件
 * @See ************************************************
 */
@Data
public class SystemConfig implements Serializable {
    private static String bankId;
    private static String bankKey;
    private static String sysCompanyName;
    private static String sysSoftwareName;
    private static String sysTelnumber;
    private static String dataBaseType;
    private static String aliAppId;
    private static String aliMerchantPrivateKey;
    private static String aliPayPublicKey;
    private static String hostDomain;
    private static String wxAppId;
    private static String wxAppSecret;
    private static String wxMchId;
    private static String wxPrivateKey;
    private static String wxPartnerKey;
    private static String wxJsapiTicket;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String imgBucketName;
    private static String endpoint;
    private static String imgDomain;
    private static String shareImg;

    private static String expressKey; //快递100
    private static String expressCustomer; //快递100
    private static String totalPlatformId;//总平台账号
    private static String appLogo;//logo
    private static String serviceTel;//客服电话

    private static String iosVersion;//苹果端版本号
    private static String iosDownload;//苹果端下载地址
    private static String azVersion;//安卓端版本号
    private static String azDownload;//安卓端下载地址

    private static String releaseRatio;//银币每日释放比例
    private static String directPush;//直推得下级获得银币得百分之十
    private static String indirectPush;//间推得下级获得银币得百分之五十
    private static String ratioLimit;//消费返利银币最低限制

    private static String silverToGoldLimit;//银币超过指定数量每天凌晨可以按比例释放
    private static String notBuyDays;//多少天未复购则不释放

    private static String firstGradeRebate;//推广注册商户第一级返金币数量
    private static String secondGradeRebate;//推广注册商户第二级返金币数量
    private static String thirdGradeRebate;//推广注册商户第三级返金币数量

    private static String goldDrawNum;//金币抽奖消耗个数


    private static String withdrawT1;//提现时间T1
    private static String withdrawT2;//提现时间T2
    private static String withdrawT3;//提现时间T3
    private static String minimumWithdrawal;//最小起提

    private static String minimumOrderPrice;//订单在最低消费可以抽奖一次



    /*---------------------------佳士得--------------------------------*/
    private static String leadTime; //提前多少分钟可预约

    private static String earnestMoney; //现金预约保证金

    private static String diamondEarnestMoney; //钻石预约保证金

    private static String serviceCharge; //转拍手续费按买入价的百分比

    private static String premium; //转拍溢价比列

    private static String noAffirmOrderTime; //卖家未在规定时间确认

    private static String noAffirmOrderMoney; //卖家未在规定时间确认扣除比例保证金

    private static String noPayOrderTime; //抢单成功多少时间不支付

    private static String noPayOrderMoney; //抢单成功多少时间不支付扣多少比例保证金

    private static String bannedUserDay; //买家拍下未在规定时间内付款封禁天数

    private static String registerWithdrawalLimit; //注册送提现额度

    private static String payAuctionWithdrawalRatio;//抢单获取提现比例

    private static String deliveryWithdrawalRatio;//现金交割单获取提现比例

    private static String newUserFinishOrderNum;//新用户完成多少收拍品交易上级奖励

    private static String userFloatingNum;//新用户成为活跃用户上级增加上浮次数

    private static String pUserAwardMoney;//新用户完成拍品上级奖励

    private static String pUserFloatingRatio;//新用户完成拍品上级转拍上浮

    private static String cashConversionDiamond;//现金充值钻石比率

    private static String diamondConversionServiceCharge;//钻石抵扣手续费比例

    private static String diamondWithdrawalRatio;//钻石区获取提现额度比例

    private static String diamondDeliveryWithdrawalRatio;//钻石区交割单获取提现额度比例

    private static String diamondPremium; //钻石转拍现金部分溢价比列

    private static String diamondServiceCharge; //钻石转拍手续费按现金部分的百分比

    private static String userEnterInAdvance; //新用户提前多少秒进入

    private static String newUserDay; //多少天是新用户

    private static String cashDeductionDiamond;//钻石竞拍现金等比钻石扣除

    private static String diamondSwitch;//钻石区开关

    private static String infractionsNum;//违规最高次数

    private static String redPacket;//平台红包奖励

    private static String howManyBefore;//前多少名完成转拍

    private static String performanceServiceCharge ;//绩效提现手续费

    private static String diamondOrderUnifyLimit;//平台钻石抢单限定次数

    private static String integralToDiamondRatio;//积分兑换钻石比例

    private static String integralToSilverRatio;//积分兑换银币比例

    private static String flipAcquireSilverRatio;//新用户转拍获得银币比例

    private static String goldServiceCharge ;//金币提现手续费

    private static String integralExchange ;//积分兑换控制

    private static String releaseCargoMinute ;//多少分钟内放货奖励

    private static String releaseCargoMoney ;//放货奖励金额

    private static String withdrawalAmountLimit ;//提现最高限额

    private static String withdrawalNumByDay ;//每天提现次数

    private static String aliSwitch ;//支付宝打款账户控制开关

    private static String diamondGoldPaySwitch ;//钻石区金币支付开关 0开 1关

    private static String registerDayCanWithdrawal ;//多少天注册新用户不能提现

    private static String goldPayControl;//金币支付手续费控制

    public static String getGoldPayControl() {
        return goldPayControl;
    }

    public static void setGoldPayControl(String goldPayControl) {
        SystemConfig.goldPayControl = goldPayControl;
    }

    public static String getRegisterDayCanWithdrawal() {
        return registerDayCanWithdrawal;
    }

    public static void setRegisterDayCanWithdrawal(String registerDayCanWithdrawal) {
        SystemConfig.registerDayCanWithdrawal = registerDayCanWithdrawal;
    }

    public static String getDiamondGoldPaySwitch() {
        return diamondGoldPaySwitch;
    }

    public static void setDiamondGoldPaySwitch(String diamondGoldPaySwitch) {
        SystemConfig.diamondGoldPaySwitch = diamondGoldPaySwitch;
    }

    public static String getWithdrawalAmountLimit() {
        return withdrawalAmountLimit;
    }

    public static void setWithdrawalAmountLimit(String withdrawalAmountLimit) {
        SystemConfig.withdrawalAmountLimit = withdrawalAmountLimit;
    }

    public static String getWithdrawalNumByDay() {
        return withdrawalNumByDay;
    }

    public static void setWithdrawalNumByDay(String withdrawalNumByDay) {
        SystemConfig.withdrawalNumByDay = withdrawalNumByDay;
    }

    public static String getAliSwitch() {
        return aliSwitch;
    }

    public static void setAliSwitch(String aliSwitch) {
        SystemConfig.aliSwitch = aliSwitch;
    }

    public static String getReleaseCargoMoney() {
        return releaseCargoMoney;
    }

    public static void setReleaseCargoMoney(String releaseCargoMoney) {
        SystemConfig.releaseCargoMoney = releaseCargoMoney;
    }

    public static String getReleaseCargoMinute() {
        return releaseCargoMinute;
    }

    public static void setReleaseCargoMinute(String releaseCargoMinute) {
        SystemConfig.releaseCargoMinute = releaseCargoMinute;
    }

    public static String getIntegralExchange() {
        return integralExchange;
    }

    public static void setIntegralExchange(String integralExchange) {
        SystemConfig.integralExchange = integralExchange;
    }

    public static String getGoldServiceCharge() {
        return goldServiceCharge;
    }

    public static void setGoldServiceCharge(String goldServiceCharge) {
        SystemConfig.goldServiceCharge = goldServiceCharge;
    }

    public static String getFlipAcquireSilverRatio() {
        return flipAcquireSilverRatio;
    }

    public static void setFlipAcquireSilverRatio(String flipAcquireSilverRatio) {
        SystemConfig.flipAcquireSilverRatio = flipAcquireSilverRatio;
    }

    public static String getIntegralToDiamondRatio() {
        return integralToDiamondRatio;
    }

    public static void setIntegralToDiamondRatio(String integralToDiamondRatio) {
        SystemConfig.integralToDiamondRatio = integralToDiamondRatio;
    }

    public static String getIntegralToSilverRatio() {
        return integralToSilverRatio;
    }

    public static void setIntegralToSilverRatio(String integralToSilverRatio) {
        SystemConfig.integralToSilverRatio = integralToSilverRatio;
    }

    public static String getDiamondEarnestMoney() {
        return diamondEarnestMoney;
    }

    public static void setDiamondEarnestMoney(String diamondEarnestMoney) {
        SystemConfig.diamondEarnestMoney = diamondEarnestMoney;
    }

    public static String getDiamondOrderUnifyLimit() {
        return diamondOrderUnifyLimit;
    }

    public static void setDiamondOrderUnifyLimit(String diamondOrderUnifyLimit) {
        SystemConfig.diamondOrderUnifyLimit = diamondOrderUnifyLimit;
    }

    public static String getDiamondDeliveryWithdrawalRatio() {
        return diamondDeliveryWithdrawalRatio;
    }

    public static void setDiamondDeliveryWithdrawalRatio(String diamondDeliveryWithdrawalRatio) {
        SystemConfig.diamondDeliveryWithdrawalRatio = diamondDeliveryWithdrawalRatio;
    }

    public static String getPerformanceServiceCharge() {
        return performanceServiceCharge;
    }

    public static void setPerformanceServiceCharge(String performanceServiceCharge) {
        SystemConfig.performanceServiceCharge = performanceServiceCharge;
    }

    public static String getHowManyBefore() {
        return howManyBefore;
    }

    public static void setHowManyBefore(String howManyBefore) {
        SystemConfig.howManyBefore = howManyBefore;
    }

    public static String getRedPacket() {
        return redPacket;
    }

    public static void setRedPacket(String redPacket) {
        SystemConfig.redPacket = redPacket;
    }

    public static String getInfractionsNum() {
        return infractionsNum;
    }

    public static void setInfractionsNum(String infractionsNum) {
        SystemConfig.infractionsNum = infractionsNum;
    }

    public static String getDiamondConversionServiceCharge() {
        return diamondConversionServiceCharge;
    }

    public static void setDiamondConversionServiceCharge(String diamondConversionServiceCharge) {
        SystemConfig.diamondConversionServiceCharge = diamondConversionServiceCharge;
    }

    public static String getDiamondSwitch() {
        return diamondSwitch;
    }

    public static void setDiamondSwitch(String diamondSwitch) {
        SystemConfig.diamondSwitch = diamondSwitch;
    }

    public static String getCashDeductionDiamond() {
        return cashDeductionDiamond;
    }

    public static void setCashDeductionDiamond(String cashDeductionDiamond) {
        SystemConfig.cashDeductionDiamond = cashDeductionDiamond;
    }

    public static String getUserEnterInAdvance() {
        return userEnterInAdvance;
    }

    public static void setUserEnterInAdvance(String userEnterInAdvance) {
        SystemConfig.userEnterInAdvance = userEnterInAdvance;
    }

    public static String getNewUserDay() {
        return newUserDay;
    }

    public static void setNewUserDay(String newUserDay) {
        SystemConfig.newUserDay = newUserDay;
    }

    public static String getUserFloatingNum() {
        return userFloatingNum;
    }

    public static void setUserFloatingNum(String userFloatingNum) {
        SystemConfig.userFloatingNum = userFloatingNum;
    }

    public static String getDiamondPremium() {
        return diamondPremium;
    }

    public static void setDiamondPremium(String diamondPremium) {
        SystemConfig.diamondPremium = diamondPremium;
    }

    public static String getDiamondServiceCharge() {
        return diamondServiceCharge;
    }

    public static void setDiamondServiceCharge(String diamondServiceCharge) {
        SystemConfig.diamondServiceCharge = diamondServiceCharge;
    }

    public static String getCashConversionDiamond() {
        return cashConversionDiamond;
    }

    public static void setCashConversionDiamond(String cashConversionDiamond) {
        SystemConfig.cashConversionDiamond = cashConversionDiamond;
    }


    public static String getDiamondWithdrawalRatio() {
        return diamondWithdrawalRatio;
    }

    public static void setDiamondWithdrawalRatio(String diamondWithdrawalRatio) {
        SystemConfig.diamondWithdrawalRatio = diamondWithdrawalRatio;
    }

    public static String getNewUserFinishOrderNum() {
        return newUserFinishOrderNum;
    }

    public static void setNewUserFinishOrderNum(String newUserFinishOrderNum) {
        SystemConfig.newUserFinishOrderNum = newUserFinishOrderNum;
    }

    public static String getpUserAwardMoney() {
        return pUserAwardMoney;
    }

    public static void setpUserAwardMoney(String pUserAwardMoney) {
        SystemConfig.pUserAwardMoney = pUserAwardMoney;
    }

    public static String getpUserFloatingRatio() {
        return pUserFloatingRatio;
    }

    public static void setpUserFloatingRatio(String pUserFloatingRatio) {
        SystemConfig.pUserFloatingRatio = pUserFloatingRatio;
    }

    public static String getRegisterWithdrawalLimit() {
        return registerWithdrawalLimit;
    }

    public static void setRegisterWithdrawalLimit(String registerWithdrawalLimit) {
        SystemConfig.registerWithdrawalLimit = registerWithdrawalLimit;
    }

    public static String getPayAuctionWithdrawalRatio() {
        return payAuctionWithdrawalRatio;
    }

    public static void setPayAuctionWithdrawalRatio(String payAuctionWithdrawalRatio) {
        SystemConfig.payAuctionWithdrawalRatio = payAuctionWithdrawalRatio;
    }

    public static String getDeliveryWithdrawalRatio() {
        return deliveryWithdrawalRatio;
    }

    public static void setDeliveryWithdrawalRatio(String deliveryWithdrawalRatio) {
        SystemConfig.deliveryWithdrawalRatio = deliveryWithdrawalRatio;
    }

    public static String getBannedUserDay() {
        return bannedUserDay;
    }

    public static void setBannedUserDay(String bannedUserDay) {
        SystemConfig.bannedUserDay = bannedUserDay;
    }

    public static String getNoAffirmOrderTime() {
        return noAffirmOrderTime;
    }

    public static void setNoAffirmOrderTime(String noAffirmOrderTime) {
        SystemConfig.noAffirmOrderTime = noAffirmOrderTime;
    }

    public static String getNoAffirmOrderMoney() {
        return noAffirmOrderMoney;
    }

    public static void setNoAffirmOrderMoney(String noAffirmOrderMoney) {
        SystemConfig.noAffirmOrderMoney = noAffirmOrderMoney;
    }

    public static String getNoPayOrderMoney() {
        return noPayOrderMoney;
    }

    public static void setNoPayOrderMoney(String noPayOrderMoney) {
        SystemConfig.noPayOrderMoney = noPayOrderMoney;
    }



    public static String getNoPayOrderTime() {
        return noPayOrderTime;
    }

    public static void setNoPayOrderTime(String noPayOrderTime) {
        SystemConfig.noPayOrderTime = noPayOrderTime;
    }

    public static String getServiceCharge() {
        return serviceCharge;
    }

    public static void setServiceCharge(String serviceCharge) {
        SystemConfig.serviceCharge = serviceCharge;
    }

    public static String getPremium() {
        return premium;
    }

    public static void setPremium(String premium) {
        SystemConfig.premium = premium;
    }

    public static String getEarnestMoney() {
        return earnestMoney;
    }

    public static void setEarnestMoney(String earnestMoney) {
        SystemConfig.earnestMoney = earnestMoney;
    }

    public static String getLeadTime() {
        return leadTime;
    }

    public static void setLeadTime(String leadTime) {
        SystemConfig.leadTime = leadTime;
    }

    public static String getMinimumOrderPrice() {
        return minimumOrderPrice;
    }

    public static void setMinimumOrderPrice(String minimumOrderPrice) {
        SystemConfig.minimumOrderPrice = minimumOrderPrice;
    }

    public static String getMinimumWithdrawal() {
        return minimumWithdrawal;
    }

    public static void setMinimumWithdrawal(String minimumWithdrawal) {
        SystemConfig.minimumWithdrawal = minimumWithdrawal;
    }

    public static String getWithdrawT1() {
        return withdrawT1;
    }

    public static void setWithdrawT1(String withdrawT1) {
        SystemConfig.withdrawT1 = withdrawT1;
    }

    public static String getWithdrawT2() {
        return withdrawT2;
    }

    public static void setWithdrawT2(String withdrawT2) {
        SystemConfig.withdrawT2 = withdrawT2;
    }

    public static String getWithdrawT3() {
        return withdrawT3;
    }

    public static void setWithdrawT3(String withdrawT3) {
        SystemConfig.withdrawT3 = withdrawT3;
    }

    public static String getGoldDrawNum() {
        return goldDrawNum;
    }

    public static void setGoldDrawNum(String goldDrawNum) {
        SystemConfig.goldDrawNum = goldDrawNum;
    }

    public static String getFirstGradeRebate() {
        return firstGradeRebate;
    }

    public static void setFirstGradeRebate(String firstGradeRebate) {
        SystemConfig.firstGradeRebate = firstGradeRebate;
    }

    public static String getSecondGradeRebate() {
        return secondGradeRebate;
    }

    public static void setSecondGradeRebate(String secondGradeRebate) {
        SystemConfig.secondGradeRebate = secondGradeRebate;
    }

    public static String getThirdGradeRebate() {
        return thirdGradeRebate;
    }

    public static void setThirdGradeRebate(String thirdGradeRebate) {
        SystemConfig.thirdGradeRebate = thirdGradeRebate;
    }

    public static String getNotBuyDays() {
        return notBuyDays;
    }

    public static void setNotBuyDays(String notBuyDays) {
        SystemConfig.notBuyDays = notBuyDays;
    }

    public static String getSilverToGoldLimit() {
        return silverToGoldLimit;
    }

    public static void setSilverToGoldLimit(String silverToGoldLimit) {
        SystemConfig.silverToGoldLimit = silverToGoldLimit;
    }

    public static String getRatioLimit() {
        return ratioLimit;
    }

    public static void setRatioLimit(String ratioLimit) {
        SystemConfig.ratioLimit = ratioLimit;
    }

    public static String getDirectPush() {
        return directPush;
    }

    public static void setDirectPush(String directPush) {
        SystemConfig.directPush = directPush;
    }

    public static String getIndirectPush() {
        return indirectPush;
    }

    public static void setIndirectPush(String indirectPush) {
        SystemConfig.indirectPush = indirectPush;
    }

    public static String getReleaseRatio() {
        return releaseRatio;
    }

    public static void setReleaseRatio(String releaseRatio) {
        SystemConfig.releaseRatio = releaseRatio;
    }

    public static String getAppLogo() {
        return appLogo;
    }

    public static void setAppLogo(String appLogo) {
        SystemConfig.appLogo = appLogo;
    }


    public static String getIosVersion() {
        return iosVersion;
    }

    public static void setIosVersion(String iosVersion) {
        SystemConfig.iosVersion = iosVersion;
    }

    public static String getIosDownload() {
        return iosDownload;
    }

    public static void setIosDownload(String iosDownload) {
        SystemConfig.iosDownload = iosDownload;
    }

    public static String getAzVersion() {
        return azVersion;
    }

    public static void setAzVersion(String azVersion) {
        SystemConfig.azVersion = azVersion;
    }

    public static String getAzDownload() {
        return azDownload;
    }

    public static void setAzDownload(String azDownload) {
        SystemConfig.azDownload = azDownload;
    }

    public static String getServiceTel() {
        return serviceTel;
    }

    public static void setServiceTel(String serviceTel) {
        SystemConfig.serviceTel = serviceTel;
    }


    public static String getExpressKey() {
        return expressKey;
    }

    public static void setExpressKey(String expressKey) {
        SystemConfig.expressKey = expressKey;
    }

    public static String getExpressCustomer() {
        return expressCustomer;
    }

    public static void setExpressCustomer(String expressCustomer) {
        SystemConfig.expressCustomer = expressCustomer;
    }

    public static String getTotalPlatformId() {
        return totalPlatformId;
    }

    public static void setTotalPlatformId(String totalPlatformId) {
        SystemConfig.totalPlatformId = totalPlatformId;
    }

    /**
     * 提现手续费
     */
    private static String withdrawalsServiceCharge;

    public static String getBankId() {
        return bankId;
    }

    public static void setBankId(String bankId) {
        SystemConfig.bankId = bankId;
    }

    public static String getBankKey() {
        return bankKey;
    }

    public static void setBankKey(String bankKey) {
        SystemConfig.bankKey = bankKey;
    }

    public static String getSysCompanyName() {
        return sysCompanyName;
    }

    public static void setSysCompanyName(String sysCompanyName) {
        SystemConfig.sysCompanyName = sysCompanyName;
    }

    public static String getSysSoftwareName() {
        return sysSoftwareName;
    }

    public static void setSysSoftwareName(String sysSoftwareName) {
        SystemConfig.sysSoftwareName = sysSoftwareName;
    }

    public static String getSysTelnumber() {
        return sysTelnumber;
    }

    public static void setSysTelnumber(String sysTelnumber) {
        SystemConfig.sysTelnumber = sysTelnumber;
    }

    public static String getDataBaseType() {
        return dataBaseType;
    }

    public static void setDataBaseType(String dataBaseType) {
        SystemConfig.dataBaseType = dataBaseType;
    }

    public static String getAliAppId() {
        return aliAppId;
    }

    public static void setAliAppId(String aliAppId) {
        SystemConfig.aliAppId = aliAppId;
    }

    public static String getAliMerchantPrivateKey() {
        return aliMerchantPrivateKey;
    }

    public static void setAliMerchantPrivateKey(String aliMerchantPrivateKey) {
        SystemConfig.aliMerchantPrivateKey = aliMerchantPrivateKey;
    }

    public static String getAliPayPublicKey() {
        return aliPayPublicKey;
    }

    public static void setAliPayPublicKey(String aliPayPublicKey) {
        SystemConfig.aliPayPublicKey = aliPayPublicKey;
    }

    public static String getHostDomain() {
        return hostDomain;
    }

    public static void setHostDomain(String hostDomain) {
        SystemConfig.hostDomain = hostDomain;
    }

    public static String getWxAppId() {
        return wxAppId;
    }

    public static void setWxAppId(String wxAppId) {
        SystemConfig.wxAppId = wxAppId;
    }

    public static String getWxAppSecret() {
        return wxAppSecret;
    }

    public static void setWxAppSecret(String wxAppSecret) {
        SystemConfig.wxAppSecret = wxAppSecret;
    }

    public static String getWxMchId() {
        return wxMchId;
    }

    public static void setWxMchId(String wxMchId) {
        SystemConfig.wxMchId = wxMchId;
    }

    public static String getWxPrivateKey() {
        return wxPrivateKey;
    }

    public static void setWxPrivateKey(String wxPrivateKey) {
        SystemConfig.wxPrivateKey = wxPrivateKey;
    }

    public static String getWxPartnerKey() {
        return wxPartnerKey;
    }

    public static void setWxPartnerKey(String wxPartnerKey) {
        SystemConfig.wxPartnerKey = wxPartnerKey;
    }

    public static String getWxJsapiTicket() {
        return wxJsapiTicket;
    }

    public static void setWxJsapiTicket(String wxJsapiTicket) {
        SystemConfig.wxJsapiTicket = wxJsapiTicket;
    }

    public static String getAccessKeyId() {
        return accessKeyId;
    }

    public static void setAccessKeyId(String accessKeyId) {
        SystemConfig.accessKeyId = accessKeyId;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }

    public static void setAccessKeySecret(String accessKeySecret) {
        SystemConfig.accessKeySecret = accessKeySecret;
    }

    public static String getImgBucketName() {
        return imgBucketName;
    }

    public static void setImgBucketName(String imgBucketName) {
        SystemConfig.imgBucketName = imgBucketName;
    }

    public static String getEndpoint() {
        return endpoint;
    }

    public static void setEndpoint(String endpoint) {
        SystemConfig.endpoint = endpoint;
    }

    public static String getImgDomain() {
        return imgDomain;
    }

    public static void setImgDomain(String imgDomain) {
        SystemConfig.imgDomain = imgDomain;
    }

    public static String getShareImg() {
        return shareImg;
    }

    public static void setShareImg(String shareImg) {
        SystemConfig.shareImg = shareImg;
    }

    public static String getWithdrawalsServiceCharge() {
        return withdrawalsServiceCharge;
    }

    public static void setWithdrawalsServiceCharge(String withdrawalsServiceCharge) {
        SystemConfig.withdrawalsServiceCharge = withdrawalsServiceCharge;
    }

}
