package com.zn.seckill.controller;

import com.zn.seckill.dto.Exposer;
import com.zn.seckill.dto.SeckillExcution;
import com.zn.seckill.dto.SeckillResult;
import com.zn.seckill.entities.SecKill;
import com.zn.seckill.enums.SeckillStateEnum;
import com.zn.seckill.exception.RepeatKillException;
import com.zn.seckill.exception.SeckillCloseException;
import com.zn.seckill.services.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author zhoun
 * Created by zhoun on 2018/4/6.
 **/
@Controller
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        //获取列表页
        List<SecKill> list = secKillService.getSeckillList();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        SecKill secKill = secKillService.getById(seckillId);
        if (secKill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute(secKill);
        return "detail";
    }


    /**
     * 暴露秒杀接口的方法
     */
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = secKillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);

        } catch (Exception e) {
            e.printStackTrace();
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/{seckillId}/{md5}/excution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public SeckillResult<SeckillExcution> excute(@PathVariable("seckillId") Long seckillId,
                                                 @PathVariable("md5") String md5,
                                                 @CookieValue(value = "userPhone", required = false) Long userPhone) {

        if (userPhone == null) {
            return new SeckillResult<SeckillExcution>(false, "未注册");
        }
        //SeckillResult<SeckillExcution> result;
        try {
            SeckillExcution seckillExcution = secKillService.excuteSeckill(seckillId, userPhone, md5);
            return new SeckillResult<>(true, seckillExcution);
        } catch (RepeatKillException e) {
            SeckillExcution seckillExcution = new SeckillExcution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<>(false, seckillExcution);
        } catch (SeckillCloseException e2) {
            SeckillExcution seckillExcution = new SeckillExcution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<>(false, seckillExcution);
        } catch (Exception e3) {
            SeckillExcution seckillExcution = new SeckillExcution(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<>(false, seckillExcution);
        }

    }


    /**
     * 获取系统的时间
     */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }

}
