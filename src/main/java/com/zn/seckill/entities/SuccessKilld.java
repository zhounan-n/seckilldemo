package com.zn.seckill.entities;

import java.util.Date;

/**
 * 秒杀成功
 * @author zhounan
 * Created by zhoun on 2018/4/6.
 **/
public class SuccessKilld {

    private long seckillId;
    private long serPhone;
    private short state;
    private Date createTime;
    private SecKill secKill;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getSerPhone() {
        return serPhone;
    }

    public void setSerPhone(long serPhone) {
        this.serPhone = serPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SecKill getSecKill() {
        return secKill;
    }

    public void setSecKill(SecKill secKill) {
        this.secKill = secKill;
    }
}
