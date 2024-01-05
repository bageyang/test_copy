package com.zj.auction.general.app.controller;

import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.AuctionAppointment;
import com.zj.auction.general.app.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/app/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;
    @PostMapping(value = "appoint")

    public Ret<AuctionAppointment> Appointment(@RequestParam("userId") Long userId, @RequestParam("areaId")Long areaId){
        return Ret.ok(appointmentService.Appointment(userId,areaId));
    }

}
