package com.zn.seckill.mapper;

import com.zn.seckill.entities.SuccessKilld;

/**
 * @author zhoun
 */
public interface SuccessKilldDao {

    /**
     * 用户秒杀行为 增加一条秒杀信息
     *
     * @param secKilld  秒杀商品id
     * @param userPhone 用户电话
     * @return 秒杀成功返回1 秒杀失败返回0
     */
    int insertSuccessKilld(long secKilld, long userPhone);

    /**
     * 根据商品id获取商品秒杀信息
     *
     * @param seckilld 秒杀商品id
     * @return 秒杀商品的明细信息
     */
    SuccessKilld getByIdWithSeckill(long seckilld);

}
