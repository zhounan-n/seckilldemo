package com.zn.seckill.exception;

/**
 * @author zhoun
 * 秒杀相关业务的异常
 * Created by zhoun on 2018/4/7.
 **/
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
