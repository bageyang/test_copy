package com.zj.auction.common.query;

public class WalletQuery extends PageQuery{
    private Long userId;
    private Byte walletType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getWalletType() {
        return walletType;
    }

    public void setWalletType(Byte walletType) {
        this.walletType = walletType;
    }
}
