package com.zn.seckill.services;

import com.zn.seckill.mapper.SecKillDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhoun on 2018/4/6.
 **/
@Service
public class SecKillService {

    @Autowired
    private SecKillDao secKillDao;


}
