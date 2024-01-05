package com.zj.auction.common.dto;

import lombok.Data;

/**
 * @author 胖胖不胖
 */
@Data
public class UserRoleDTO {
    private Long userId;
    private Long roleId;
    private String roleStr;
}
