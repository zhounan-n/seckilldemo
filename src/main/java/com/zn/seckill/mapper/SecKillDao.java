package com.zn.seckill.mapper;

import com.zn.seckill.entities.SecKill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author zhoun
 */
public interface SecKillDao {

    /**
     * 根据seckilld减少商品库存
     *
     * @param seckillId 秒杀商品id
     * @param killTime  秒杀的精确时间
     * @return 秒杀成功返回1 失败返回0
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据seckilld查询秒杀商品的详情
     *
     * @param seckillId 秒杀商品id
     * @return 返回查询到的秒杀商品信息
     */
    SecKill queryById(@Param("seckillId") long seckillId);

    /**
     * 根据一个偏移量是查询秒杀的商品列表
     *
     * @param offset 偏移量
     * @param limit  限制查询的数据个数
     * @return
     */
    List<SecKill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

}
