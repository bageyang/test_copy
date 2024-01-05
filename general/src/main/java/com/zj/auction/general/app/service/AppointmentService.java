package com.zj.auction.general.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.auction.common.model.AuctionAppointment;


import java.util.List;

public interface AppointmentService extends IService<com.zj.auction.common.model.AuctionAppointment> {
    //查询当前预约记录 可能多个
    List<AuctionAppointment> queryAppointments(Long userId);
    //预约产生预约记录
    AuctionAppointment Appointment(Long userId, Long areaId);
    //
}
