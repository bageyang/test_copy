package com.zj.auction.general.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.auction.common.enums.AuctionAppointmentEnum;
import com.zj.auction.common.mapper.AuctionAppointmentMapper;
import com.zj.auction.common.model.AuctionAppointment;
import com.zj.auction.general.app.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
@Service
@Slf4j
public class AppointmentServiceImpl extends ServiceImpl<AuctionAppointmentMapper, AuctionAppointment> implements AppointmentService {
    @Resource
    AuctionAppointmentMapper auctionAppointmentMapper;

    @Override
    public List<AuctionAppointment> queryAppointments(Long userId) {
        return null;
    }

    @Override
    public AuctionAppointment Appointment(Long userId, Long areaId) {
        //产生新的预约单
        AuctionAppointment auctionAppointment = new AuctionAppointment();
        auctionAppointment.setUserId(userId);
        auctionAppointment.setAreaId(areaId);
        //todo 设置预约单费用
        auctionAppointment.setAppointmentFee(new BigDecimal("100"));
        auctionAppointment.setStatus(AuctionAppointmentEnum.APPOINTMENT_NO_PAY.getCode());
        auctionAppointmentMapper.insertSelective(auctionAppointment);
        return auctionAppointment;

    }
}
