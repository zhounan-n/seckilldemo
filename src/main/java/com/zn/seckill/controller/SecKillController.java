package com.zn.seckill.controller;

import com.zn.seckill.services.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author zhoun
 * Created by zhoun on 2018/4/6.
 **/
@Controller
public class SecKillController {

    @Autowired
    private SecKillService secKillService;



}
