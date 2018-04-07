package com.zn.seckill.dto;

import com.zn.seckill.entities.SuccessKilld;
import com.zn.seckill.enums.SeckillStateEnum;

/**
 * Created by zhoun on 2018/4/7.
 **/
public class SeckillExcution {

    private long seckillId;

    /**
     * 执行结果状态
     */
    private int state;

    /**
     * 状态的明文标识
     */
    private String stateInfo;

    /**
     * 当秒杀成功时，需要传秒杀成功的对象回去
     */
    private SuccessKilld successKilld;

    /**
     * 秒杀成功返回所有信息
     */
    public SeckillExcution(long seckillId, SeckillStateEnum seckillStateEnum, SuccessKilld successKilld) {
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getStateInfo();
        this.successKilld = successKilld;
    }


    /**
     * 秒杀失败
     */
    public SeckillExcution(long seckillId, SeckillStateEnum statEnum) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }


}
