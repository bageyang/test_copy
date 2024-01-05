package com.zj.auction.common.enums;

public enum UserBillStatAndTypeEnum {
    //流水类型
    BUY_GOODS((byte)1,"购买商品支付"),
    RECHARGE((byte) 2,"充值余额支付"),
    APPOINTMENT((byte)3,"预约保证金支付"),
    AUCTION((byte)4,"转拍手续费支付"),
    PAY((byte)5,"pay"),
    //支付手段  支付宝，微信
    ALI_PAY((byte)1,"支付宝支付"),
    WX_PAY((byte)2,"微信支付"),
    //流水状态
    NO_PAY((byte)1,"待支付"),
    SUCCESS_PAY((byte)2,"成功支付"),
    CLOSE((byte)3,"已关闭");



    private final byte code;
    private final String text;

    UserBillStatAndTypeEnum(byte code, String text) {
        this.code = code;
        this.text =text;
    }

    //流水
    public static String getTextByCode (byte code) {
        String res = "";
        switch (code) {
            case 1:
                res = BUY_GOODS.getText();
                break;
            case 2:
                res =  RECHARGE.getText();
                break;
            case 3:
                res = APPOINTMENT.getText();
                break;
            case 4:
                res = AUCTION.getText();
                break;
            case 5:
                res = PAY.getText();
                break;
            default:
        }
        return res;
    }

    public static String getPayTextByCode (byte code) {
        String res = "";
        switch (code) {
            case 1:
                res = ALI_PAY.getText();
                break;
            case 2:
                res =  WX_PAY.getText();
                break;
            default:
        }
        return res;
    }
    public static String getStatusTextByCode (byte code) {
        String res = "";
        switch (code) {
            case 1:
                res = NO_PAY.getText();
                break;
            case 2:
                res =  SUCCESS_PAY.getText();
                break;
            case 3:
                res = CLOSE.getText();
            default:
        }
        return res;
    }

    public byte getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
