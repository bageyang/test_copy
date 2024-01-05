package com.zj.auction.common.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AppointmentVo {

    private Long id;

    private Long areaId;

    private Long userId;

    private BigDecimal appointmentFee;

    private Byte payment;

    private Byte status;

    private String remark;

}
