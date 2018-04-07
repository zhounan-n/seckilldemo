package com.zn.seckill.services.impl;

import com.zn.seckill.dto.Exposer;
import com.zn.seckill.dto.SeckillExcution;
import com.zn.seckill.entities.SecKill;
import com.zn.seckill.entities.SuccessKilld;
import com.zn.seckill.enums.SeckillStateEnum;
import com.zn.seckill.exception.RepeatKillException;
import com.zn.seckill.exception.SeckillCloseException;
import com.zn.seckill.exception.SeckillException;
import com.zn.seckill.mapper.SecKillDao;
import com.zn.seckill.mapper.SuccessKilldDao;
import com.zn.seckill.services.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @author zhoun
 * Created by zhoun on 2018/4/7.
 **/
@Service
public class SecKillServiceImpl implements SecKillService {

    Logger logger = LoggerFactory.getLogger(SecKillServiceImpl.class);

    /**
     * 加入一个混淆字符串(秒杀接口)的salt，为了我避免用户猜出我们的md5值，值任意给，越复杂越好
     */
    private final String salt = "shsdssljdd'l.";

    @Autowired
    private SecKillDao secKillDao;
    @Autowired
    private SuccessKilldDao successKilldDao;

    @Override
    public List<SecKill> getSeckillList() {
        return secKillDao.queryAll(0, 4);
    }

    @Override
    public SecKill getById(long seckillId) {
        return secKillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        SecKill secKill = secKillDao.queryById(seckillId);
        //无此秒杀记录
        if (secKill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();
        Date nowTime = new Date();
        if (startTime.getTime() > nowTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        //秒杀开启，返回秒杀商品的id 用给接口加密的md5
        String md5 = getMd5(seckillId);

        return new Exposer(true, md5, seckillId);
    }

    private String getMd5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 秒杀成功，减库存，增加明细，失败，抛异常，事务回滚
     */
    @Transactional
    @Override
    public SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5) {
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑:减库存+增加明细购买
        Date now = new Date();
        try {
            int insertCount = successKilldDao.insertSuccessKilld(seckillId, userPhone);
            //看该明细是否被重复插入，既该用户是否重复秒杀
            if (insertCount <= 0) {
                throw new RepeatKillException("seckill repeated");
            } else {
                //减库存 热点商品竞争
                int updateCount = secKillDao.reduceNumber(seckillId, now);
                if (updateCount < 0) {
                    //没有更新库存记录 说明秒杀结束 rollback
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    //秒杀成功 得到成功插入的明细信息，并返回成功秒杀的信息
                    SuccessKilld successKilld = successKilldDao.getByIdWithSeckill(seckillId);
                    return new SeckillExcution(seckillId, SeckillStateEnum.SUCCESS, successKilld);
                }

            }


        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SeckillException("seckill error:" + e.getMessage());
        }
    }
}
