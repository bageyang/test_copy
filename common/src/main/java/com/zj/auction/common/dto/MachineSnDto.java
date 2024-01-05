package com.zj.auction.common.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MachineSnDto {
    private String ip;
    private String serverName;
    private LocalDateTime activeTime;
}
