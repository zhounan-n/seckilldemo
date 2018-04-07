package com.zn.seckill.exception;

/**
 * 重复秒杀异常
 * Created by zhoun on 2018/4/7.
 **/
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }

}
