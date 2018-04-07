package com.zn.seckill.services;

import com.zn.seckill.dto.Exposer;
import com.zn.seckill.dto.SeckillExcution;
import com.zn.seckill.entities.SecKill;
import com.zn.seckill.exception.SeckillException;

import java.util.List;

/**
 * @author zhoun
 * Created by zhoun on 2018/4/6.
 **/
public interface SecKillService {

    /**
     * 查询全部的秒杀记录
     *
     * @return List<SecKill>
     */
    List<SecKill> getSeckillList();

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId 秒杀id
     * @return 秒杀信息
     */
    SecKill getById(long seckillId);

    /**
     * 在秒杀开始时输出秒杀接口的地址，否则输出系统时间和秒杀时间
     *
     * @param seckillId 秒杀商品id
     * @return Exposer
     */
    Exposer exportSeckillUrl(long seckillId);


    /**
     * 执行秒杀操作,有可能成功,有可能失败
     *
     * @param seckillId 秒杀商品id
     * @param userPhone 用户电话(唯一性)
     * @param md5       加密
     * @return 执行结果
     */
    SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5);
}
