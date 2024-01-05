package com.zj.auction.common.condition;

import com.zj.auction.common.base.BaseCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionCondition extends BaseCondition {
    private String permissionName;
    private Integer levelNum;
    private String permis;

}
